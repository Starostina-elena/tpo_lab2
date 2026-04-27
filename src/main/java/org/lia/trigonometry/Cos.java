package org.lia.trigonometry;

public class Cos {

    public double calculate(double x, double precision, boolean useTable) {
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

    private static final java.util.Map<Double, Double> TABLE;
    static {
        TABLE = new java.util.HashMap<>();
        TABLE.put(0.0, 1.0);
        TABLE.put(0.5235987755982988, 0.8660254037844386); // PI/6
        TABLE.put(0.7853981633974483, 0.7071067811865476); // PI/4
        TABLE.put(1.0471975511965976, 0.5); // PI/3
        TABLE.put(1.5707963267948966, 0.0); // PI/2
        TABLE.put(3.141592653589793, -1.0); // PI
    }

    public java.util.OptionalDouble tableSearch(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
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
                double v = calculate(x, 1e-10, true);
                pw.printf("%s,%s%n", x, v);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to write CSV file: " + filename, e);
        }
    }
}
