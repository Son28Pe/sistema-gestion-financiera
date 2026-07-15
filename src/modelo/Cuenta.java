package modelo;

import java.util.ArrayList;
import java.util.List;

import observer.EventoCuenta;
import observer.IObservador;
import observer.ISujeto;
import state.IEstadoCuenta;
import util.Validador;

// Reemplazo el stub que había en el repo, pero dejo intacto el contrato que necesita mi
// compañero de State y Observer: el campo IEstadoCuenta con la delegación en
// depositar/retirar/bloquear/desbloquear/cerrar, la implementación de ISujeto (lista de
// observadores + notificar) y todos los getters/setters que ya se usan en controlador/ y
// repositorio/. No cambio ninguna firma pública, solo agrego validación en el constructor
// y equals/hashCode/toString para que sea más fácil depurar.
public abstract class Cuenta implements ISujeto {

    private final String numero;        // identificador
    private final String titularDni;
    private double saldo;

    private IEstadoCuenta estado;                                     // State
    private final List<IObservador> observadores = new ArrayList<>(); // Observer

    protected Cuenta(String numero, String titularDni, double saldoInicial,
                     IEstadoCuenta estadoInicial) {
        Validador.validarTexto(numero, "numero");
        Validador.validarTexto(titularDni, "titularDni");
        Validador.validarNoNulo(estadoInicial, "estadoInicial");
        this.numero = numero;
        this.titularDni = titularDni;
        this.saldo = saldoInicial;
        this.estado = estadoInicial;
    }

    // Operaciones: la cuenta delega en su estado actual (State).
    public void depositar(double monto) { estado.depositar(this, monto); }
    public void retirar(double monto)   { estado.retirar(this, monto); }
    public void bloquear()              { estado.bloquear(this); }
    public void desbloquear()           { estado.desbloquear(this); }
    public void cerrar()                { estado.cerrar(this); }

    // Observer: administro la lista de observadores y notifico sin conocerlos.
    @Override public void agregarObservador(IObservador observador) { observadores.add(observador); }
    @Override public void removerObservador(IObservador observador) { observadores.remove(observador); }

    @Override
    public void notificar(EventoCuenta evento) {
        for (IObservador observador : observadores) {
            observador.actualizar(evento);
        }
    }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public IEstadoCuenta getEstado() { return estado; }
    public void setEstado(IEstadoCuenta estado) { this.estado = estado; }

    public String getNumero() { return numero; }
    public String getTitularDni() { return titularDni; }

    public abstract String getTipo(); // para mostrar y persistir

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuenta otra)) return false;
        return numero.equals(otra.numero);
    }

    @Override
    public int hashCode() { return numero.hashCode(); }

    @Override
    public String toString() {
        return "Cuenta{numero='" + numero + "', tipo='" + getTipo() + "', titularDni='" + titularDni
                + "', saldo=" + saldo + ", estado=" + estado.getNombre() + "}";
    }
}
