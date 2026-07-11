package repositorio;

import java.util.LinkedHashMap;
import java.util.Map;

import modelo.Cliente;

// Repositorio JSON de clientes. Solo mapea la entidad; la E/S y el CRUD los pone la base.
public class RepositorioClienteJson extends RepositorioJsonBase<Cliente> {

    public RepositorioClienteJson() {
        super("data/clientes.json");
    }

    @Override
    protected String obtenerId(Cliente c) {
        return c.getDni();
    }

    @Override
    protected Map<String, String> aMapa(Cliente c) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("dni", c.getDni());
        m.put("nombre", c.getNombre());
        m.put("correo", c.getCorreo());
        return m;
    }

    @Override
    protected Cliente desdeMapa(Map<String, String> d) {
        return new Cliente(d.get("dni"), d.get("nombre"), d.get("correo"));
    }
}
