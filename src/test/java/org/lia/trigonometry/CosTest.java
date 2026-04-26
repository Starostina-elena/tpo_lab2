package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CosTest {

    @Test
    void tableValuesCheck() {
        assertEquals(1.0, Cos.calculate(0.0, 1e-12, true), 1e-9);
        assertEquals(0.8660254037844386, Cos.calculate(Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(0.0, Cos.calculate(Math.PI / 2.0, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        assertEquals(1.0, Cos.calculate(0.0, 1e-12, false), 1e-9);
        assertEquals(0.8660254037844386, Cos.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(0.0, Cos.calculate(Math.PI / 2.0, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> Cos.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> Cos.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        assertTrue(Cos.tableSearch(Double.NaN).isEmpty());
        assertTrue(Cos.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        assertTrue(Cos.calculate(-0.00001, 1e-12, false) > 0.0);
        assertTrue(Cos.calculate(0.00001, 1e-12, false) > 0.0);
        assertEquals(1.0, Cos.calculate(0.0, 1e-12, false));
        assertTrue(Cos.calculate(Math.PI - 0.00001, 1e-12, false) < 0.0);
        assertTrue(Cos.calculate(Math.PI + 0.00001, 1e-12, false) < 0.0);
    }

    @Test
    void someValues() {
        assertEquals(0.7071067811865476, Cos.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.7071067811865476, Cos.calculate(-Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.5, Cos.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
    }
}
