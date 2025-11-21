package org.educa;

import org.educa.entity.VehiculoEntity;
import org.educa.service.VehiculoService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final VehiculoService vehiculoService = new VehiculoService();

        System.out.println("Bienvenido al programa para eliminar vehículos");
        System.out.println("Introduce las matrículas: (Para finalizar teclee la letra F)");
        try (Scanner sc = new Scanner(System.in)) {
            List<VehiculoEntity> vehiculos = new ArrayList<>();
            String matricula = "";
            do {
                matricula = sc.nextLine();
                if (!matricula.equalsIgnoreCase("F")) {
                    VehiculoEntity vehiculo = vehiculoService.findByMatricula(matricula, 5);
                    if (vehiculo != null) {
                        vehiculos.add(vehiculo);
                    }
                }
            } while (!matricula.equalsIgnoreCase("F"));

            if (!vehiculos.isEmpty()) {
                for (VehiculoEntity vehiculo : vehiculos) {
                    System.out.printf("Vehículo seleccionado: %s (%s) [Id: %d]%n Marca: %s %n Modelo: %s %n Color: %s %n Año: %d %n",
                            vehiculo.getMatricula(), vehiculo.getBastidor(), vehiculo.getIdVehiculo(), vehiculo.getMarca(),
                            vehiculo.getModelo(), vehiculo.getColor(), vehiculo.getAnio());
                    System.out.println("================================================");
                }
                System.out.println("¿Desea eliminar estos vehículos? Y/N");
                String ans = sc.nextLine();
                if (ans.equalsIgnoreCase("y")) {
                    vehiculoService.deleteAll(vehiculos);
                }
            } else {
                System.out.println("No se han encontrado vehículos");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}