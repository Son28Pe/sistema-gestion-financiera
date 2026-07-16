package modelo;

import state.IEstadoCuenta;

// Segundo tipo de cuenta: da sentido al polimorfismo de FabricaCuentas.
public class CuentaCorriente extends Cuenta {

    public CuentaCorriente(String numero, String titularDni, double saldoInicial,
                           IEstadoCuenta estadoInicial) {
        super(numero, titularDni, saldoInicial, estadoInicial);
    }

    @Override
    public String getTipo() { return "CORRIENTE"; }
}
