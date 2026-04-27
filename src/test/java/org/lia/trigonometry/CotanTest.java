package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class CotanTest {

    @Test
    void tableValuesCheck() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));

        Cotan cotan = new Cotan(sin, cos);

        assertEquals(1.7320508075688772, cotan.tableSearch(Math.PI / 6.0).orElse(Double.NaN), 1e-9);
        assertEquals(1.0, cotan.tableSearch(Math.PI / 4.0).orElse(Double.NaN), 1e-9);
        assertEquals(0.5773502691896257, cotan.tableSearch(Math.PI / 3.0).orElse(Double.NaN), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));

        Cotan cotan = new Cotan(sin, cos);

        assertEquals(1.7320508075688772, cotan.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(1.0, cotan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.5773502691896257, cotan.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        Cotan cotan = new Cotan(sin, cos);

        assertThrows(IllegalArgumentException.class, () -> cotan.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> cotan.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        Cotan cotan = new Cotan(sin, cos);

        assertTrue(cotan.tableSearch(Double.NaN).isEmpty());
        assertTrue(cotan.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));

        Cotan cotan = new Cotan(sin, cos);

        assertTrue(cotan.calculate(0.0001, 1e-10, false) > 0.0);
        assertTrue(cotan.calculate(-0.0001, 1e-10, false) < 0.0);
        assertTrue(cotan.calculate(Math.PI - 0.0001, 1e-10, false) < 0.0);
        assertTrue(cotan.calculate(Math.PI + 0.0001, 1e-10, false) > 0.0);
        assertEquals(0.0, cotan.calculate(Math.PI / 2.0, 1e-12, false), 1e-9);
    }

    @Test
    void someValues() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));

        Cotan cotan = new Cotan(sin, cos);

        assertEquals(1.0, cotan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(-1.0, cotan.calculate(-Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(0.5773502691896257, cotan.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
    }
}
