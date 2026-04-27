package org.lia.log;

public class Log {

    private final Ln ln;

    public Log(Ln ln) {
        this.ln = ln;
    }

    public double calculate(double x, double precision, double foundation, boolean useTable) {
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

        double lnX = ln.calculate(x, precision, useTable);
        double lnF = ln.calculate(foundation, precision, useTable);

        if (Math.abs(lnF) < Double.MIN_VALUE || lnF == 0.0) {
            double fallback = Math.log(foundation);
            if (Math.abs(fallback) < Double.MIN_VALUE) {
                throw new ArithmeticException("Logarithm of foundation is too close to zero");
            }
            lnF = fallback;
        }

        return lnX / lnF;
    }

    public java.util.OptionalDouble tableSearch(double x, double foundation) {
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

    public void printTable(double start, double end, double step, double foundation, String filename) {
        if (Double.isNaN(start) || Double.isNaN(end) || Double.isNaN(step) || Double.isNaN(foundation) ||
            Double.isInfinite(start) || Double.isInfinite(end) || Double.isInfinite(step) || Double.isInfinite(foundation)) {
            throw new IllegalArgumentException("start, end, step and foundation must be finite numbers");
        }
        if (step <= 0.0) {
            throw new IllegalArgumentException("step must be > 0");
        }
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("filename must be provided");
        }
        if (foundation <= 0.0 || foundation == 1.0) {
            throw new IllegalArgumentException("foundation must be > 0 and != 1");
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
                try {
                    double v = calculate(x, 1e-10, foundation, true);
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
