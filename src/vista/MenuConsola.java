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
        int opcion = -1;
        do {
            System.out.println("\n==========================================");
            System.out.println("      SISTEMA DE GESTIÓN FINANCIERA       ");
            System.out.println("==========================================");
            System.out.println("1. Gestión de Clientes (CRUD)");
            System.out.println("2. Gestión de Cuentas (CRUD)");
            System.out.println("3. Operaciones Bancarias (Depósitos/Retiros)");
            System.out.println("4. Control de Estados de Cuenta (State/Observer)");
            System.out.println("0. Salir del Sistema");
            System.out.println("==========================================");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                procesarMenuPrincipal(opcion);
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Por favor, ingrese un número válido.");
                opcion = -1;
            } catch (OperacionInvalidaException e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERROR INESPERADO] " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void procesarMenuPrincipal(int opcion) {
        switch (opcion) {
            case 1 -> menuClientes();
            case 2 -> menuCuentas();
            case 3 -> menuOperaciones();
            case 4 -> menuEstados();
            case 0 -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("Opción no válida.");
        }
    }

    // --- Submenú de Clientes ---
    private void menuClientes() {
        int opcion = -1;
        do {
            System.out.println("\n>>> GESTIÓN DE CLIENTES <<<");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Eliminar Cliente");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> registrarCliente();
                    case 2 -> listarClientes();
                    case 3 -> eliminarCliente();
                    case 0 -> {}
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Ingrese un número válido.");
                opcion = -1;
            } catch (OperacionInvalidaException e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // --- Submenú de Cuentas ---
    private void menuCuentas() {
        int opcion = -1;
        do {
            System.out.println("\n>>> GESTIÓN DE CUENTAS <<<");
            System.out.println("1. Registrar Cuenta (Ahorros)");
            System.out.println("2. Listar Cuentas");
            System.out.println("3. Eliminar Cuenta");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> registrarCuenta();
                    case 2 -> listarCuentas();
                    case 3 -> eliminarCuenta();
                    case 0 -> {}
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Ingrese un número válido.");
                opcion = -1;
            } catch (OperacionInvalidaException e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // --- Submenú de Operaciones ---
    private void menuOperaciones() {
        int opcion = -1;
        do {
            System.out.println("\n>>> OPERACIONES FINANCIERAS <<<");
            System.out.println("1. Depositar");
            System.out.println("2. Retiro Estándar");
            System.out.println("3. Retiro Externo (Aplica Comisión - Decorator)");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> depositar();
                    case 2 -> retirarEstandar();
                    case 3 -> retirarConComision();
                    case 0 -> {}
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Ingrese un número válido.");
                opcion = -1;
            } catch (OperacionInvalidaException e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // --- Submenú de Estados ---
    private void menuEstados() {
        int opcion = -1;
        do {
            System.out.println("\n>>> CONTROL DE ESTADOS (STATE) <<<");
            System.out.println("1. Bloquear Cuenta");
            System.out.println("2. Desbloquear Cuenta");
            System.out.println("3. Cerrar Cuenta");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> bloquearCuenta();
                    case 2 -> desbloquearCuenta();
                    case 3 -> cerrarCuenta();
                    case 0 -> {}
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Ingrese un número válido.");
                opcion = -1;
            } catch (OperacionInvalidaException e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // --- Métodos de Acción ---
    private void registrarCliente() {
        System.out.print("DNI (8 dígitos): ");
        String dni = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        fachada.registrarCliente(dni, nombre, correo);
        System.out.println("Cliente registrado con éxito.");
    }

    private void listarClientes() {
        System.out.println("\n    Lista de Clientes    ");
        var clientes = fachada.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente c : clientes) {
                System.out.printf("DNI: %s | Nombre: %s | Correo: %s%n", 
                        c.getDni(), c.getNombre(), c.getCorreo());
            }
        }
    }

    private void eliminarCliente() {
        System.out.print("DNI del Cliente a eliminar: ");
        String dni = scanner.nextLine();
        System.out.print("¿Está seguro que desea ELIMINAR este cliente definitivamente? (Si/No): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("Si")) {
            fachada.eliminarCliente(dni);
            System.out.println("Cliente eliminado exitosamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void registrarCuenta() {
        System.out.print("Número de Cuenta: ");
        String num = scanner.nextLine();
        System.out.print("DNI del Titular: ");
        String dni = scanner.nextLine();
        System.out.print("Saldo Inicial: ");
        double saldo = Double.parseDouble(scanner.nextLine());
        fachada.registrarCuenta(num, dni, saldo);
        System.out.println("Cuenta registrada con éxito.");
    }

    private void listarCuentas() {
        System.out.println("\n    Lista de Cuentas    ");
        var cuentas = fachada.listarCuentas();
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
        } else {
            for (Cuenta c : cuentas) {
                System.out.printf("Cuenta: %s | Titular: %s | Saldo: %.2f | Estado: %s%n", 
                        c.getNumero(), c.getTitularDni(), c.getSaldo(), c.getEstado().getNombre());
            }
        }
    }

    private void eliminarCuenta() {
        System.out.print("Número de Cuenta a eliminar: ");
        String num = scanner.nextLine();
        System.out.print("¿Está seguro que desea ELIMINAR esta cuenta definitivamente? (Si/No): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("Si")) {
            fachada.eliminarCuenta(num);
            System.out.println("Cuenta eliminada exitosamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void depositar() {
        System.out.print("Número de Cuenta: ");
        String num = scanner.nextLine();
        System.out.print("Monto a depositar: ");
        double monto = Double.parseDouble(scanner.nextLine());
        fachada.depositar(num, monto);
        System.out.println("Depósito procesado.");
    }

    private void retirarEstandar() {
        System.out.print("Número de Cuenta: ");
        String num = scanner.nextLine();
        System.out.print("Monto a retirar: ");
        double monto = Double.parseDouble(scanner.nextLine());
        fachada.retirar(num, monto);
        System.out.println("Retiro procesado.");
    }

    private void retirarConComision() {
        System.out.print("Número de Cuenta: ");
        String num = scanner.nextLine();
        System.out.print("Monto base a retirar: ");
        double base = Double.parseDouble(scanner.nextLine());
        System.out.print("Comisión fija a cobrar: ");
        double comision = Double.parseDouble(scanner.nextLine());
        
        // Aplicación del patrón Decorator
        ICalculoOperacion calculo = new ComisionDecorator(new CalculoBase(), comision);
        double montoFinal = calculo.calcular(base); 
        
        System.out.printf("Procesando retiro con comisión. Monto real a debitar: %.2f%n", montoFinal);
        fachada.retirar(num, montoFinal);
        System.out.println("Retiro con comisión procesado.");
    }

    private void bloquearCuenta() {
        System.out.print("Número de Cuenta a bloquear: ");
        String num = scanner.nextLine();
        fachada.bloquearCuenta(num);
        System.out.println("Cuenta bloqueada.");
    }

    private void desbloquearCuenta() {
        System.out.print("Número de Cuenta a desbloquear: ");
        String num = scanner.nextLine();
        fachada.desbloquearCuenta(num);
        System.out.println("Cuenta desbloqueada. Vuelve a estar ACTIVA.");
    }

    private void cerrarCuenta() {
        System.out.print("Número de Cuenta a cerrar: ");
        String num = scanner.nextLine();
        System.out.print("¿Está seguro que desea CERRAR esta cuenta definitivamente? (Si/No): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("Si")) {
            fachada.cerrarCuenta(num);
            System.out.println("Cuenta cerrada exitosamente. Ya no admitirá más operaciones.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}