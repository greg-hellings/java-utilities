package com.thehellings.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class ModeTest {
    @Test
    public void testTheThings() {
        assertEquals(Mode.DEVELOPMENT, Mode.DEVELOPMENT);
        assertArrayEquals(Mode.values(), Mode.values());
        assertEquals(Mode.PRODUCTION, Mode.valueOf("PRODUCTION"));
    }
}
