package singleton;

// Singleton: configuración global única del sistema (inicialización eager).
public final class ConfiguracionGlobal {

    private static final ConfiguracionGlobal INSTANCIA = new ConfiguracionGlobal();

    private String nombreBanco;
    private String moneda;
    private double limiteSobregiro;

    private ConfiguracionGlobal() {
        // Valores por defecto del sistema.
        this.nombreBanco = "Banco Diseno de Patrones";
        this.moneda = "PEN";
        this.limiteSobregiro = -500.0;
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
