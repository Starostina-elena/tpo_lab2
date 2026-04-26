package org.lia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.lia.trigonometry.*;
import org.lia.log.*;

public class IntegrationSuperCalculatorIT {

    @ParameterizedTest
    @CsvFileSource(resources = "/pos_values.csv")
    void positiveBranchIntegration(double x, double expected) {
        Sin sin = new Sin();
        Cos cos = new Cos();
        Tan tan = new Tan(sin, cos);
        Cotan cotan = new Cotan(sin, cos);
        Ln ln = new Ln();
        Log log = new Log(ln);
        SuperCalculator sc = new SuperCalculator(tan, cotan, log, ln);

        double actual = sc.calculate(x, 1e-12, false);
        assertEquals(expected, actual, 1e-9);
    }

    @Test
    void negativeBranchIntegration() {
        Sin sin = new Sin();
        Cos cos = new Cos();
        Tan tan = new Tan(sin, cos);
        Cotan cotan = new Cotan(sin, cos);
        Ln ln = new Ln();
        Log log = new Log(ln);
        SuperCalculator sc = new SuperCalculator(tan, cotan, log, ln);

        assertEquals(1.0, sc.calculate(-5.0, 1e-12, false), 1e-10);
        assertEquals(1.0, sc.calculate(-10.0, 1e-12, false), 1e-10);
    }
}
