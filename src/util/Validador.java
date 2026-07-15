package util;

import java.util.Optional;

import excepciones.OperacionInvalidaException;

// GRASP Fabricacion Pura: agrupa validaciones reutilizables e independientes del estado.
// No es una entidad del dominio; existe para no ensuciar el controlador ni duplicar reglas.
public final class Validador {

    private Validador() {} // utilidad sin estado: no se instancia

    public static void validarMonto(double monto) {
        if (monto <= 0) {
            throw new OperacionInvalidaException("El monto debe ser mayor a cero.");
        }
    }

    public static void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new OperacionInvalidaException("El campo '" + campo + "' es obligatorio.");
        }
    }

    public static void validarDni(String dni) {
        validarTexto(dni, "DNI");
        if (!dni.matches("\\d{8}")) {
            throw new OperacionInvalidaException("El DNI debe contener exactamente 8 números.");
        }
    }

    public static void validarEmail(String email) {
        validarTexto(email, "correo");
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new OperacionInvalidaException("El correo electrónico no tiene un formato válido.");
        }
    }

    public static void validarNoNulo(Object obj, String nombre) {
        if (obj == null) {
            throw new OperacionInvalidaException("'" + nombre + "' no puede ser nulo.");
        }
    }

    // Devuelve la entidad si existe; si no, lanza. Uso: requerirExistencia(repo.buscarPorId(id), "cuenta " + id)
    public static <T> T requerirExistencia(Optional<T> encontrado, String descripcion) {
        return encontrado.orElseThrow(
                () -> new OperacionInvalidaException("No existe: " + descripcion + "."));
    }
}
