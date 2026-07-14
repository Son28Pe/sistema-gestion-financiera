package state;

import modelo.Cuenta;

// Patrón State: la Cuenta delega cada operación en su estado actual (OCP + polimorfismo).
// Cada estado decide su propia transición (ej. retiro en negativo -> EstadoSobregirada).
public interface IEstadoCuenta {

    void depositar(Cuenta cuenta, double monto);

    void retirar(Cuenta cuenta, double monto);

    void bloquear(Cuenta cuenta);

    void desbloquear(Cuenta cuenta);

    void cerrar(Cuenta cuenta);

    String getNombre();
}
