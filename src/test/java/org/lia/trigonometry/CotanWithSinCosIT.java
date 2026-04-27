package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CotanWithSinCosIT {

    @Test
    void cotanWithSinCos() {
        Sin sin = new Sin();
        Cos cos = new Cos();
        Cotan cotan = new Cotan(sin, cos);

        assertEquals(1.7320508075688772, cotan.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(1.0, cotan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.5773502691896257, cotan.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
        assertEquals(-1.7320508075688772, cotan.calculate(-Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(-1.0, cotan.calculate(-Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(-0.5773502691896257, cotan.calculate(-Math.PI / 3.0, 1e-12, false), 1e-9);
    }

    @Test
    void cotanWithSinCosTable() {
        Sin sin = new Sin();
        Cos cos = new Cos();
        Cotan cotan = new Cotan(sin, cos);

        assertEquals(1.7320508075688772, cotan.calculate(Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(1.0, cotan.calculate(Math.PI / 4.0, 1e-12, true), 1e-9);
        assertEquals(0.5773502691896257, cotan.calculate(Math.PI / 3.0, 1e-12, true), 1e-9);
        assertEquals(-1.7320508075688772, cotan.calculate(-Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(-1.0, cotan.calculate(-Math.PI / 4.0, 1e-12, true), 1e-9);
        assertEquals(-0.5773502691896257, cotan.calculate(-Math.PI / 3.0, 1e-12, true), 1e-9);
    }
}
