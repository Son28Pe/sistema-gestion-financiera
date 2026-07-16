package factory;

import java.util.UUID;

import modelo.Transaccion;

// Factory: crea Transaccion generando el id con UUID automáticamente.
public final class FabricaTransacciones {

    private FabricaTransacciones() {}

    public static Transaccion crear(String numeroCuenta, String tipo, double monto) {
        return new Transaccion(UUID.randomUUID().toString(), numeroCuenta, tipo, monto);
    }
}
