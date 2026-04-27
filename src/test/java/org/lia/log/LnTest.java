package org.lia.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LnTest {

    @Test
    void tableValuesCheck() {
        Ln ln = new Ln();
        assertEquals(0.6931471805599453, ln.calculate(2.0, 1e-12, true), 1e-9);
        assertEquals(1.0, ln.calculate(2.718281828459045, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        Ln ln = new Ln();
        assertEquals(0.6931471805599453, ln.calculate(2.0, 1e-12, false), 1e-9);
        assertEquals(1.0, ln.calculate(2.718281828459045, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        Ln ln = new Ln();
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(-1.0, 1e-12, false));
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        Ln ln = new Ln();
        assertTrue(ln.tableSearch(Double.NaN).isEmpty());
        assertTrue(ln.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        Ln ln = new Ln();
        assertTrue(ln.calculate(0.99999, 1e-12, false) < 0.0);
        assertTrue(ln.calculate(1.00001, 1e-12, false) > 0.0);
        assertEquals(0.0, ln.calculate(1.0, 1e-12, false));
    }

    @Test
    void someValues() {
        Ln ln = new Ln();
        assertEquals(1.60943791243410033, ln.calculate(5, 1e-12, false), 1e-9);
        assertEquals(-2.3025850929940455, ln.calculate(0.1, 1e-12, false), 1e-9);
        assertEquals(4.605170185988092, ln.calculate(100, 1e-12, false), 1e-9);
    }
}
