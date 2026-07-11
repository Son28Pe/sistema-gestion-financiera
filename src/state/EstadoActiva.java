package state;

import excepciones.OperacionInvalidaException;
import modelo.Cuenta;
import observer.EventoCuenta;
import observer.EventoCuenta.TipoEvento;

// Estado inicial. Opera con normalidad y decide las transiciones a Sobregirada,
// Bloqueada y Cerrada.
public class EstadoActiva implements IEstadoCuenta {

    // Sobregiro máximo permitido. Provisional: luego vendrá de la config global (Singleton, Persona 1).
    private static final double LIMITE_SOBREGIRO = -500.0;

    @Override
    public void depositar(Cuenta cuenta, double monto) {
        double nuevoSaldo = cuenta.getSaldo() + monto;
        cuenta.setSaldo(nuevoSaldo);
        cuenta.notificar(new EventoCuenta(TipoEvento.DEPOSITO, cuenta.getNumero(),
                monto, nuevoSaldo, "Depósito realizado"));
    }

    @Override
    public void retirar(Cuenta cuenta, double monto) {
        double nuevoSaldo = cuenta.getSaldo() - monto;
        if (nuevoSaldo < LIMITE_SOBREGIRO) {
            throw new OperacionInvalidaException(
                    "Fondos insuficientes: el retiro supera el límite de sobregiro.");
        }
        cuenta.setSaldo(nuevoSaldo);
        if (nuevoSaldo < 0) {
            cuenta.setEstado(new EstadoSobregirada()); // el estado decide su transición
            cuenta.notificar(new EventoCuenta(TipoEvento.SOBREGIRO, cuenta.getNumero(),
                    monto, nuevoSaldo, "Retiro dejó la cuenta sobregirada"));
        } else {
            cuenta.notificar(new EventoCuenta(TipoEvento.RETIRO, cuenta.getNumero(),
                    monto, nuevoSaldo, "Retiro realizado"));
        }
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
    public String getNombre() { return "ACTIVA"; }
}
