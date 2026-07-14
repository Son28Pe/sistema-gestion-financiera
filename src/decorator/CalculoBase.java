package decorator;

public class CalculoBase implements ICalculoOperacion {
    @Override
    public double calcular(double base) {
        return base;
    }
}