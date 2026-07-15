package singleton;

// Mi Singleton: configuración global de la app, una sola instancia compartida por todo
// el sistema (límites de negocio, moneda, nombre de la entidad, etc.). Uso inicialización
// eager porque es la forma más simple y evita el problema clásico de doble verificación
// con inicialización perezosa (no es que aquí importe la concurrencia, ya que el proyecto
// es de consola, pero así queda bien hecho de una vez).
public final class ConfiguracionGlobal {

    private static final ConfiguracionGlobal INSTANCIA = new ConfiguracionGlobal();

    private String nombreBanco;
    private String moneda;
    private double limiteSobregiro;

    private ConfiguracionGlobal() {
        // Valores por defecto del sistema.
        this.nombreBanco = "Banco Diseno de Patrones";
        this.moneda = "PEN";
        this.limiteSobregiro = -500.0; // mismo valor que usaba EstadoActiva como provisional
    }

    public static ConfiguracionGlobal getInstancia() {
        return INSTANCIA;
    }

    public String getNombreBanco() { return nombreBanco; }
    public void setNombreBanco(String nombreBanco) { this.nombreBanco = nombreBanco; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public double getLimiteSobregiro() { return limiteSobregiro; }
    public void setLimiteSobregiro(double limiteSobregiro) { this.limiteSobregiro = limiteSobregiro; }
}
