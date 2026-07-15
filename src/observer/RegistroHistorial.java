package observer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import repositorio.JsonUtil;

// Observer concreto: acumula el historial de eventos en memoria y los persiste en JSON.
public class RegistroHistorial implements IObservador {

    private final Path archivo = Path.of("data/historial.json");
    private final List<EventoCuenta> historial = new ArrayList<>();

    public RegistroHistorial() {
        cargarHistorial();
    }

    @Override
    public synchronized void actualizar(EventoCuenta evento) {
        historial.add(evento);
        guardarHistorial();
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

    private void cargarHistorial() {
        if (!Files.exists(archivo)) return;
        try {
            String json = Files.readString(archivo);
            List<Map<String, String>> arreglo = JsonUtil.parsearArreglo(json);
            historial.clear();
            for (Map<String, String> datos : arreglo) {
                EventoCuenta.TipoEvento tipo = EventoCuenta.TipoEvento.valueOf(datos.get("tipo"));
                String numeroCuenta = datos.get("numeroCuenta");
                double monto = Double.parseDouble(datos.get("monto"));
                double saldoResultante = Double.parseDouble(datos.get("saldoResultante"));
                LocalDateTime fecha = LocalDateTime.parse(datos.get("fecha"));
                String detalle = datos.get("detalle");
                historial.add(new EventoCuenta(tipo, numeroCuenta, monto, saldoResultante, fecha, detalle));
            }
        } catch (IOException ex) {
            // Si hay error, simplemente ignoramos y empezamos con historial vacío
        }
    }

    private void guardarHistorial() {
        try {
            if (archivo.getParent() != null) Files.createDirectories(archivo.getParent());
            StringBuilder sb = new StringBuilder("[\n");
            for (int i = 0; i < historial.size(); i++) {
                EventoCuenta e = historial.get(i);
                sb.append("  {");
                sb.append("\"tipo\":\"").append(e.tipo().name()).append("\",");
                sb.append("\"numeroCuenta\":\"").append(JsonUtil.escapar(e.numeroCuenta())).append("\",");
                sb.append("\"monto\":\"").append(e.monto()).append("\",");
                sb.append("\"saldoResultante\":\"").append(e.saldoResultante()).append("\",");
                sb.append("\"fecha\":\"").append(e.fecha().toString()).append("\",");
                sb.append("\"detalle\":\"").append(JsonUtil.escapar(e.detalle())).append("\"");
                sb.append("}");
                if (i < historial.size() - 1) sb.append(",\n");
                else sb.append("\n");
            }
            sb.append("]\n");
            Files.writeString(archivo, sb.toString());
        } catch (IOException ex) {
            throw new UncheckedIOException("No se pudo escribir el historial " + archivo, ex);
        }
    }
}
