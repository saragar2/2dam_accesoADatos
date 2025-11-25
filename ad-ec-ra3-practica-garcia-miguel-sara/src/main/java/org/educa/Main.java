package org.educa;

import org.educa.entity.*;
import org.educa.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Servicios
            ClienteService clienteService = new ClienteService();
            AlquilerService alquilerService = new AlquilerService();
            SucursalService sucursalService = new SucursalService();
            VehiculoService vehiculoService = new VehiculoService();
            SeguroService seguroService = new SeguroService();

            System.out.println("===== SISTEMA DE ALQUILERES =====");

            // === 1. Solicitar DNI ===
            System.out.print("Introduce el DNI del cliente: ");
            String dni = sc.nextLine().trim();

            ClienteEntity cliente = clienteService.findByDNI(dni);
            List<AlquilerEntity> alquileresPrevios = new ArrayList<>();

            if (cliente == null) {
                System.out.println("Cliente no encontrado. Procedemos a registrarlo.");

                cliente = new ClienteEntity();
                System.out.print("Nombre: ");
                cliente.setNombre(sc.nextLine().trim());
                System.out.print("Primer apellido: ");
                cliente.setPrimerApellido(sc.nextLine().trim());
                System.out.print("Segundo apellido: ");
                cliente.setSegundoApellido(sc.nextLine().trim());
                System.out.print("Email: ");
                cliente.setEmail(sc.nextLine().trim());
                System.out.print("Teléfono: ");
                cliente.setTelefono(sc.nextLine().trim());
                cliente.setDni(dni);

                cliente.setDirecciones(new ArrayList<>());

                boolean continuar;
                do {
                    System.out.print("Calle: ");
                    String calle = sc.nextLine();

                    System.out.print("Ciudad: ");
                    String ciudad = sc.nextLine();

                    System.out.print("País: ");
                    String pais = sc.nextLine();

                    System.out.print("Código Postal: ");
                    String cPostal = sc.nextLine();

                    DireccionEntity direccion = new DireccionEntity(null, calle, ciudad, pais, cPostal, cliente);
                    cliente.getDirecciones().add(direccion);

                    System.out.print("¿Desea añadir otra dirección? (s/n): ");
                    String respuesta = sc.nextLine().trim().toLowerCase();
                    continuar = respuesta.equals("s");
                } while (continuar);

                clienteService.saveCliente(cliente);
                System.out.println("Cliente registrado correctamente.");
            } else {
                System.out.println("\nCliente encontrado: " + cliente.getNombre() + " " + cliente.getPrimerApellido() +
                        " " + cliente.getSegundoApellido());
                System.out.println("Mostrando alquileres previos...");

                if (cliente.getAlquileres().isEmpty()) {
                    System.out.println("No tiene alquileres previos.");
                } else {
                    alquileresPrevios = cliente.getAlquileres();
                    for (AlquilerEntity a : cliente.getAlquileres()) {
                        System.out.println(" - Alquiler ID " + a.getId() +
                                " | Vehículo: " + a.getVehiculo().getMarca() + " " + a.getVehiculo().getModelo() +
                                " | Fechas: " + a.getFechaIni() + " a " + a.getFechaFin());
                    }
                }
            }

            // === 2. Proceso de nuevo alquiler ===
            System.out.println("\n=== NUEVO ALQUILER ===");
            boolean fechasCorrectas = false;
            LocalDate fechaIni;
            LocalDate fechaFin;
            do {
                fechaIni = leerFecha(sc, "Introduce fecha de inicio (dd/MM/yyyy): ", formatter);
                fechaFin = leerFecha(sc, "Introduce fecha de fin (dd/MM/yyyy): ", formatter);
                if ((fechaIni.isAfter(LocalDate.now()) || fechaIni.isEqual(LocalDate.now()))
                        && fechaFin.isAfter(fechaIni)) {
                    fechasCorrectas = true;
                } else {
                    System.out.println("❌ La fecha de fin debe ser posterior a la fecha de inicio. " +
                            "Y la fecha inicio debe ser mayor o igual al día de hoy");
                }
            } while (!fechasCorrectas);

            // === Selección de sucursal ===
            System.out.println("\nSeleccione la sucursal de origen:");
            List<SucursalEntity> sucursales = sucursalService.findAll();
            for (int i = 0; i < sucursales.size(); i++) {
                SucursalEntity s = sucursales.get(i);
                System.out.println((i + 1) + ". " + s.getCalle() + " (" + s.getCiudad() + ")");
            }
            int opcSucursal = leerOpcion(sc, sucursales.size());
            SucursalEntity sucursal = sucursales.get(opcSucursal - 1);

            // === Vehículos disponibles ===
            System.out.println("\nVehículos disponibles en " + sucursal.getCiudad() + ":");
            List<VehiculoEntity> vehiculos = vehiculoService.findBySucursal(sucursal);
            for (int i = 0; i < vehiculos.size(); i++) {
                VehiculoEntity v = vehiculos.get(i);
                System.out.println((i + 1) + ". " + v.getMarca() + " " + v.getModelo() + " " + v.getColor()
                        + " (" + vehiculoService.getPrecioPorDia(v) + "€/dia) \nEquipamiento:");
                for (EquipamientoEntity equipamiento : v.getEquipamientos()) {
                    System.out.println("\t " + equipamiento.getNombre());
                }
            }
            int opcVehiculo = leerOpcion(sc, vehiculos.size());
            VehiculoEntity vehiculo = vehiculos.get(opcVehiculo - 1);

            // === Selección de seguro ===
            System.out.println("\nSeleccione el tipo de seguro:");
            List<SeguroEntity> seguros = seguroService.findAll();
            for (int i = 0; i < seguros.size(); i++) {
                SeguroEntity seguro = seguros.get(i);
                System.out.println((i + 1) + ". " + seguro.getNombre() + " (" + seguroService.getPrecioPorDia(seguro) + "€/dia)");
            }
            int opcSeguro = leerOpcion(sc, seguros.size());
            SeguroEntity seguro = seguros.get(opcSeguro - 1);

            // === Cálculo de precio ===
            AlquilerEntity alquiler = new AlquilerEntity();
            alquiler.setCliente(cliente);
            alquiler.setVehiculo(vehiculo);
            alquiler.setSeguro(seguro);
            alquiler.setFechaIni(fechaIni);
            alquiler.setFechaFin(fechaFin);
            alquilerService.calculatePrecio(alquiler, alquileresPrevios.size());


            // === Mostrar resumen ===
            System.out.println("\n=== RESUMEN DE ALQUILER ===");
            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getPrimerApellido());
            System.out.println("Vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo());
            System.out.println("Seguro: " + seguro.getNombre());
            System.out.println("Precio total: " + alquiler.getPrecio() + "€");

            System.out.println("¿Desea realizar el aqluiler? (y/n)");
            String confirm = sc.nextLine();
            if ("y".equalsIgnoreCase(confirm)) {
                // === Guardar alquiler ===
                alquilerService.save(alquiler);
                System.out.println("\n✅ Alquiler registrado correctamente. Gracias por usar nuestro sistema.");
            } else {
                System.out.println("\n✖️ Alquiler cancelado. Gracias por usar nuestro sistema.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // === MÉTODOS AUXILIARES ===

    private static int leerOpcion(Scanner sc, int max) {
        int opcion = -1;
        while (opcion == -1) {
            System.out.println("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                if (opcion < 1 || opcion > max) {
                    System.out.println("⚠️ Debe introducir un número entre " + 1 + " y " + max + ".");
                    opcion = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Valor inválido. Introduce un número.");
                opcion = -1;
            }
        }
        return opcion;
    }

    private static LocalDate leerFecha(Scanner sc, String mensaje, DateTimeFormatter formatter) {
        while (true) {
            System.out.println(mensaje);
            try {
                return LocalDate.parse(sc.nextLine().trim(), formatter);
            } catch (Exception e) {
                System.out.println("⚠️  Formato inválido. Usa el formato dd/MM/yyyy.");
            }
        }
    }
}
