package org.lia.trigonometry;

public class Cos {

    public static double calculate(double x, double precision) {
        if (Double.isNaN(x) || Double.isNaN(precision) || Double.isInfinite(x) || Double.isInfinite(precision)) {
            throw new IllegalArgumentException("x and precision must be numbers");
        }
        if (precision <= 0.0) {
            throw new IllegalArgumentException("precision must be > 0");
        }

        double xr = Math.IEEEremainder(x, 2 * Math.PI);

        // члены ряда: term_0 = 1, term_{n+1} = -term_n * xr^2 / ((2n+1)*(2n+2))
        double term = 1.0;
        double sum = term;
        int n = 0;
        final int MAX_ITER = 1_000_000;

        while (Math.abs(term) >= precision) {
            if (n >= MAX_ITER) {
                throw new ArithmeticException("Series did not converge within maximum iterations");
            }
            double denom = (2.0 * n + 1) * (2.0 * n + 2);
            term = -term * xr * xr / denom;
            sum += term;
            n++;
        }

        return sum;
    }
}
