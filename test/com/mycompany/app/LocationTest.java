package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LocationTest {
    private Location location1;
    private Location location1Copy;
    private Location location2;
    private Location location3;
    private Location location4;

    @BeforeEach
    void setUp() {
        location1 = new Location(53.53094866, -113.46986, "POINT (53.53094866 -113.46986)");
        location1Copy = new Location(53.53094866, -113.46986, "POINT (53.53094866 -113.46986)");
        location2 = new Location(70.230271, -13.421236, "POINT (70.230271 -13.421236)");
        location3 = new Location(90, -90, "POINT (90 -90)");
        location4 = new Location(180, -179.9, "POINT (180 -179.9)");
    }

    @Test
    void getLat() {
        assertEquals(53.53094866, location1.getLat());
        assertEquals(location1.getLat(), location1Copy.getLat());

        assertEquals(70.230271, location2.getLat());
        assertEquals(90, location3.getLat());
        assertEquals(180, location4.getLat());

        assertNotEquals(42.11111, location1.getLat());
        assertNotEquals(location1.getLat(), (location2.getLat()));
    }

    @Test
    void getLng() {
        assertEquals(-113.46986, location1.getLng());
        assertEquals(location1.getLng(), location1Copy.getLng());

        assertEquals(-13.421236, location2.getLng());
        assertEquals(-90, location3.getLng());
        assertEquals(-179.9, location4.getLng());

        assertNotEquals(-20.11111, location1.getLng());
        assertNotEquals(location1.getLng(), (location2.getLng()));
    }

    @Test
    void getPoint() {
        assertEquals("POINT (53.53094866 -113.46986)", location1.getPoint());
        assertEquals(location1.getPoint(), location1Copy.getPoint());
        assertNotEquals("POINT (53.53094866 -20.11111)", location1.getPoint());

        assertNotEquals(location1.getPoint(), (location2.getPoint()));

        assertEquals("POINT (90 -90)", location3.getPoint());
        assertEquals("POINT (180 -179.9)", location4.getPoint());
    }

    @Test
    void testToString() {
        assertEquals(location1.toString(), location1Copy.toString());
        assertNotEquals(location1.toString(), (location2.toString()));

        assertEquals("(70.230271, -13.421236)", location2.toString());
        assertEquals("(90.0, -90.0)", location3.toString());
    }

    @Test
    void testEquals() {
        assertEquals(location1, location1);
        assertEquals(location1, location1Copy);
        assertNotEquals(location1, location2);
        assertNotEquals(location3, location4);
    }

    @Test
    void testHashCode() {
        assertEquals(location1.hashCode(), location1.hashCode());
        assertEquals(location1.hashCode(), location1Copy.hashCode());
        assertNotEquals(location1.hashCode(), location2.hashCode());

    }
}