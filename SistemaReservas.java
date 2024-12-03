
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

class Pasajero {
    String nombre;
    String documento;
    String tipoBoleto;
    double precioBoleto;
    double equipajePeso;

    Pasajero(String nombre, String documento, String tipoBoleto, double precioBoleto, double equipajePeso) {
        this.nombre = nombre;
        this.documento = documento;
        this.tipoBoleto = tipoBoleto;
        this.precioBoleto = precioBoleto;
        this.equipajePeso = equipajePeso;
    }
}

public class SistemaReservas {
    private static final int ASIENTOS_TOTALES = 100;
    private static final double LIMITE_EQUIPAJE = 23.0; // kg
    private static final LocalDateTime LIMITE_RESERVA = LocalDateTime.now().plusDays(7);
    private static final ArrayList<Pasajero> reservas = new ArrayList<>();
    private static int asientosDisponibles = ASIENTOS_TOTALES;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SISTEMA DE RESERVAS ---");
            System.out.println("1. Reservar vuelo");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Ver asientos disponibles");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    realizarReserva(sc);
                    break;
                case 2:
                    cancelarReserva(sc);
                    break;
                case 3:
                    System.out.println("Asientos disponibles: " + asientosDisponibles);
                    break;
                case 4:
                    System.out.println("¡Gracias por usar el sistema!");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void realizarReserva(Scanner sc) {
        if (LocalDateTime.now().isAfter(LIMITE_RESERVA)) {
            System.out.println("El tiempo para realizar reservas ha expirado.");
            return;
        }
        if (asientosDisponibles == 0) {
            System.out.println("No hay asientos disponibles.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Documento: ");
        String documento = sc.nextLine();
        System.out.print("Tipo de boleto (Económico/Premium): ");
        String tipoBoleto = sc.nextLine();
        System.out.print("Precio del boleto: ");
        double precioBoleto = sc.nextDouble();
        System.out.print("Peso del equipaje (kg): ");
        double equipajePeso = sc.nextDouble();

        if (equipajePeso > LIMITE_EQUIPAJE) {
            System.out.println("El peso del equipaje excede el límite permitido de " + LIMITE_EQUIPAJE + " kg.");
            return;
        }

        Pasajero pasajero = new Pasajero(nombre, documento, tipoBoleto, precioBoleto, equipajePeso);
        reservas.add(pasajero);
        asientosDisponibles--;
        System.out.println("Reserva realizada con éxito.");
    }

    private static void cancelarReserva(Scanner sc) {
        if (LocalDateTime.now().isAfter(LIMITE_RESERVA)) {
            System.out.println("El tiempo para cancelaciones ha expirado. Aplicará una multa del 20%.");
        }

        System.out.print("Ingrese el documento del pasajero a cancelar: ");
        String documento = sc.nextLine();

        for (Pasajero pasajero : reservas) {
            if (pasajero.documento.equals(documento)) {
                double multa = LocalDateTime.now().isAfter(LIMITE_RESERVA) ? pasajero.precioBoleto * 0.2 : 0;
                reservas.remove(pasajero);
                asientosDisponibles++;
                System.out.println("Reserva cancelada. Multa aplicada: $" + multa);
                return;
            }
        }

        System.out.println("No se encontró una reserva con ese documento.");
    }
}
