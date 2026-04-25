package org.lia;

import org.lia.trigonometry.Tan;
import org.lia.trigonometry.Cotan;

import org.lia.log.Log;
import org.lia.log.Ln;

public class SuperCalculator {

    public static double calculate(double x, double precision) {
        if (x <= 0) {
            double tan = Tan.calculate(x, precision);
            double cotan = Cotan.calculate(x, precision);
            return (tan * cotan) * (tan * cotan);
        } else {
            double lnX = Ln.calculate(x, precision);
            double log3X = Log.calculate(x, precision, 3);
            double log5X = Log.calculate(x, precision, 5);

            double a = log5X + log3X;
            double b = (lnX - log3X * log5X);
            double c = a * a * b;

            return c * c + log3X;
        }
    }
}
