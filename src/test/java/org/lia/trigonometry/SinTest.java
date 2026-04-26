package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SinTest {

    @Test
    void tableValuesCheck() {
        assertEquals(0.0, Sin.calculate(0.0, 1e-12, true), 1e-9);
        assertEquals(0.5, Sin.calculate(Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(1, Sin.calculate(Math.PI / 2, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        assertEquals(0.0, Sin.calculate(0.0, 1e-12, false), 1e-9);
        assertEquals(0.5, Sin.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(1, Sin.calculate(Math.PI / 2, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> Sin.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> Sin.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        assertTrue(Sin.tableSearch(Double.NaN).isEmpty());
        assertTrue(Sin.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        assertTrue(Sin.calculate(-0.00001, 1e-12, false) < 0.0);
        assertTrue(Sin.calculate(0.00001, 1e-12, false) > 0.0);
        assertEquals(0.0, Sin.calculate(0.0, 1e-12, false));
        assertTrue(Sin.calculate(Math.PI - 0.00001, 1e-12, false) > 0.0);
        assertTrue(Sin.calculate(Math.PI + 0.00001, 1e-12, false) < 0.0);
    }

    @Test
    void someValues() {
        assertEquals(0.7071067811865475, Sin.calculate(Math.PI / 4, 1e-12, false), 1e-9);
        assertEquals(-0.7071067811865475, Sin.calculate(-Math.PI / 4, 1e-12, false), 1e-9);
        assertEquals(0.8660254037844386, Sin.calculate(Math.PI / 3, 1e-12, false), 1e-9);
    }
}
