package org.lia;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SuperCalculatorTest {

    @Test
    void tableValuesCheck() {
        assertEquals(0.0, SuperCalculator.calculate(1.0, 1e-12, true), 1e-9);
        assertEquals(3.0931, SuperCalculator.calculate(3.89677, 1e-12, true), 1e-9);
        assertEquals(1.60547, SuperCalculator.calculate(5.80833, 1e-12, true), 1e-9);
        assertEquals(0.0, SuperCalculator.calculate(0.53856, 1e-12, true), 1e-9);
        assertEquals(-0.33824, SuperCalculator.calculate(0.64377, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        assertEquals(0.0, SuperCalculator.calculate(1.0, 1e-12, false), 1e-4);
        assertEquals(3.0931, SuperCalculator.calculate(3.89677, 1e-12, false), 1e-4);
        assertEquals(1.60547, SuperCalculator.calculate(5.80833, 1e-12, false), 1e-4);
        assertEquals(0.0, SuperCalculator.calculate(0.53856, 1e-12, false), 1e-4);
        assertEquals(-0.33824, SuperCalculator.calculate(0.64377, 1e-12, false), 1e-4);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/pos_values.csv")
    void positiveBranch(double x, double expected) {
        double actual = SuperCalculator.calculate(x, 1e-12, false);
        assertEquals(expected, actual, 1e-9);
    }

    @Test
    void negativeBranch() {
        assertEquals(1, SuperCalculator.calculate(-5, 1e-12, false), 1e-10);
        assertEquals(1, SuperCalculator.calculate(-10, 1e-12, false), 1e-10);
    }

    @Test
    void invalidArgumentsPropagate() {
        assertThrows(IllegalArgumentException.class, () -> SuperCalculator.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> SuperCalculator.calculate(1.0, 0.0, false));
        assertThrows(IllegalArgumentException.class, () -> SuperCalculator.calculate(Double.POSITIVE_INFINITY, 1e-10, false));
    }

    @Test
    void tableSearchEdgeCases() {
        assertTrue(SuperCalculator.tableSearch(Double.NaN).isEmpty());
        assertTrue(SuperCalculator.tableSearch(Double.POSITIVE_INFINITY).isEmpty());

        assertTrue(SuperCalculator.tableSearch(1.0).isPresent());
        assertEquals(0.0, SuperCalculator.tableSearch(1.0).getAsDouble(), 1e-12);
        assertTrue(SuperCalculator.tableSearch(3.89677).isPresent());
        assertEquals(3.0931, SuperCalculator.tableSearch(3.89677).getAsDouble(), 1e-8);
    }
}
