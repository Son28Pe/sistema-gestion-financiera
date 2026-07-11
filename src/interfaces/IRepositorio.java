package interfaces;

import java.util.List;
import java.util.Optional;

// Contrato de persistencia (DIP): el controlador depende de esta interfaz, no del JSON.
public interface IRepositorio<T> {

    void guardar(T entidad);

    Optional<T> buscarPorId(String id); // Optional evita devolver null

    List<T> listar();

    void actualizar(T entidad);

    void eliminar(String id);
}
