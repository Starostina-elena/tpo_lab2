package org.lia.trigonometry;

public class Cotan {

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

        double sin = Sin.calculate(xr, precision, useTable);
        if (Math.abs(sin) < precision) {
            throw new ArithmeticException("cotan is undefined for this x (sin is too close to zero)");
        }
        double cos = Cos.calculate(xr, precision, useTable);
        return cos / sin ;
    }

    private static final java.util.Map<Double, Double> TABLE;
    static {
        TABLE = new java.util.HashMap<>();
        TABLE.put(0.5235987755982988, 1.7320508075688772);  // PI/6 -> sqrt(3)
        TABLE.put(0.7853981633974483, 1.0);                // PI/4 -> 1
        TABLE.put(1.0471975511965976, 0.5773502691896257); // PI/3 -> 1/sqrt(3)
    }

    public static java.util.OptionalDouble tableSearch(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return java.util.OptionalDouble.empty();
        }
        Double v = TABLE.get(x);
        return v == null ? java.util.OptionalDouble.empty() : java.util.OptionalDouble.of(v);
    }

    public static void printTable(double start, double end, double step, String filename) {
        if (Double.isNaN(start) || Double.isNaN(end) || Double.isNaN(step) ||
            Double.isInfinite(start) || Double.isInfinite(end) || Double.isInfinite(step)) {
            throw new IllegalArgumentException("start, end, and step must be finite numbers");
        }
        if (step <= 0.0) {
            throw new IllegalArgumentException("step must be > 0");
        }
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("filename must be provided");
        }

        java.io.File f = new java.io.File(filename);
        java.io.File parent = f.getAbsoluteFile().getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(f))) {
            pw.println("X, Результаты модуля (X)");
            for (double x = start; x <= end; x += step) {
                double v = calculate(x, 1e-10, true);
                pw.printf("%s,%s%n", x, v);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to write CSV file: " + filename, e);
        }
    }
}
