package org.lia.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class LogTest {

    @Test
    void tableValuesCheck() {
        Ln mockLn = mock(Ln.class);
        Log log = new Log(mockLn);

        assertEquals(0.0, log.calculate(1.0, 1e-12, 2.0, true), 1e-9);
        assertEquals(1.0, log.calculate(3.0, 1e-12, 3.0, true), 1e-9);
        assertEquals(3.0, log.calculate(8.0, 1e-12, 2.0, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        Ln mockLn = mock(Ln.class);
        Log log = new Log(mockLn);

        when(mockLn.calculate(anyDouble(), anyDouble(), anyBoolean()))
            .thenAnswer(inv -> Math.log(inv.getArgument(0, Double.class)));

        assertEquals(0.0, log.calculate(1.0, 1e-12, 2.0, false), 1e-9);
        assertEquals(1.0, log.calculate(3.0, 1e-12, 3.0, false), 1e-9);
        assertEquals(3.0, log.calculate(8.0, 1e-12, 2.0, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        Ln mockLn = mock(Ln.class);
        Log log = new Log(mockLn);

        assertThrows(IllegalArgumentException.class, () -> log.calculate(0.0, 1e-10, 2.0, false));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(2.0, 1e-10, 1.0, false));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(Double.NaN, 1e-10, 2.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        Ln mockLn = mock(Ln.class);
        Log log = new Log(mockLn);

        assertTrue(log.tableSearch(Double.NaN, 2.0).isEmpty());
        assertTrue(log.tableSearch(2.0, Double.NaN).isEmpty());
        assertTrue(log.tableSearch(-1.0, 2.0).isEmpty());
    }

    @Test
    void signCheck() {
        Ln mockLn = mock(Ln.class);
        Log log = new Log(mockLn);

        when(mockLn.calculate(anyDouble(), anyDouble(), anyBoolean()))
            .thenAnswer(inv -> Math.log(inv.getArgument(0, Double.class)));

        assertTrue(log.calculate(0.99999, 1e-12, 2.0, false) < 0.0);
        assertTrue(log.calculate(1.00001, 1e-12, 2.0, false) > 0.0);
        assertEquals(0.0, log.calculate(1.0, 1e-12, 2.0, false));
    }

    @Test
    void someValues() {
        Ln mockLn = mock(Ln.class);
        Log log = new Log(mockLn);

        when(mockLn.calculate(anyDouble(), anyDouble(), anyBoolean()))
            .thenAnswer(inv -> Math.log(inv.getArgument(0, Double.class)));

        assertEquals(0.5, log.calculate(2.0, 1e-12, 4.0, false), 1e-9);
        assertEquals(3.0, log.calculate(8.0, 1e-12, 2.0, false), 1e-9);
        assertEquals(3.321928094887362, log.calculate(10.0, 1e-12, 2.0, false), 1e-9);
    }
}
