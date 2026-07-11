package controlador;

import java.util.List;

import excepciones.OperacionInvalidaException;
import interfaces.IRepositorio;
import modelo.Cliente;
import util.Validador;

// GRASP Controlador: coordina el CRUD de clientes. Depende de la interfaz del repositorio (DIP)
// y delega las validaciones al Validador. No contiene logica de negocio.
public class ControladorCliente {

    private final IRepositorio<Cliente> repositorio;

    public ControladorCliente(IRepositorio<Cliente> repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente registrar(String dni, String nombre, String correo) {
        Validador.validarTexto(dni, "dni");
        Validador.validarTexto(nombre, "nombre");
        Validador.validarTexto(correo, "correo");
        if (repositorio.buscarPorId(dni).isPresent()) {
            throw new OperacionInvalidaException("Ya existe un cliente con dni " + dni + ".");
        }
        Cliente cliente = new Cliente(dni, nombre, correo);
        repositorio.guardar(cliente);
        return cliente;
    }

    public Cliente buscar(String dni) {
        return Validador.requerirExistencia(repositorio.buscarPorId(dni), "cliente " + dni);
    }

    public List<Cliente> listar() {
        return repositorio.listar();
    }

    public void modificar(String dni, String nuevoNombre, String nuevoCorreo) {
        Cliente cliente = buscar(dni);
        Validador.validarTexto(nuevoNombre, "nombre");
        Validador.validarTexto(nuevoCorreo, "correo");
        cliente.setNombre(nuevoNombre);
        cliente.setCorreo(nuevoCorreo);
        repositorio.actualizar(cliente);
    }

    public void eliminar(String dni) {
        buscar(dni); // valida que exista antes de eliminar
        repositorio.eliminar(dni);
    }
}
