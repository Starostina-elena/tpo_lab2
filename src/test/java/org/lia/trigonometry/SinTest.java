package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SinTest {

    @Test
    void tableValuesCheck() {
        Sin sin = new Sin();
        assertEquals(0.0, sin.calculate(0.0, 1e-12, true), 1e-9);
        assertEquals(0.5, sin.calculate(Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(1, sin.calculate(Math.PI / 2, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        Sin sin = new Sin();
        assertEquals(0.0, sin.calculate(0.0, 1e-12, false), 1e-9);
        assertEquals(0.5, sin.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(1, sin.calculate(Math.PI / 2, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        Sin sin = new Sin();
        assertThrows(IllegalArgumentException.class, () -> sin.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> sin.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        Sin sin = new Sin();
        assertTrue(sin.tableSearch(Double.NaN).isEmpty());
        assertTrue(sin.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        Sin sin = new Sin();
        assertTrue(sin.calculate(-0.00001, 1e-12, false) < 0.0);
        assertTrue(sin.calculate(0.00001, 1e-12, false) > 0.0);
        assertEquals(0.0, sin.calculate(0.0, 1e-12, false));
        assertTrue(sin.calculate(Math.PI - 0.00001, 1e-12, false) > 0.0);
        assertTrue(sin.calculate(Math.PI + 0.00001, 1e-12, false) < 0.0);
    }

    @Test
    void someValues() {
        Sin sin = new Sin();
        assertEquals(0.7071067811865475, sin.calculate(Math.PI / 4, 1e-12, false), 1e-9);
        assertEquals(-0.7071067811865475, sin.calculate(-Math.PI / 4, 1e-12, false), 1e-9);
        assertEquals(0.8660254037844386, sin.calculate(Math.PI / 3, 1e-12, false), 1e-9);
    }
}
