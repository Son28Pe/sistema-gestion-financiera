package repositorio;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import interfaces.IRepositorio;

// Base comun de los repositorios JSON (Template Method): concentra la E/S de archivo y el
// CRUD sobre la lista; cada subclase solo define como mapear su entidad a/desde JSON.
public abstract class RepositorioJsonBase<T> implements IRepositorio<T> {

    private final Path archivo;

    protected RepositorioJsonBase(String rutaArchivo) {
        this.archivo = Path.of(rutaArchivo);
    }

    // Ganchos que cada subclase implementa (Template Method).
    protected abstract String obtenerId(T entidad);
    protected abstract Map<String, String> aMapa(T entidad);
    protected abstract T desdeMapa(Map<String, String> datos);

    @Override
    public void guardar(T entidad) {
        List<T> lista = listar();
        lista.add(entidad);
        escribirTodo(lista);
    }

    @Override
    public Optional<T> buscarPorId(String id) {
        return listar().stream()
                .filter(e -> obtenerId(e).equals(id))
                .findFirst();
    }

    @Override
    public List<T> listar() {
        if (!Files.exists(archivo)) return new ArrayList<>();
        List<T> lista = new ArrayList<>();
        for (Map<String, String> datos : JsonUtil.parsearArreglo(leerArchivo())) {
            lista.add(desdeMapa(datos));
        }
        return lista;
    }

    @Override
    public void actualizar(T entidad) {
        List<T> lista = listar();
        String id = obtenerId(entidad);
        for (int i = 0; i < lista.size(); i++) {
            if (obtenerId(lista.get(i)).equals(id)) {
                lista.set(i, entidad);
                escribirTodo(lista);
                return;
            }
        }
    }

    @Override
    public void eliminar(String id) {
        List<T> lista = listar();
        lista.removeIf(e -> obtenerId(e).equals(id));
        escribirTodo(lista);
    }

    private void escribirTodo(List<T> lista) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < lista.size(); i++) {
            sb.append("  ").append(objetoAJson(aMapa(lista.get(i))));
            sb.append(i < lista.size() - 1 ? ",\n" : "\n");
        }
        sb.append("]\n");
        escribirArchivo(sb.toString());
    }

    private String objetoAJson(Map<String, String> campos) {
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, String> e : campos.entrySet()) {
            sb.append('"').append(JsonUtil.escapar(e.getKey())).append("\":\"")
              .append(JsonUtil.escapar(e.getValue())).append('"');
            if (++i < campos.size()) sb.append(',');
        }
        return sb.append('}').toString();
    }

    private String leerArchivo() {
        try {
            return Files.readString(archivo);
        } catch (IOException ex) {
            throw new UncheckedIOException("No se pudo leer " + archivo, ex);
        }
    }

    private void escribirArchivo(String contenido) {
        try {
            if (archivo.getParent() != null) Files.createDirectories(archivo.getParent());
            Files.writeString(archivo, contenido);
        } catch (IOException ex) {
            throw new UncheckedIOException("No se pudo escribir " + archivo, ex);
        }
    }
}
