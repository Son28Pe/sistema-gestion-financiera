package factory;

import java.util.UUID;

import modelo.Transaccion;

// Otra Factory chica: creo Transaccion generando el id con UUID automáticamente, para no
// tener que inventar identificadores únicos a mano cada vez que registro un movimiento.
public final class FabricaTransacciones {

    private FabricaTransacciones() {}

    public static Transaccion crear(String numeroCuenta, String tipo, double monto) {
        return new Transaccion(UUID.randomUUID().toString(), numeroCuenta, tipo, monto);
    }
}
