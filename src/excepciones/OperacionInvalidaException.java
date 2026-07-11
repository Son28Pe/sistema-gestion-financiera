package excepciones;

// Operación no válida para el estado actual de la cuenta (saldo insuficiente,
// cuenta bloqueada/cerrada, etc.). Unchecked: la captura el controlador.
public class OperacionInvalidaException extends RuntimeException {

    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
