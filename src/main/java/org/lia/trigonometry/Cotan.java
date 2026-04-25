package org.lia.trigonometry;

public class Cotan {

    public static double calculate(double x, double precision) {
        if (Double.isNaN(x) || Double.isNaN(precision) || Double.isInfinite(x) || Double.isInfinite(precision)) {
            throw new IllegalArgumentException("x and precision must be numbers");
        }
        if (precision <= 0.0) {
            throw new IllegalArgumentException("precision must be > 0");
        }

        double cos = Cos.calculate(x, precision);
        if (Math.abs(cos) < precision) {
            throw new ArithmeticException("tan is undefined for this x (cos is too close to zero)");
        }
        double sin = Sin.calculate(x, precision);
        return cos / sin;
    }
}
