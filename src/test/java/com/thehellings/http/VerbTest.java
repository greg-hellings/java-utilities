package com.thehellings.http;

import org.junit.Test;

import static org.junit.Assert.*;

public class VerbTest {
    @Test
    public void justDoTheTest() {
        assertEquals(Verbs.CONNECT, Verbs.CONNECT);
        assertArrayEquals(Verbs.values(), Verbs.values());
        assertEquals(Verbs.valueOf("GET"), Verbs.GET);
    }
}
