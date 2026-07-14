package observer;

// Observer concreto: simula enviar un correo al titular ante cualquier movimiento.
public class NotificadorEmail implements IObservador {

    @Override
    public void actualizar(EventoCuenta evento) {
        System.out.printf("[Email] Cuenta %s: %s (saldo %.2f)%n",
                evento.numeroCuenta(), evento.detalle(), evento.saldoResultante());
    }
}
