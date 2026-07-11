package observer;

import java.util.ArrayList;
import java.util.List;

// Observer concreto: acumula el historial de eventos en memoria.
public class RegistroHistorial implements IObservador {

    private final List<EventoCuenta> historial = new ArrayList<>();

    @Override
    public void actualizar(EventoCuenta evento) {
        historial.add(evento);
    }

    public List<EventoCuenta> getHistorial() {
        return List.copyOf(historial); // copia inmutable, no expone la lista interna
    }

    public void imprimir() {
        System.out.println("--- Historial (" + historial.size() + " eventos) ---");
        for (EventoCuenta e : historial) {
            System.out.printf("  %s | %-12s | %s%n", e.fecha(), e.tipo(), e.detalle());
        }
    }
}
