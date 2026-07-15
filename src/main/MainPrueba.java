package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import controlador.ControladorCliente;
import controlador.ControladorCuenta;
import observer.AlertaFraude;
import observer.IObservador;
import observer.NotificadorEmail;
import observer.RegistroHistorial;
import repositorio.RepositorioClienteJson;
import repositorio.RepositorioCuentaJson;

// Main de prueba (temporal): corre el flujo completo de la parte de Persona 2 sin el menu.
// La vista/menu real la hara Persona 3.
public class MainPrueba {

    public static void main(String[] args) throws IOException {
        limpiarDatosDemo();

        // Cableado (en el sistema real lo arma la Facade de Persona 3).
        var repoClientes = new RepositorioClienteJson();
        var repoCuentas = new RepositorioCuentaJson();
        var historial = new RegistroHistorial();
        List<IObservador> observadores = List.of(new NotificadorEmail(), historial, new AlertaFraude());

        var clientes = new ControladorCliente(repoClientes);
        var cuentas = new ControladorCuenta(repoCuentas, repoClientes, observadores);

        titulo("Registro");
        clientes.registrar("12345678", "Edson Raqui", "edson@mail.com");
        cuentas.registrar("001-2345", "12345678", 300.0);
        System.out.println("Registrados. Estado: " + cuentas.buscar("001-2345").getEstado().getNombre());

        titulo("Deposito de 500");
        cuentas.depositar("001-2345", 500);

        titulo("Retiro de 1200 (deja la cuenta sobregirada)");
        cuentas.retirar("001-2345", 1200);
        mostrar(cuentas);

        titulo("Deposito de 600 (regulariza la cuenta)");
        cuentas.depositar("001-2345", 600);
        mostrar(cuentas);

        titulo("Bloqueo e intento de retiro");
        cuentas.bloquear("001-2345");
        try {
            cuentas.retirar("001-2345", 50);
        } catch (RuntimeException e) {
            System.out.println("Rechazado: " + e.getMessage());
        }

        titulo("Historial de movimientos");
        historial.imprimir();

        titulo("Persistencia");
        System.out.println("Datos guardados en data/clientes.json y data/cuentas.json");
    }

    private static void mostrar(ControladorCuenta cuentas) {
        var c = cuentas.buscar("001-2345");
        System.out.println("Estado: " + c.getEstado().getNombre() + " | Saldo: " + c.getSaldo());
    }

    private static void titulo(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    // Arranca limpio en cada ejecucion (es un demo, no el sistema real).
    private static void limpiarDatosDemo() throws IOException {
        Files.deleteIfExists(Path.of("data/cuentas.json"));
        Files.deleteIfExists(Path.of("data/clientes.json"));
        Files.deleteIfExists(Path.of("data/historial.json"));
    }
}
