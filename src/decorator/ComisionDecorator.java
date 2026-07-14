package decorator;

public class ComisionDecorator implements ICalculoOperacion {
    
    private final ICalculoOperacion envuelto;
    private final double comision;

    public ComisionDecorator(ICalculoOperacion envuelto, double comision) {
        this.envuelto = envuelto;
        this.comision = comision;
    }

    @Override 
    public double calcular(double base) {
        return envuelto.calcular(base) + comision; 
    }
}