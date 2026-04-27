package org.lia;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import org.lia.trigonometry.Tan;
import org.lia.trigonometry.Cotan;

public class SuperCalculatorTest {

    @Test
    void tableValuesCheck() {
        Tan mockTan = mock(Tan.class);
        Cotan mockCotan = mock(Cotan.class);
        org.lia.log.Log mockLog = mock(org.lia.log.Log.class);
        org.lia.log.Ln mockLn = mock(org.lia.log.Ln.class);
        SuperCalculator sc = new SuperCalculator(mockTan, mockCotan, mockLog, mockLn);

        assertEquals(0.0, sc.calculate(1.0, 1e-12, true), 1e-9);
        assertEquals(3.0931, sc.calculate(3.89677, 1e-12, true), 1e-9);
        assertEquals(1.60547, sc.calculate(5.80833, 1e-12, true), 1e-9);
        assertEquals(0.0, sc.calculate(0.53856, 1e-12, true), 1e-9);
        assertEquals(-0.33824, sc.calculate(0.64377, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        Tan mockTan = mock(Tan.class);
        Cotan mockCotan = mock(Cotan.class);
        org.lia.log.Log mockLog = mock(org.lia.log.Log.class);
        org.lia.log.Ln mockLn = mock(org.lia.log.Ln.class);

        when(mockLn.calculate(anyDouble(), anyDouble(), anyBoolean()))
            .thenAnswer(inv -> Math.log(inv.getArgument(0, Double.class)));
        when(mockLog.calculate(anyDouble(), anyDouble(), anyDouble(), anyBoolean()))
            .thenAnswer(inv -> {
                double x = inv.getArgument(0, Double.class);
                double base = inv.getArgument(2, Double.class);
                return Math.log(x) / Math.log(base);
            });

        SuperCalculator sc = new SuperCalculator(mockTan, mockCotan, mockLog, mockLn);

        assertEquals(0.0, sc.calculate(1.0, 1e-12, false), 1e-4);
        assertEquals(3.0931, sc.calculate(3.89677, 1e-12, false), 1e-4);
        assertEquals(1.60547, sc.calculate(5.80833, 1e-12, false), 1e-4);
        assertEquals(0.0, sc.calculate(0.53856, 1e-12, false), 1e-4);
        assertEquals(-0.33824, sc.calculate(0.64377, 1e-12, false), 1e-4);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/pos_values.csv")
    void positiveBranch(double x, double expected) {
        Tan mockTan = mock(Tan.class);
        Cotan mockCotan = mock(Cotan.class);
        org.lia.log.Log mockLog = mock(org.lia.log.Log.class);
        org.lia.log.Ln mockLn = mock(org.lia.log.Ln.class);

        when(mockLn.calculate(anyDouble(), anyDouble(), anyBoolean()))
            .thenAnswer(inv -> Math.log(inv.getArgument(0, Double.class)));
        when(mockLog.calculate(anyDouble(), anyDouble(), anyDouble(), anyBoolean()))
            .thenAnswer(inv -> {
                double xx = inv.getArgument(0, Double.class);
                double base = inv.getArgument(2, Double.class);
                return Math.log(xx) / Math.log(base);
            });

        SuperCalculator sc = new SuperCalculator(mockTan, mockCotan, mockLog, mockLn);

        double actual = sc.calculate(x, 1e-12, false);
        assertEquals(expected, actual, 1e-4);
    }

    @Test
    void negativeBranch() {
        Tan mockTan = mock(Tan.class);
        Cotan mockCotan = mock(Cotan.class);
        org.lia.log.Log mockLog = mock(org.lia.log.Log.class);
        org.lia.log.Ln mockLn = mock(org.lia.log.Ln.class);
        SuperCalculator sc = new SuperCalculator(mockTan, mockCotan, mockLog, mockLn);

        when(mockCotan.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> 1 / Math.tan(inv.getArgument(0, Double.class)));
        when(mockTan.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.tan(inv.getArgument(0, Double.class)));

        assertEquals(1, sc.calculate(-5, 1e-12, false), 1e-10);
        assertEquals(1, sc.calculate(-10, 1e-12, false), 1e-10);
    }

    @Test
    void invalidArgumentsPropagate() {
        Tan mockTan = mock(Tan.class);
        Cotan mockCotan = mock(Cotan.class);
        org.lia.log.Log mockLog = mock(org.lia.log.Log.class);
        org.lia.log.Ln mockLn = mock(org.lia.log.Ln.class);
        SuperCalculator sc = new SuperCalculator(mockTan, mockCotan, mockLog, mockLn);

        when(mockLn.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> {
            Double x = inv.getArgument(0, Double.class);
            if (x.isNaN() || x.isInfinite()) {
                throw new IllegalArgumentException();
            }
            return 0.0;
        });

        assertThrows(IllegalArgumentException.class, () -> sc.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> sc.calculate(1.0, 0.0, false));
        assertThrows(IllegalArgumentException.class, () -> sc.calculate(Double.POSITIVE_INFINITY, 1e-10, false));
    }

    @Test
    void tableSearchEdgeCases() {
        Tan mockTan = mock(Tan.class);
        Cotan mockCotan = mock(Cotan.class);
        org.lia.log.Log mockLog = mock(org.lia.log.Log.class);
        org.lia.log.Ln mockLn = mock(org.lia.log.Ln.class);
        SuperCalculator sc = new SuperCalculator(mockTan, mockCotan, mockLog, mockLn);

        assertTrue(sc.tableSearch(Double.NaN).isEmpty());
        assertTrue(sc.tableSearch(Double.POSITIVE_INFINITY).isEmpty());

        assertTrue(sc.tableSearch(1.0).isPresent());
        assertEquals(0.0, sc.tableSearch(1.0).getAsDouble(), 1e-12);
        assertTrue(sc.tableSearch(3.89677).isPresent());
        assertEquals(3.0931, sc.tableSearch(3.89677).getAsDouble(), 1e-8);
    }
}
