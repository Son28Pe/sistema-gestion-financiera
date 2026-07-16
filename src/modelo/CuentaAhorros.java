package modelo;

import state.IEstadoCuenta;

// Cuenta de ahorros. Las reglas de estado/sobregiro las maneja el paquete state/.
public class CuentaAhorros extends Cuenta {

    public CuentaAhorros(String numero, String titularDni, double saldoInicial,
                         IEstadoCuenta estadoInicial) {
        super(numero, titularDni, saldoInicial, estadoInicial);
    }

    @Override
    public String getTipo() { return "AHORROS"; }
}
