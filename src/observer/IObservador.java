package observer;

// Observer: lo implementa quien reacciona a los movimientos de la cuenta.
public interface IObservador {

    void actualizar(EventoCuenta evento);
}
