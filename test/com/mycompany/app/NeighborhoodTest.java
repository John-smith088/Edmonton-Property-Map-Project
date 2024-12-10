package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighborhoodTest {

    private Neighborhood n1;
    private Neighborhood n1Copy;
    private Neighborhood n2;

    @BeforeEach
    void setUp() {
        n1 = new Neighborhood(1004, "Mill Woods", "Ward 2");
        n1Copy = new Neighborhood(1004, "Mill Woods", "Ward 2");
        n2 = new Neighborhood(1006, "Castle Downs", "Ward 3");
    }

    @Test
    void getNeighborhoodID() {
        assertEquals(1004, n1.getNeighborhoodID());
        assertEquals(n1.getNeighborhoodID(), n1Copy.getNeighborhoodID());
        assertEquals(1006, n2.getNeighborhoodID());
        assertFalse(n1.getNeighborhoodID() == (n2.getNeighborhoodID()));
    }

    @Test
    void getNeighborhoodName() {
        assertEquals("Mill Woods", n1.getNeighborhoodName());
        assertEquals(n1.getNeighborhoodName(), n1Copy.getNeighborhoodName());
        assertEquals("Castle Downs", n2.getNeighborhoodName());
        assertFalse(n1.getNeighborhoodName().equals(n2.getNeighborhoodName()));
    }

    @Test
    void getWard() {
        assertEquals("Ward 2", n1.getWard());
        assertEquals(n1.getWard(), n1Copy.getWard());
        assertEquals("Ward 3", n2.getWard());
        assertFalse(n1.getWard().equals(n2.getWard()));
    }

    @Test
    void testToString() {
        assertEquals("Mill Woods (Ward 2)", n1.toString());
        assertEquals(n1.toString(), n1Copy.toString());
        assertEquals("Castle Downs (Ward 3)", n2.toString());
    }

    @Test
    void testEquals() {
        assertEquals(n1.toString(), n1Copy.toString());
        assertNotEquals(n1, n2);
        assertNotEquals(null, n1);
    }

    @Test
    void testHashCode() {
        assertEquals(n1.hashCode(), n1.hashCode());
        assertEquals(n1.hashCode(), n1Copy.hashCode());
        assertNotEquals(n1.hashCode(), n2.hashCode());
    }
}