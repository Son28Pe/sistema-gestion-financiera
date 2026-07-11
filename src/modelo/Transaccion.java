package modelo;

import java.time.LocalDateTime;

// STUB temporal - lo reemplaza Persona 1
public class Transaccion {

    private final String id;
    private final String numeroCuenta;
    private final String tipo;      // p. ej. DEPOSITO, RETIRO
    private final double monto;
    private final LocalDateTime fecha;

    public Transaccion(String id, String numeroCuenta, String tipo, double monto) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }
}
