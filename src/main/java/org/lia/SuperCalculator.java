package org.lia;

import org.lia.trigonometry.Tan;
import org.lia.trigonometry.Cotan;

import org.lia.log.Log;
import org.lia.log.Ln;

public class SuperCalculator {

    public static double calculate(double x, double precision, boolean useTable) {
        if (useTable) {
            java.util.OptionalDouble tableValue = tableSearch(x);
            if (tableValue.isPresent()) return tableValue.getAsDouble();
        }

        if (x <= 0) {
            double tan = Tan.calculate(x, precision, useTable);
            double cotan = Cotan.calculate(x, precision, useTable);
            return (tan * cotan) * (tan * cotan);
        } else {
            double lnX = Ln.calculate(x, precision, useTable);
            double log3X = Log.calculate(x, precision, 3, useTable);
            double log5X = Log.calculate(x, precision, 5, useTable);

            double a = log5X + log3X;
            double b = (lnX - log3X * log5X);
            double c = a * a * b;

            return c * c + log3X;
        }
    }

    private static final java.util.Map<Double, Double> TABLE;
    static {
        TABLE = new java.util.HashMap<>();
        TABLE.put(0.53856, 0.0);
        TABLE.put(0.64377, -0.33824);
        TABLE.put(1.0, 0.0);
        TABLE.put(3.89677, 3.0931);
        TABLE.put(5.80833, 1.60547);
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
                try {
                    double v = calculate(x, 1e-10, true);
                    pw.printf("%s,%s%n", x, v);
                } catch (Exception e) {
                    pw.printf("%s,%s%n", x, "NaN");
                }
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to write CSV file: " + filename, e);
        }
    }
}