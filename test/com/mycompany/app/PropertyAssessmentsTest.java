package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class PropertyAssessmentsTest {

    private PropertyAssessments propertyAssessmentsCSV;
    private PropertyAssessments propertyAssessmentsEmpty;
    private PropertyAssessments propertyAssessmentsEven;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        propertyAssessmentsCSV = new PropertyAssessments
                ("Property_Assessment_Data_2024.csv");
        propertyAssessmentsEmpty = new PropertyAssessments(new ArrayList<>());

        List<PropertyAssessment> newProperties = new ArrayList<>();
        newProperties.add(new PropertyAssessment(
                11111111,
                new Address(2015, 8340, "WHYTE AVENUE NW"),
                "N",
                new Neighborhood(1070, "DIMMADOME", "Ward 1"),
                500001111,
                new Location(53.55591201, -113.4703111, "POINT (-113.47036614606736 53.55594401019)"),
                new AssessmentClass(100, 0, 0, "RESIDENTIAL", "", "")
        ));
        newProperties.add(new PropertyAssessment(
                2222222,
                new Address(2015, 8340, "JASPER AVENUE NW"),
                "N",
                new Neighborhood(1070, "DONSDALE", "Ward 2"),
                100055,
                new Location(53.55111201, -113.4093121, "POINT (-113.40931214606736 53.55111201019)"),
                new AssessmentClass(75, 25, 0, "COMMERCIAL", "RESIDENTIAL", "")
        ));
        propertyAssessmentsEven = new PropertyAssessments(newProperties);
    }

    @org.junit.jupiter.api.Test
    void getfileName() {
        String expectedFileName = "Property_Assessment_Data_2024.csv";
        assertEquals(expectedFileName, propertyAssessmentsCSV.getfileName());
    }

    @org.junit.jupiter.api.Test
    void getProperties() {
        List<PropertyAssessment> expectedProperties = propertyAssessmentsCSV.getProperties();
        assertFalse(expectedProperties.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void getNumberOfRecords() {
        assertEquals(426913, propertyAssessmentsCSV.getNumberOfRecords());
        assertEquals(0, propertyAssessmentsEmpty.getNumberOfRecords());
        assertEquals(2, propertyAssessmentsEven.getNumberOfRecords());
    }

    @org.junit.jupiter.api.Test
    void getMinValue() {
        assertEquals(0, propertyAssessmentsCSV.getMinValue());
        assertEquals(100055, propertyAssessmentsEven.getMinValue());
    }

    @org.junit.jupiter.api.Test
    void getMaxValue() {
        assertEquals(1237751000, propertyAssessmentsCSV.getMaxValue());
        assertEquals(500001111, propertyAssessmentsEven.getMaxValue());
    }

    @org.junit.jupiter.api.Test
    void getRange() {
        long range = propertyAssessmentsCSV.getRange();
        long range2 = propertyAssessmentsEven.getRange();
        assertEquals(1237751000, range);
        assertEquals(499901056, range2);
    }

    @org.junit.jupiter.api.Test
    void getMean() {
        long mean = propertyAssessmentsCSV.getMean();
        assertNotEquals(0, mean);
        assertEquals(478462, mean);
    }

    @org.junit.jupiter.api.Test
    void getMedian() {
        // Test on an odd number of properties
        assertEquals(334000, propertyAssessmentsCSV.getMedian());

        // Test on an even number of properties
        assertEquals(250050583, propertyAssessmentsEven.getMedian());
    }

    @org.junit.jupiter.api.Test
    void getPropertyByAccountID() {
        PropertyAssessment propertyCSV = propertyAssessmentsCSV.getPropertyByAccountID(1213677);
        assertNotNull(propertyCSV);
        assertEquals("CROMDALE", propertyCSV.getNeighborhood().getNeighborhoodName());
        assertEquals(1213677, propertyCSV.getAccountID());

        PropertyAssessment propertyCSV2 = propertyAssessmentsCSV.getPropertyByAccountID(45454);
        assertNull(propertyCSV2);
    }
}