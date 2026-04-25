package org.lia.log;

public class Ln {

    public static double calculate(double x, double precision) {
        if (Double.isNaN(x) || Double.isNaN(precision) || Double.isInfinite(x) || Double.isInfinite(precision)) {
            throw new IllegalArgumentException("x and precision must be numbers");
        }
        if (precision <= 0.0) {
            throw new IllegalArgumentException("precision must be > 0");
        }
        if (x <= 0.0) {
            throw new IllegalArgumentException("x must be > 0 for natural logarithm");
        }

        double t = (x - 1.0) / (x + 1.0);
        double term = t; // t^(2n+1)
        double sum = 0.0;
        int n = 0;
        final int MAX_ITER = 1_000_000;

        while (true) {
            double add = 2.0 * term / (2.0 * n + 1.0);
            if (Math.abs(add) < precision) {
                break;
            }
            if (n >= MAX_ITER) {
                throw new ArithmeticException("Series did not converge within maximum iterations");
            }
            sum += add;
            term *= t * t;
            n++;
        }

        return sum;
    }
}
