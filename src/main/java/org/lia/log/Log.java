package org.lia.log;

public class Log {

    public static double calculate(double x, double precision, double foundation) {
        if (Double.isNaN(x) || Double.isNaN(precision) || Double.isInfinite(x) || Double.isInfinite(precision)) {
            throw new IllegalArgumentException("x and precision must be numbers");
        }
        if (precision <= 0.0) {
            throw new IllegalArgumentException("precision must be > 0");
        }

        if (x <= 0.0) {
            throw new IllegalArgumentException("x must be > 0");
        }
        if (Double.isNaN(foundation) || Double.isInfinite(foundation) ) {
            throw new IllegalArgumentException("foundation must be a finite number");
        }
        if (foundation <= 0.0) {
            throw new IllegalArgumentException("foundation must be > 0");
        }
        if (foundation == 1.0) {
            throw new IllegalArgumentException("foundation must not be 1");
        }

        double lnX = Ln.calculate(x, precision);
        double lnF = Ln.calculate(foundation, precision);

        if (Math.abs(lnF) < Double.MIN_VALUE) {
            throw new ArithmeticException("Logarithm of foundation is too close to zero");
        }

        return lnX / lnF;
    }
}
