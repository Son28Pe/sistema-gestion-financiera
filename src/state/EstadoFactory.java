package state;

import excepciones.OperacionInvalidaException;

// Reconstruye el estado a partir de su nombre (para cargar cuentas desde JSON).
public final class EstadoFactory {

    private EstadoFactory() {}

    public static IEstadoCuenta desdeNombre(String nombre) {
        return switch (nombre) {
            case "ACTIVA"      -> new EstadoActiva();
            case "SOBREGIRADA" -> new EstadoSobregirada();
            case "BLOQUEADA"   -> new EstadoBloqueada();
            case "CERRADA"     -> new EstadoCerrada();
            default -> throw new OperacionInvalidaException("Estado desconocido: " + nombre);
        };
    }
}
