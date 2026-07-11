package state;

import excepciones.OperacionInvalidaException;
import modelo.Cuenta;
import observer.EventoCuenta;
import observer.EventoCuenta.TipoEvento;

// Cuenta bloqueada: no admite movimientos. Solo desbloquear (vuelve a Activa) o cerrar.
public class EstadoBloqueada implements IEstadoCuenta {

    @Override
    public void depositar(Cuenta cuenta, double monto) {
        throw new OperacionInvalidaException("La cuenta está bloqueada: no admite depósitos.");
    }

    @Override
    public void retirar(Cuenta cuenta, double monto) {
        throw new OperacionInvalidaException("La cuenta está bloqueada: no admite retiros.");
    }

    @Override
    public void bloquear(Cuenta cuenta) {
        throw new OperacionInvalidaException("La cuenta ya está bloqueada.");
    }

    @Override
    public void desbloquear(Cuenta cuenta) {
        cuenta.setEstado(new EstadoActiva());
        cuenta.notificar(new EventoCuenta(TipoEvento.CAMBIO_ESTADO, cuenta.getNumero(),
                0, cuenta.getSaldo(), "Cuenta desbloqueada (vuelve a Activa)"));
    }

    @Override
    public void cerrar(Cuenta cuenta) {
        cuenta.setEstado(new EstadoCerrada());
        cuenta.notificar(new EventoCuenta(TipoEvento.CIERRE, cuenta.getNumero(),
                0, cuenta.getSaldo(), "Cuenta cerrada"));
    }

    @Override
    public String getNombre() { return "BLOQUEADA"; }
}
