package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

class PropertyAssessmentTest {

    private PropertyAssessment propertyAssessment;
    private Address address;
    private String garage;
    private Neighborhood neighbourhood;
    private Location location;
    private AssessmentClass assessmentClass;

    @BeforeEach
    void setUp() {
        address = new Address(2015, 8340, "124 STREET NW");
        garage = "N";
        neighbourhood = new Neighborhood(1070, "DIMMADOME", "Ward 1");
        location = new Location(53.55591201, -113.4703111, "POINT (-113.47036614606736 53.55594401019)");
        assessmentClass = new AssessmentClass(100, 0, 0,
                "RESIDENTIAL", "", "");
        propertyAssessment = new PropertyAssessment(1000002, address, garage, neighbourhood,
                500001, location, assessmentClass);
    }

    @Test
    void getAccountID() {
        assertEquals(1000002, propertyAssessment.getAccountID());
        assertFalse(propertyAssessment.getAccountID() == 1000001);
    }

    @Test
    void getAddress() {
        assertEquals(address, propertyAssessment.getAddress());
        assertFalse(propertyAssessment.getAddress().equals(null));
    }

    @Test
    void getGarage() {
        assertEquals(garage, propertyAssessment.getGarage());
    }

    @Test
    void getNeighborhood() {
        assertEquals(neighbourhood, propertyAssessment.getNeighborhood());
    }

    @Test
    void getAssessedValue() {
        assertEquals(500001, propertyAssessment.getAssessedValue());
    }

    @Test
    void getLocation() {
        assertEquals(location, propertyAssessment.getLocation());
    }

    @Test
    void getAssessmentClass() {
        assertEquals(assessmentClass, propertyAssessment.getAssessmentClass());
    }

    @Test
    void compareTo() {
        assertEquals(0, propertyAssessment.compareTo(propertyAssessment));

        PropertyAssessment propertyAssessment2 = new PropertyAssessment(1000002, address, garage, neighbourhood,
                5000011, location, assessmentClass);
        assertEquals(-1, propertyAssessment.compareTo(propertyAssessment2), "propertyAssessment is smaller than propertyAssessment2");

        PropertyAssessment propertyAssessment3= new PropertyAssessment(1000002, address, garage, neighbourhood,
                500001, location, assessmentClass);
        assertEquals(0, propertyAssessment.compareTo(propertyAssessment3), "propertyAssessment and propertyAssessment3 are equal");

        PropertyAssessment propertyAssessment4= new PropertyAssessment(1000002, address, garage, neighbourhood,
                3206, location, assessmentClass);
        assertEquals(1, propertyAssessment.compareTo(propertyAssessment4), "propertyAssessment is larger than propertyAssessment4");
    }
}