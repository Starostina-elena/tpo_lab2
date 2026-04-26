package org.lia.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LogLnIT {

    @Test
    void logWithLn() {
        Ln ln = new Ln();
        Log log = new Log(ln);

        assertEquals(0.0, log.calculate(1.0, 1e-12, 2.0, false), 1e-9);
        assertEquals(1.0, log.calculate(3.0, 1e-12, 3.0, false), 1e-9);
        assertEquals(3.0, log.calculate(8.0, 1e-12, 2.0, false), 1e-9);
        assertEquals(0.5, log.calculate(2.0, 1e-12, 4.0, false), 1e-9);
        assertEquals(-2.0, log.calculate(0.25, 1e-12, 2.0, false), 1e-9);
    }

    @Test
    void logWithLnTable() {
        Ln ln = new Ln();
        Log log = new Log(ln);

        assertEquals(0.0, log.calculate(1.0, 1e-12, 2.0, true), 1e-9);
        assertEquals(1.0, log.calculate(3.0, 1e-12, 3.0, true), 1e-9);
        assertEquals(3.0, log.calculate(8.0, 1e-12, 2.0, true), 1e-9);
        assertEquals(0.5, log.calculate(2.0, 1e-12, 4.0, true), 1e-9);
        assertEquals(-2.0, log.calculate(0.25, 1e-12, 2.0, true), 1e-9);
    }
}

