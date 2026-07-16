package repositorio;

import java.util.LinkedHashMap;
import java.util.Map;

import factory.FabricaCuentas;
import modelo.Cuenta;
import state.EstadoFactory;
import state.IEstadoCuenta;

// Repositorio JSON de cuentas. Al cargar, reconstruye el estado (texto -> objeto) y el subtipo.
public class RepositorioCuentaJson extends RepositorioJsonBase<Cuenta> {

    public RepositorioCuentaJson() {
        super("data/cuentas.json");
    }

    @Override
    protected String obtenerId(Cuenta c) {
        return c.getNumero();
    }

    @Override
    protected Map<String, String> aMapa(Cuenta c) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("numero", c.getNumero());
        m.put("titularDni", c.getTitularDni());
        m.put("saldo", String.valueOf(c.getSaldo()));
        m.put("estado", c.getEstado().getNombre());
        m.put("tipo", c.getTipo());
        return m;
    }

    @Override
    protected Cuenta desdeMapa(Map<String, String> d) {
        String numero = d.get("numero");
        String titularDni = d.get("titularDni");
        double saldo = Double.parseDouble(d.get("saldo"));
        IEstadoCuenta estado = EstadoFactory.desdeNombre(d.get("estado"));
        // reconstruir() (no crearNueva()): el estado ya viene resuelto del JSON.
        return FabricaCuentas.reconstruir(d.get("tipo"), numero, titularDni, saldo, estado);
    }
}
