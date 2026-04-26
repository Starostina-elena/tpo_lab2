package org.lia.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LogTest {

    @Test
    void tableValuesCheck() {
        assertEquals(0.0, Log.calculate(1.0, 1e-12, 2.0, true), 1e-9);
        assertEquals(1.0, Log.calculate(3.0, 1e-12, 3.0, true), 1e-9);
        assertEquals(3.0, Log.calculate(8.0, 1e-12, 2.0, true), 1e-9);
    }

    @Test
    void tableValuesCloseToReal() {
        assertEquals(0.0, Log.calculate(1.0, 1e-12, 2.0, false), 1e-9);
        assertEquals(1.0, Log.calculate(3.0, 1e-12, 3.0, false), 1e-9);
        assertEquals(3.0, Log.calculate(8.0, 1e-12, 2.0, false), 1e-9);
    }

    @Test
    void invalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> Log.calculate(0.0, 1e-10, 2.0, false));
        assertThrows(IllegalArgumentException.class, () -> Log.calculate(2.0, 1e-10, 1.0, false));
        assertThrows(IllegalArgumentException.class, () -> Log.calculate(Double.NaN, 1e-10, 2.0, false));
    }

    @Test
    void tableSearchEdgeCases() {
        assertTrue(Log.tableSearch(Double.NaN, 2.0).isEmpty());
        assertTrue(Log.tableSearch(2.0, Double.NaN).isEmpty());
        assertTrue(Log.tableSearch(-1.0, 2.0).isEmpty());
    }

    @Test
    void signCheck() {
        assertTrue(Log.calculate(0.99999, 1e-12, 2.0, false) < 0.0);
        assertTrue(Log.calculate(1.00001, 1e-12, 2.0, false) > 0.0);
        assertEquals(0.0, Log.calculate(1.0, 1e-12, 2.0, false));
    }

    @Test
    void someValues() {
        assertEquals(0.5, Log.calculate(2.0, 1e-12, 4.0, false), 1e-9);
        assertEquals(3.0, Log.calculate(8.0, 1e-12, 2.0, false), 1e-9);
        assertEquals(3.321928094887362, Log.calculate(10.0, 1e-12, 2.0, false), 1e-9);
    }
}

