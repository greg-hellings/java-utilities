package com.thehellings.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {
    enum TestEnums {
        field1,
        field2,
        field3,
        not_present
    };

    @Test
    public void developmentEnumeratorConstructor() {
        Config config = new Config(Mode.DEVELOPMENT);
        assertEquals("dev1", config.get("field1"));
        assertEquals("dev2", config.get("field2"));
        assertEquals("default3", config.get(TestEnums.field3));
        assertNull(config.get(TestEnums.not_present));
    }

    @Test
    public void productionStringConstructor() {
        Config config = new Config("PRODUCTION");
        assertEquals("prod1", config.get(TestEnums.field1));
        assertNull(config.get(TestEnums.field2));
        assertEquals("prod3", config.get("field3"));
        assertNull(config.get(TestEnums.not_present));
    }

    @Test
    public void nonDefaultFilename() {
        Config config = new Config("DEVELOPMENT", ConfigTest.class.getClassLoader().getResourceAsStream("other.ini"));
        assertEquals("dev1", config.get("field1"));
        assertEquals("dev2", config.get("field2"));
        assertNull(config.get("field3"));
    }

    @Test
    public void nonDefaultFilenameEnum() throws Exception {
        Config config = new Config(Mode.PRODUCTION, ConfigTest.class.getClassLoader().getResourceAsStream("other.ini"));
        assertEquals("prod1", config.get("field1"));
        assertNull(config.get("field2"));
        assertEquals("prod3", config.get("field3"));
    }

    @Test
    public void defaultsOnly() {
        Config config = new Config("derpenv", ConfigTest.class.getClassLoader().getResourceAsStream("defaultsonly.ini"));
        assertEquals("somevalue", config.get("field1"));
    }

    @Test
    public void failOpen() {
        Config config = new Config("anyenv", null);
        assertNull(config.get("anything"));
    }
}