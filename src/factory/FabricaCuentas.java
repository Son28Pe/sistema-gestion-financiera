package factory;

import excepciones.OperacionInvalidaException;
import modelo.Cuenta;
import modelo.CuentaAhorros;
import modelo.CuentaCorriente;
import state.EstadoActiva;
import state.IEstadoCuenta;

// Factory de cuentas: centraliza la creación según el "tipo", aislando a controlador y
// repositorio de las subclases concretas.
//  - crearNueva:  cuenta nueva -> arranca en EstadoActiva.
//  - reconstruir: al leer de JSON -> conserva el estado ya resuelto.
public final class FabricaCuentas {

    private FabricaCuentas() {}

    public static Cuenta crearNueva(String tipo, String numero, String titularDni, double saldoInicial) {
        return construir(tipo, numero, titularDni, saldoInicial, new EstadoActiva());
    }

    public static Cuenta reconstruir(String tipo, String numero, String titularDni,
                                     double saldo, IEstadoCuenta estado) {
        return construir(tipo, numero, titularDni, saldo, estado);
    }

    private static Cuenta construir(String tipo, String numero, String titularDni,
                                    double saldo, IEstadoCuenta estado) {
        return switch (tipo) {
            case "AHORROS"   -> new CuentaAhorros(numero, titularDni, saldo, estado);
            case "CORRIENTE" -> new CuentaCorriente(numero, titularDni, saldo, estado);
            default -> throw new OperacionInvalidaException("Tipo de cuenta no soportado: " + tipo);
        };
    }
}
