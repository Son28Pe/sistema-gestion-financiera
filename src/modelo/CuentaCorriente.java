package modelo;

import state.IEstadoCuenta;

// Segundo tipo de cuenta, para que mi Factory (factory/FabricaCuentas) tenga sentido y
// muestre polimorfismo real, no solo un único tipo disfrazado de patrón.
// La dejo estructuralmente igual a CuentaAhorros porque el límite de sobregiro y las
// transiciones de estado las decide state/ de forma genérica; si más adelante quiero un
// límite distinto por tipo de cuenta, tendría que coordinar ese cambio con quien maneja
// EstadoActiva.
public class CuentaCorriente extends Cuenta {

    public CuentaCorriente(String numero, String titularDni, double saldoInicial,
                           IEstadoCuenta estadoInicial) {
        super(numero, titularDni, saldoInicial, estadoInicial);
    }

    @Override
    public String getTipo() { return "CORRIENTE"; }
}
