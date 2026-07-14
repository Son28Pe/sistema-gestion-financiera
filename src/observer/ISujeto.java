package observer;

// Observer: contrato del sujeto observable (lo implementa Cuenta): administra y notifica observadores.
public interface ISujeto {

    void agregarObservador(IObservador observador);

    void removerObservador(IObservador observador);

    void notificar(EventoCuenta evento);
}
