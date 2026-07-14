package observer;

import java.time.LocalDateTime;

// Observer (push): datos inmutables del evento que la Cuenta envía a sus observadores.
public record EventoCuenta(
        TipoEvento tipo,
        String numeroCuenta,
        double monto,
        double saldoResultante,
        LocalDateTime fecha,
        String detalle) {

    public enum TipoEvento {
        DEPOSITO, RETIRO, SOBREGIRO, CAMBIO_ESTADO, BLOQUEO, CIERRE
    }

    // Constructor de conveniencia: fecha = ahora.
    public EventoCuenta(TipoEvento tipo, String numeroCuenta, double monto,
                        double saldoResultante, String detalle) {
        this(tipo, numeroCuenta, monto, saldoResultante, LocalDateTime.now(), detalle);
    }
}
