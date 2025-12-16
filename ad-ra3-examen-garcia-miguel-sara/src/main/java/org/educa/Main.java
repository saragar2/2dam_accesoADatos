package org.educa;

import org.educa.entity.SucursalEntity;
import org.educa.entity.VehiculoEntity;
import org.educa.service.SucursalService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Bienvenido al menu de sucursal.");
        SucursalService sucursalService = new SucursalService();
        try (Scanner sc = new Scanner(System.in)) {
            int opcion = 0;
            while (opcion != 5) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Insertar nueva sucursal");
                System.out.println("2. Buscar sucursal por CP");
                System.out.println("3. Buscar sucursal por calle");
                System.out.println("4. Eliminar sucursal por CP");
                System.out.println("5. Salir");

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        insertarSucursal(sc, sucursalService);
                        break;
                    case 2:
                        buscarSucursalPorCP(sc, sucursalService);
                        break;
                    case 3:
                        buscarSucursalPorCalle(sc, sucursalService);
                        break;
                    case 4:
                        eliminarSucursalPorCP(sc, sucursalService);
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertarSucursal(Scanner sc, SucursalService sucursalService) throws ParseException {
        System.out.println("Ingrese el pais:");
        String pais = sc.nextLine();
        System.out.println("Ingrese la ciudad:");
        String ciudad = sc.nextLine();
        System.out.println("Ingrese la calle:");
        String calle = sc.nextLine();
        System.out.println("Ingrese el cp:");
        String cp = sc.nextLine();

        SucursalEntity sucursal = new SucursalEntity();
        sucursal.setCalle(calle);
        sucursal.setCiudad(ciudad);
        sucursal.setPais(pais);
        sucursal.setCp(cp);

        List<VehiculoEntity> vehiculos = new ArrayList<>();
        while (true) {
            System.out.println("¿Desea agregar un vehiculo? (s/n):");
            String respuesta = sc.nextLine();
            if (!"s".equalsIgnoreCase(respuesta)) {
                break;
            }
            System.out.println("Ingrese matricula:");
            String matricula = sc.nextLine();
            System.out.println("Ingrese bastidor:");
            String bastidor = sc.nextLine();
            System.out.println("Ingrese marca:");
            String marca = sc.nextLine();
            System.out.println("Ingrese modelo:");
            String modelo = sc.nextLine();
            System.out.println("Ingrese color:");
            String color = sc.nextLine();
            System.out.println("Ingrese año:");
            Integer anyo = sc.nextInt();
            sc.nextLine();

            VehiculoEntity vehiculo = new VehiculoEntity();
            vehiculo.setSucursal(sucursal);
            vehiculo.setMatricula(matricula);
            vehiculo.setBastidor(bastidor);
            vehiculo.setMarca(marca);
            vehiculo.setModelo(modelo);
            vehiculo.setColor(color);
            vehiculo.setAnio(anyo);

            vehiculos.add(vehiculo);
        }
        sucursal.setVehiculos(vehiculos);

        sucursalService.insertar(sucursal);
        System.out.println("Vehiculo insertado correctamente.");
        System.out.print("\n");
    }

    private static void buscarSucursalPorCP(Scanner sc, SucursalService sucursalService) {
        System.out.println("Ingrese CP de la sucursal:");
        String cp = sc.nextLine();
        SucursalEntity sucursal = sucursalService.findByCP(cp);
        if (sucursal != null) {
            mostrarSucursal(sucursal);
        } else {
            System.out.println("Vehiculo no encontrado.");
        }
        System.out.print("\n");
    }

    private static void eliminarSucursalPorCP(Scanner sc, SucursalService sucursalService) {
        System.out.println("Ingrese el CP de la sucursal:");
        String cp = sc.nextLine();
        Integer id = sucursalService.deleteByCP(cp);
        if (id != null && id > 0) {
            System.out.println("Sucursal eliminada.");
        } else {
            System.out.println("Sucursal NO eliminada");
        }
        System.out.print("\n");
    }

    private static void buscarSucursalPorCalle(Scanner sc, SucursalService sucursalService) {
        System.out.println("Ingrese texto a buscar como parte de la calle:");
        String texto = sc.nextLine();
        List<SucursalEntity> sucursales = sucursalService.findByCalle(texto);
        if (!sucursales.isEmpty()) {
            for (SucursalEntity sucursal : sucursales) {
                mostrarSucursal(sucursal);
            }
        } else {
            System.out.println("No se encontraron sucursales con esa calle.");
        }
        System.out.print("\n");
    }

    private static void mostrarSucursal(SucursalEntity sucursal) {
        System.out.println("Pais: " + sucursal.getPais());
        System.out.println("Ciudad: " + sucursal.getCiudad());
        System.out.println("Calle: " + sucursal.getCalle());
        System.out.println("CP: " + sucursal.getCp());
        System.out.println("Vehiculos:");
        if (sucursal.getVehiculos() != null && !sucursal.getVehiculos().isEmpty()) {
            for (VehiculoEntity vehiculo : sucursal.getVehiculos()) {
                System.out.println("- " + vehiculo.getMatricula() + ", " + vehiculo.getBastidor() + ", " + vehiculo.getMarca()
                        + ", " + vehiculo.getModelo() + ", " + vehiculo.getColor() + " (" + vehiculo.getAnio() + ")");
            }
        } else {
            System.out.println("- [Sucursal sin vehiculos]");
        }
        System.out.println("==================================");
    }
}