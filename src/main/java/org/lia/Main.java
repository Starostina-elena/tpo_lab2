package org.lia;

import org.lia.trigonometry.*;
import org.lia.log.*;

public class Main {
    public static void main(String[] args) {
        Sin sin = new Sin();
        Cos cos = new Cos();
        Tan tan = new Tan(sin, cos);
        Cotan cotan = new Cotan(sin, cos);
        Ln ln = new Ln();
        Log log = new Log(ln);
        SuperCalculator sc = new SuperCalculator(tan, cotan, log, ln);

        sc.printTable(-5, 5, 0.1, "table.csv");
    }
}