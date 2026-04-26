package org.lia.trigonometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TanTest {

    @Test
    void tableValuesCheck() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));

        Tan tan = new Tan(sin, cos);

        assertEquals(0.0, tan.tableSearch(0.0).orElse(Double.NaN), 1e-9);
        assertEquals(0.5773502691896257, tan.tableSearch(Math.PI / 6.0).orElse(Double.NaN), 1e-9);
        assertEquals(1.0, tan.tableSearch(Math.PI / 4.0).orElse(Double.NaN), 1e-9);
    }

    @Test
    void tableValuesCloseToReal_withMockedSinCos() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));
        Tan tan = new Tan(sin, cos);

        assertEquals(0.5773502691896257, tan.calculate(Math.PI / 6.0, 1e-12, false), 1e-9);
        assertEquals(1.0, tan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        Tan tan = new Tan(sin, cos);

        assertThrows(IllegalArgumentException.class, () -> tan.calculate(Double.NaN, 1e-10, false));
        assertThrows(IllegalArgumentException.class, () -> tan.calculate(1.0, 0.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        Tan tan = new Tan(sin, cos);

        assertTrue(tan.tableSearch(Double.NaN).isEmpty());
        assertTrue(tan.tableSearch(Double.POSITIVE_INFINITY).isEmpty());
    }

    @Test
    void signCheck() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));
        Tan tan = new Tan(sin, cos);

        assertTrue(tan.calculate(0.0001, 1e-10, false) > 0.0);
        assertTrue(tan.calculate(-0.0001, 1e-10, false) < 0.0);
        assertTrue(tan.calculate(Math.PI - 0.0001, 1e-10, false) < 0.0);
        assertTrue(tan.calculate(Math.PI + 0.0001, 1e-10, false) > 0.0);
        assertEquals(0.0, tan.calculate(0, 1e-12, false), 1e-9);
    }

    @Test
    void someValues() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        when(sin.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.sin(inv.getArgument(0, Double.class)));
        when(cos.calculate(anyDouble(), anyDouble(), anyBoolean())).thenAnswer(inv -> Math.cos(inv.getArgument(0, Double.class)));
        Tan tan = new Tan(sin, cos);

        assertEquals(1.0, tan.calculate(Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(-1.0, tan.calculate(-Math.PI / 4.0, 1e-12, false), 1e-9);
        assertEquals(1.7320508075688772, tan.calculate(Math.PI / 3.0, 1e-12, false), 1e-9);
    }
}
