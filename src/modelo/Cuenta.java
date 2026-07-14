package modelo;

import java.util.ArrayList;
import java.util.List;

import observer.EventoCuenta;
import observer.IObservador;
import observer.ISujeto;
import state.IEstadoCuenta;

// STUB temporal - lo reemplaza Persona 1.
// Persona 1: conservar el campo IEstadoCuenta + delegacion (State) y la lista de
// IObservador + notificar() (Observer): son la base de los patrones de Persona 2.
public abstract class Cuenta implements ISujeto {

    private final String numero;        // identificador
    private final String titularDni;
    private double saldo;

    private IEstadoCuenta estado;                                     // State
    private final List<IObservador> observadores = new ArrayList<>(); // Observer

    protected Cuenta(String numero, String titularDni, double saldoInicial,
                     IEstadoCuenta estadoInicial) {
        this.numero = numero;
        this.titularDni = titularDni;
        this.saldo = saldoInicial;
        this.estado = estadoInicial;
    }

    // Operaciones: la Cuenta delega en su estado (State).
    public void depositar(double monto) { estado.depositar(this, monto); }
    public void retirar(double monto)   { estado.retirar(this, monto); }
    public void bloquear()              { estado.bloquear(this); }
    public void desbloquear()           { estado.desbloquear(this); }
    public void cerrar()                { estado.cerrar(this); }

    // Observer: administra observadores y notifica sin conocerlos.
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
}
