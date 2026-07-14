package observer;

// Observer concreto: solo reacciona a movimientos sospechosos (retiros grandes o sobregiros).
public class AlertaFraude implements IObservador {

    private static final double UMBRAL_MONTO = 1000.0;

    @Override
    public void actualizar(EventoCuenta evento) {
        boolean retiroGrande = evento.tipo() == EventoCuenta.TipoEvento.RETIRO
                && evento.monto() >= UMBRAL_MONTO;
        boolean sobregiro = evento.tipo() == EventoCuenta.TipoEvento.SOBREGIRO;
        if (retiroGrande || sobregiro) {
            System.out.printf("[ALERTA FRAUDE] Cuenta %s: %s (monto %.2f)%n",
                    evento.numeroCuenta(), evento.detalle(), evento.monto());
        }
    }
}
