package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TanWithSinCosIT {

    @Test
    void tanWithSinCos() {
        Sin sin = new Sin();
        Cos cos = new Cos();
        Tan tan = new Tan(sin, cos);

        assertEquals(0.5773502691896257, tan.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(1.0, tan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(-0.5773502691896257, tan.calculate(-Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(-1.0, tan.calculate(-Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(-1.7320508075688772, tan.calculate(-Math.PI / 3.0, 1e-12, false), 1e-9);
    }

    @Test
    void tanWithSinCosTable() {
        Sin sin = new Sin();
        Cos cos = new Cos();
        Tan tan = new Tan(sin, cos);

        assertEquals(0.5773502691896257, tan.calculate(Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(1.0, tan.calculate(Math.PI / 4.0, 1e-12, true), 1e-9);
        assertEquals(-0.5773502691896257, tan.calculate(-Math.PI / 6.0, 1e-12, true), 1e-9);
        assertEquals(-1.0, tan.calculate(-Math.PI / 4.0, 1e-12, true), 1e-9);
        assertEquals(-1.7320508075688772, tan.calculate(-Math.PI / 3.0, 1e-12, true), 1e-9);
    }
}

