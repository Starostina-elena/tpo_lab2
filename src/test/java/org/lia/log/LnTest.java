package org.lia.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LnTest {

    @Test
    void tableValuesCheck() {
        assertEquals(0.6931471805599453, Ln.calculate(2.0, 1e-12, true), 1e-9);
        assertEquals(1.0, Ln.calculate(2.718281828459045, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        assertEquals(0.6931471805599453, Ln.calculate(2.0, 1e-12, false), 1e-9);
        assertEquals(1.0, Ln.calculate(2.718281828459045, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> Ln.calculate(-1.0, 1e-12, false));
        assertThrows(IllegalArgumentException.class, () -> Ln.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        assertTrue(Ln.tableSearch(Double.NaN).isEmpty());
        assertTrue(Ln.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        assertTrue(Ln.calculate(0.99999, 1e-12, false) < 0.0);
        assertTrue(Ln.calculate(1.00001, 1e-12, false) > 0.0);
        assertEquals(0.0, Ln.calculate(1.0, 1e-12, false));
    }

    @Test
    void someValues() {
        assertEquals(1.60943791243410033, Ln.calculate(5, 1e-12, false), 1e-9);
        assertEquals(-2.3025850929940455, Ln.calculate(0.1, 1e-12, false), 1e-9);
        assertEquals(4.605170185988092, Ln.calculate(100, 1e-12, false), 1e-9);
    }
}

