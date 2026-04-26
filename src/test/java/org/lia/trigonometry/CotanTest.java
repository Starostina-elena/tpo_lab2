package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CotanTest {

    @Test
    void tableValuesCheck() {
        assertEquals(1.7320508075688772, Cotan.calculate(Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(1.0, Cotan.calculate(Math.PI / 4.0, 1e-12, true), 1e-9);
        assertEquals(0.5773502691896257, Cotan.calculate(Math.PI / 3.0, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        assertEquals(1.7320508075688772, Cotan.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(1.0, Cotan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.5773502691896257, Cotan.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> Cotan.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> Cotan.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        assertTrue(Cotan.tableSearch(Double.NaN).isEmpty());
        assertTrue(Cotan.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        assertTrue(Cotan.calculate(0.0001, 1e-10, false) > 0.0);
        assertTrue(Cotan.calculate(-0.0001, 1e-10, false) < 0.0);
        assertTrue(Cotan.calculate(Math.PI - 0.0001, 1e-10, false) < 0.0);
        assertTrue(Cotan.calculate(Math.PI + 0.0001, 1e-10, false) > 0.0);
        assertEquals(0.0, Cotan.calculate(Math.PI / 2.0, 1e-12, false), 1e-9);
    }

    @Test
    void someValues() {
        assertEquals(1.0, Cotan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(-1.0, Cotan.calculate(-Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.5773502691896257, Cotan.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
    }
}
