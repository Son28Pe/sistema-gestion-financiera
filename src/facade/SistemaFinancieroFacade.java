package facade;

import java.util.List;

import controlador.ControladorCliente;
import controlador.ControladorCuenta;
import modelo.Cliente;
import modelo.Cuenta;
import observer.AlertaFraude;
import observer.IObservador;
import observer.NotificadorEmail;
import observer.RegistroHistorial;
import repositorio.RepositorioClienteJson;
import repositorio.RepositorioCuentaJson;

public class SistemaFinancieroFacade {

    private final ControladorCliente clientes;
    private final ControladorCuenta cuentas;

    public SistemaFinancieroFacade() {
        var repoCli = new RepositorioClienteJson();
        var repoCta = new RepositorioCuentaJson();
        List<IObservador> obs = List.of(
                new NotificadorEmail(), 
                new RegistroHistorial(), 
                new AlertaFraude()
        );

        this.clientes = new ControladorCliente(repoCli);
        this.cuentas = new ControladorCuenta(repoCta, repoCli, obs);
    }

    //Delegaciones para Gestión de Clientes
    public Cliente registrarCliente(String dni, String nombre, String correo) {
        return clientes.registrar(dni, nombre, correo);
    }

    public Cliente buscarCliente(String dni) {
        return clientes.buscar(dni);
    }

    public List<Cliente> listarClientes() {
        return clientes.listar();
    }

    public void modificarCliente(String dni, String nombre, String correo) {
        clientes.modificar(dni, nombre, correo);
    }

    public void eliminarCliente(String dni) {
        clientes.eliminar(dni);
    }

    //Delegaciones para Gestión de Cuentas
    public Cuenta registrarCuenta(String numero, String titularDni, double saldoInicial) {
        return cuentas.registrar(numero, titularDni, saldoInicial);
    }

    public Cuenta buscarCuenta(String numero) {
        return cuentas.buscar(numero);
    }

    public List<Cuenta> listarCuentas() {
        return cuentas.listar();
    }

    public void depositar(String numero, double monto) {
        cuentas.depositar(numero, monto);
    }

    public void retirar(String numero, double monto) {
        cuentas.retirar(numero, monto);
    }

    public void bloquearCuenta(String numero) {
        cuentas.bloquear(numero);
    }

    public void desbloquearCuenta(String numero) {
        cuentas.desbloquear(numero);
    }

    public void cerrarCuenta(String numero) {
        cuentas.cerrar(numero);
    }

    public void eliminarCuenta(String numero) {
        cuentas.eliminar(numero);
    }
}