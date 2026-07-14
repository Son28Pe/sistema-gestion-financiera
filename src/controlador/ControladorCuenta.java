package controlador;

import java.util.List;

import excepciones.OperacionInvalidaException;
import interfaces.IRepositorio;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.CuentaAhorros;
import observer.IObservador;
import state.EstadoActiva;
import util.Validador;

// GRASP Controlador: coordina el CRUD de cuentas y sus operaciones. Depende de las interfaces
// de repositorio (DIP), valida con Validador y DELEGA las operaciones a la Cuenta/estado (State).
public class ControladorCuenta {

    private final IRepositorio<Cuenta> repositorio;
    private final IRepositorio<Cliente> repositorioClientes; // para validar el titular
    private final List<IObservador> observadores;

    public ControladorCuenta(IRepositorio<Cuenta> repositorio,
                             IRepositorio<Cliente> repositorioClientes,
                             List<IObservador> observadores) {
        this.repositorio = repositorio;
        this.repositorioClientes = repositorioClientes;
        this.observadores = observadores;
    }

    public Cuenta registrar(String numero, String titularDni, double saldoInicial) {
        Validador.validarTexto(numero, "numero");
        Validador.requerirExistencia(repositorioClientes.buscarPorId(titularDni), "cliente " + titularDni);
        if (repositorio.buscarPorId(numero).isPresent()) {
            throw new OperacionInvalidaException("Ya existe una cuenta " + numero + ".");
        }
        // Integracion: cuando Persona 1 tenga su Factory de cuentas, delegar la creacion aqui.
        Cuenta cuenta = new CuentaAhorros(numero, titularDni, saldoInicial, new EstadoActiva());
        repositorio.guardar(cuenta);
        return cuenta;
    }

    public Cuenta buscar(String numero) {
        return Validador.requerirExistencia(repositorio.buscarPorId(numero), "cuenta " + numero);
    }

    public List<Cuenta> listar() {
        return repositorio.listar();
    }

    public void depositar(String numero, double monto) {
        Validador.validarMonto(monto);
        Cuenta cuenta = prepararCuenta(numero);
        cuenta.depositar(monto);        // delega en el estado (State) y notifica (Observer)
        repositorio.actualizar(cuenta);
    }

    public void retirar(String numero, double monto) {
        Validador.validarMonto(monto);
        Cuenta cuenta = prepararCuenta(numero);
        cuenta.retirar(monto);
        repositorio.actualizar(cuenta);
    }

    public void bloquear(String numero) {
        Cuenta cuenta = prepararCuenta(numero);
        cuenta.bloquear();
        repositorio.actualizar(cuenta);
    }

    public void desbloquear(String numero) {
        Cuenta cuenta = prepararCuenta(numero);
        cuenta.desbloquear();
        repositorio.actualizar(cuenta);
    }

    public void cerrar(String numero) {
        Cuenta cuenta = prepararCuenta(numero);
        cuenta.cerrar();
        repositorio.actualizar(cuenta);
    }

    public void eliminar(String numero) {
        buscar(numero); // valida que exista
        repositorio.eliminar(numero);
    }

    // Carga la cuenta y le engancha los observadores (al cargar desde disco vienen sin ellos).
    private Cuenta prepararCuenta(String numero) {
        Cuenta cuenta = buscar(numero);
        for (IObservador observador : observadores) {
            cuenta.agregarObservador(observador);
        }
        return cuenta;
    }
}
