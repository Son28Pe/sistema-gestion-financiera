package factory;

import excepciones.OperacionInvalidaException;
import modelo.Cuenta;
import modelo.CuentaAhorros;
import modelo.CuentaCorriente;
import state.EstadoActiva;
import state.IEstadoCuenta;

// Mi Factory: centralizo aquí la creación de Cuenta según su "tipo" (String), para que
// ni el controlador ni el repositorio tengan que conocer las subclases concretas. Así,
// si mañana agrego un tipo de cuenta nuevo, solo toco este archivo (no ControladorCuenta
// ni RepositorioCuentaJson).
//
// Expongo dos métodos porque tengo dos escenarios distintos:
//  - crearNueva:  alta de una cuenta nueva -> siempre arranca en EstadoActiva.
//  - reconstruir: se usa al leer desde JSON -> el estado ya viene resuelto, no lo reinicio.
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
