package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CosTest {

    @Test
    void tableValuesCheck() {
        Cos cos = new Cos();
        assertEquals(1.0, cos.calculate(0.0, 1e-12, true), 1e-9);
        assertEquals(0.8660254037844386, cos.calculate(Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(0.0, cos.calculate(Math.PI / 2.0, 1e-12, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        Cos cos = new Cos();
        assertEquals(1.0, cos.calculate(0.0, 1e-12, false), 1e-9);
        assertEquals(0.8660254037844386, cos.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(0.0, cos.calculate(Math.PI / 2.0, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        Cos cos = new Cos();
        assertThrows(IllegalArgumentException.class, () -> cos.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> cos.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        Cos cos = new Cos();
        assertTrue(cos.tableSearch(Double.NaN).isEmpty());
        assertTrue(cos.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        Cos cos = new Cos();
        assertTrue(cos.calculate(-0.00001, 1e-12, false) > 0.0);
        assertTrue(cos.calculate(0.00001, 1e-12, false) > 0.0);
        assertEquals(1.0, cos.calculate(0.0, 1e-12, false));
        assertTrue(cos.calculate(Math.PI - 0.00001, 1e-12, false) < 0.0);
        assertTrue(cos.calculate(Math.PI + 0.00001, 1e-12, false) < 0.0);
    }

    @Test
    void someValues() {
        Cos cos = new Cos();
        assertEquals(0.7071067811865476, cos.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.7071067811865476, cos.calculate(-Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.5, cos.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
    }
}
