package org.lia.trigonometry;

public class Tan {

    public static double calculate(double x, double precision, boolean useTable) {
        if (Double.isNaN(x) || Double.isNaN(precision) || Double.isInfinite(x) || Double.isInfinite(precision)) {
            throw new IllegalArgumentException("x and precision must be numbers");
        }
        if (precision <= 0.0) {
            throw new IllegalArgumentException("precision must be > 0");
        }

        double xr = Math.IEEEremainder(x, 2 * Math.PI);

        if (useTable) {
            java.util.OptionalDouble tableValue = tableSearch(xr);
            if (tableValue.isPresent()) {
                return tableValue.getAsDouble();
            }
        }

        double cos = Cos.calculate(xr, precision, useTable);
        if (Math.abs(cos) < precision) {
            throw new ArithmeticException("tan is undefined for this x (cos is too close to zero)");
        }
        double sin = Sin.calculate(xr, precision, useTable);
        return sin / cos;
    }

    private static final java.util.Map<Double, Double> TABLE;
    static {
        TABLE = new java.util.HashMap<>();
        TABLE.put(0.0, 0.0);
        TABLE.put(0.5235987755982988, 0.5773502691896257);  // PI/6
        TABLE.put(0.7853981633974483, 1.0);  // PI/4
        TABLE.put(1.0471975511965976, 1.7320508075688772);   // PI/3
    }

    public static java.util.OptionalDouble tableSearch(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return java.util.OptionalDouble.empty();
        }
        Double v = TABLE.get(x);
        return v == null ? java.util.OptionalDouble.empty() : java.util.OptionalDouble.of(v);
    }
}
