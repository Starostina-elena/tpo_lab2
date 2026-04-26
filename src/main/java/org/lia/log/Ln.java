package org.lia.log;

public class Ln {

    public double calculate(double x, double precision, boolean useTable) {
        if (Double.isNaN(x) || Double.isNaN(precision) || Double.isInfinite(x) || Double.isInfinite(precision)) {
            throw new IllegalArgumentException("x and precision must be numbers");
        }
        if (precision <= 0.0) {
            throw new IllegalArgumentException("precision must be > 0");
        }
        if (x <= 0.0) {
            throw new IllegalArgumentException("x must be > 0 for natural logarithm");
        }

        if (useTable) {
            java.util.OptionalDouble tableValue = tableSearch(x);
            if (tableValue.isPresent()) {
                return tableValue.getAsDouble();
            }
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

    private static final java.util.Map<Double, Double> TABLE;
    static {
        TABLE = new java.util.HashMap<>();
        TABLE.put(1.0, 0.0);
        TABLE.put(2.718281828459045, 1.0); // e
        TABLE.put(2.0, 0.6931471805599453); // ln(2)
        TABLE.put(0.5, -0.6931471805599453); // ln(0.5) = -ln2
    }

    public java.util.OptionalDouble tableSearch(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            return java.util.OptionalDouble.empty();
        }
        if (x <= 0.0) {
            return java.util.OptionalDouble.empty();
        }
        Double v = TABLE.get(x);
        return v == null ? java.util.OptionalDouble.empty() : java.util.OptionalDouble.of(v);
    }

    public void printTable(double start, double end, double step, String filename) {
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
                if (x <= 0.0) {
                    pw.printf("%s,%s%n", x, "NaN");
                    continue;
                }
                double v = calculate(x, 1e-10, true);
                pw.printf("%s,%s%n", x, v);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to write CSV file: " + filename, e);
        }
    }
}
