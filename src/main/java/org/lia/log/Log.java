package org.lia.log;

public class Log {

    public static double calculate(double x, double precision, double foundation, boolean useTable) {
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

        if (useTable) {
            java.util.OptionalDouble tableValue = tableSearch(x, foundation);
            if (tableValue.isPresent()) {
                return tableValue.getAsDouble();
            }
        }

        double lnX = Ln.calculate(x, precision, useTable);
        double lnF = Ln.calculate(foundation, precision, useTable);

        if (Math.abs(lnF) < Double.MIN_VALUE) {
            throw new ArithmeticException("Logarithm of foundation is too close to zero");
        }

        return lnX / lnF;
    }

    public static java.util.OptionalDouble tableSearch(double x, double foundation) {
        if (Double.isNaN(x) || Double.isNaN(foundation)) {
            return java.util.OptionalDouble.empty();
        }
        if (x <= 0.0 || foundation <= 0.0 || foundation == 1.0) {
            return java.util.OptionalDouble.empty();
        }

        if (x == 1.0) {
            return java.util.OptionalDouble.of(0.0);
        }
        if (x == foundation) {
            return java.util.OptionalDouble.of(1.0);
        }
        if (foundation * foundation == x) {
            return java.util.OptionalDouble.of(2.0);
        }
        if (foundation * foundation * foundation == x) {
            return java.util.OptionalDouble.of(3.0);
        }
        if (x * x == foundation) {
            return java.util.OptionalDouble.of(0.5);
        }

        return java.util.OptionalDouble.empty();
    }
}
