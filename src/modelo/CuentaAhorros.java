package modelo;

import state.IEstadoCuenta;

// STUB temporal - lo reemplaza Persona 1
public class CuentaAhorros extends Cuenta {

    public CuentaAhorros(String numero, String titularDni, double saldoInicial,
                         IEstadoCuenta estadoInicial) {
        super(numero, titularDni, saldoInicial, estadoInicial);
    }

    @Override
    public String getTipo() { return "AHORROS"; }
}
