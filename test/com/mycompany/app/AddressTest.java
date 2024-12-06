package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address1;
    private Address address2;

    @BeforeEach
    void setUp() {
        address1 = new Address(2015, 8340, "124 STREET NW");
        address2 = new Address(111, 9020, "WOLF CRESCENT NW");
    }

    @Test
    void getSuite() {
        assertEquals(2015, address1.getSuite());
        assertEquals(111, address2.getSuite());
        assertFalse(address1.getSuite() == address2.getSuite());
    }

    @Test
    void getHouseNumber() {
        assertEquals(8340, address1.getHouseNumber());
        assertEquals(9020, address2.getHouseNumber());
        assertFalse(address1.getHouseNumber() == address2.getHouseNumber());
    }

    @Test
    void getStreetName() {
        assertEquals("124 STREET NW", address1.getStreetName());
        assertEquals("WOLF CRESCENT NW", address2.getStreetName());
        assertFalse(address1.getStreetName().equals(address2.getStreetName()));
    }

    @Test
    void testToString() {
        assertEquals("8340 124 STREET NW", address1.toString());
        assertEquals("9020 WOLF CRESCENT NW", address2.toString());

        Address address = new Address(32, 9018, "");
        assertEquals("9018", address.toString());
        Address address1 = new Address(32, -1, "");
        assertEquals("N/A", address1.toString());
    }

    @Test
    void testEquals() {
        Address addressCopy = new Address(2015, 8340, "124 STREET NW");
        assertEquals(addressCopy, address1);
        assertNotEquals(address1, address2);

        // Test different Suite
        Address address3 = new Address(2011, 8340, "124 STREET NW");
        assertFalse(address1.equals(address3));

        // Test different House Number
        Address address4 = new Address(2016, 8311, "124 STREET NW");
        assertFalse(address1.equals(address4));

        // Test different Street Name
        Address address5 = new Address(2016, 8340, "81 STREET NW");
        Address address6 = new Address(2016, 8340, null);
        assertFalse(address1.equals(address5));
        assertFalse(address5.equals(address6));
        assertTrue(address6.equals(address6));
    }

    @Test
    void testHashCode() {
        Address addressCopy = new Address(2015, 8340, "124 STREET NW");
        assertEquals(address1.hashCode(), addressCopy.hashCode());
        assertFalse(address1.hashCode() == address2.hashCode());
    }
}