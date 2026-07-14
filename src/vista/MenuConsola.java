package vista;

import java.util.Scanner;

import decorator.CalculoBase;
import decorator.ComisionDecorator;
import decorator.ICalculoOperacion;
import excepciones.OperacionInvalidaException;
import facade.SistemaFinancieroFacade;
import modelo.Cliente;
import modelo.Cuenta;

public class MenuConsola {

    private final SistemaFinancieroFacade fachada;
    private final Scanner scanner;

    public MenuConsola() {
        this.fachada = new SistemaFinancieroFacade();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n    SISTEMA DE GESTION FINANCIERA    ");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Cuenta");
            System.out.println("3. Listar Cuentas");
            System.out.println("4. Depositar");
            System.out.println("5. Retiro estandar");
            System.out.println("6. Retiro en cajero de otro banco (Aplica comision)");
            System.out.println("7. Bloquear Cuenta");
            System.out.println("8. Desbloquear Cuenta");
            System.out.println("9. Cerrar Cuenta");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            
            opcion = Integer.parseInt(scanner.nextLine());

            try {
                procesarOpcion(opcion);
            } catch (OperacionInvalidaException e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERROR INESPERADO] " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> {
                System.out.print("DNI: ");
                String dni = scanner.nextLine();
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Correo: ");
                String correo = scanner.nextLine();
                fachada.registrarCliente(dni, nombre, correo);
                System.out.println("Cliente registrado con exito.");
            }
            case 2 -> {
                System.out.print("Numero de Cuenta: ");
                String num = scanner.nextLine();
                System.out.print("DNI del Titular: ");
                String dni = scanner.nextLine();
                System.out.print("Saldo Inicial: ");
                double saldo = Double.parseDouble(scanner.nextLine());
                fachada.registrarCuenta(num, dni, saldo);
                System.out.println("Cuenta registrada con exito.");
            }
            case 3 -> {
                System.out.println("\n    Lista de Cuentas    ");
                for (Cuenta c : fachada.listarCuentas()) {
                    System.out.printf("Cuenta: %s | Titular: %s | Saldo: %.2f | Estado: %s%n", 
                            c.getNumero(), c.getTitularDni(), c.getSaldo(), c.getEstado().getNombre());
                }
            }
            case 4 -> {
                System.out.print("Numero de Cuenta: ");
                String num = scanner.nextLine();
                System.out.print("Monto a depositar: ");
                double monto = Double.parseDouble(scanner.nextLine());
                fachada.depositar(num, monto);
                System.out.println("Deposito procesado.");
            }
            case 5 -> {
                System.out.print("Numero de Cuenta: ");
                String num = scanner.nextLine();
                System.out.print("Monto a retirar: ");
                double monto = Double.parseDouble(scanner.nextLine());
                fachada.retirar(num, monto);
                System.out.println("Retiro procesado.");
            }
            case 6 -> {
                System.out.print("Numero de Cuenta: ");
                String num = scanner.nextLine();
                System.out.print("Monto base a retirar: ");
                double base = Double.parseDouble(scanner.nextLine());
                System.out.print("Comision fija a cobrar: ");
                double comision = Double.parseDouble(scanner.nextLine());
                
                // Aplicación del patrón Decorator
                ICalculoOperacion calculo = new ComisionDecorator(new CalculoBase(), comision);
                double montoFinal = calculo.calcular(base); 
                
                System.out.printf("Procesando retiro con comision. Monto real a debitar: %.2f%n", montoFinal);
                fachada.retirar(num, montoFinal);
                System.out.println("Retiro con comision procesado.");
            }
            case 7 -> {
                System.out.print("Numero de Cuenta a bloquear: ");
                String num = scanner.nextLine();
                fachada.bloquearCuenta(num);
                System.out.println("Cuenta bloqueada.");
            }
            case 8 -> {
                System.out.print("Numero de Cuenta a desbloquear: ");
                String num = scanner.nextLine();
                fachada.desbloquearCuenta(num);
                System.out.println("Cuenta desbloqueada. Vuelve a estar ACTIVA.");
            }
            case 9 -> {
                System.out.print("Numero de Cuenta a cerrar: ");
                String num = scanner.nextLine();
                System.out.print("¿Esta seguro que desea CERRAR esta cuenta definitivamente? : ");
                String confirmacion = scanner.nextLine();
                if (confirmacion.equalsIgnoreCase("Si")) {
                    fachada.cerrarCuenta(num);
                    System.out.println("Cuenta cerrada exitosamente. Ya no admitira mas operaciones.");
                } else if (confirmacion.equalsIgnoreCase("No")) {
                    System.out.println("Operación cancelada.");
                }
            }
            case 0 -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("Opcion no válida.");
        }
    }
}