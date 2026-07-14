package state;

import excepciones.OperacionInvalidaException;
import modelo.Cuenta;
import observer.EventoCuenta;
import observer.EventoCuenta.TipoEvento;

// Saldo negativo. No permite retiros; un depósito que regulariza el saldo
// devuelve la cuenta a Activa.
public class EstadoSobregirada implements IEstadoCuenta {

    @Override
    public void depositar(Cuenta cuenta, double monto) {
        double nuevoSaldo = cuenta.getSaldo() + monto;
        cuenta.setSaldo(nuevoSaldo);
        if (nuevoSaldo >= 0) {
            cuenta.setEstado(new EstadoActiva());
            cuenta.notificar(new EventoCuenta(TipoEvento.DEPOSITO, cuenta.getNumero(),
                    monto, nuevoSaldo, "Depósito regularizó la cuenta (vuelve a Activa)"));
        } else {
            cuenta.notificar(new EventoCuenta(TipoEvento.DEPOSITO, cuenta.getNumero(),
                    monto, nuevoSaldo, "Depósito sobre cuenta sobregirada"));
        }
    }

    @Override
    public void retirar(Cuenta cuenta, double monto) {
        throw new OperacionInvalidaException("Cuenta sobregirada: no se permiten retiros.");
    }

    @Override
    public void bloquear(Cuenta cuenta) {
        cuenta.setEstado(new EstadoBloqueada());
        cuenta.notificar(new EventoCuenta(TipoEvento.BLOQUEO, cuenta.getNumero(),
                0, cuenta.getSaldo(), "Cuenta bloqueada"));
    }

    @Override
    public void desbloquear(Cuenta cuenta) {
        throw new OperacionInvalidaException("La cuenta no está bloqueada.");
    }

    @Override
    public void cerrar(Cuenta cuenta) {
        cuenta.setEstado(new EstadoCerrada());
        cuenta.notificar(new EventoCuenta(TipoEvento.CIERRE, cuenta.getNumero(),
                0, cuenta.getSaldo(), "Cuenta cerrada"));
    }

    @Override
    public String getNombre() { return "SOBREGIRADA"; }
}
