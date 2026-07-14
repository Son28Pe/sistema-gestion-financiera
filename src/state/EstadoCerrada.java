package state;

import excepciones.OperacionInvalidaException;
import modelo.Cuenta;

// Estado terminal: no admite ninguna operación.
public class EstadoCerrada implements IEstadoCuenta {

    @Override
    public void depositar(Cuenta cuenta, double monto) {
        throw new OperacionInvalidaException("La cuenta está cerrada.");
    }

    @Override
    public void retirar(Cuenta cuenta, double monto) {
        throw new OperacionInvalidaException("La cuenta está cerrada.");
    }

    @Override
    public void bloquear(Cuenta cuenta) {
        throw new OperacionInvalidaException("La cuenta está cerrada.");
    }

    @Override
    public void desbloquear(Cuenta cuenta) {
        throw new OperacionInvalidaException("La cuenta está cerrada.");
    }

    @Override
    public void cerrar(Cuenta cuenta) {
        throw new OperacionInvalidaException("La cuenta ya está cerrada.");
    }

    @Override
    public String getNombre() { return "CERRADA"; }
}
