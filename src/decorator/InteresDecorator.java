package decorator;

public class InteresDecorator implements ICalculoOperacion {
    
    private final ICalculoOperacion envuelto;
    private final double porcentaje;

    public InteresDecorator(ICalculoOperacion envuelto, double porcentaje) {
        this.envuelto = envuelto;
        this.porcentaje = porcentaje;
    }

    @Override 
    public double calcular(double base) {
        double subtotal = envuelto.calcular(base);
        return subtotal + (subtotal * porcentaje); 
    }
}