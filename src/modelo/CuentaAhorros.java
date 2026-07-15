package modelo;

import state.IEstadoCuenta;

// Cuenta de ahorros. Por diseño de producto no tiene nada especial más allá del tipo:
// las reglas de sobregiro/estado las maneja el paquete state/ de forma genérica.
public class CuentaAhorros extends Cuenta {

    public CuentaAhorros(String numero, String titularDni, double saldoInicial,
                         IEstadoCuenta estadoInicial) {
        super(numero, titularDni, saldoInicial, estadoInicial);
    }

    @Override
    public String getTipo() { return "AHORROS"; }
}
