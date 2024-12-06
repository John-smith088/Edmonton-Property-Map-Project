package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssessmentClassTest {

    private AssessmentClass assessmentClass;
    private AssessmentClass assessmentClassCopy;
    private AssessmentClass assessmentClass3;

    @BeforeEach
    void setUp() {
        assessmentClass = new AssessmentClass(75, 25, 0,
                "COMMERCIAL", "RESIDENTIAL", "");
        assessmentClassCopy = new AssessmentClass(75, 25, 0,
                "COMMERCIAL", "RESIDENTIAL", "");
        assessmentClass3 = new AssessmentClass(50, 25, 25,
                "FARMLAND", "COMMERCIAL", "RESIDENTIAL");
    }

    @Test
    void getAssessmentPercentage1() {
        assertEquals(75, assessmentClass.getAssessmentPercentage1());
        assertEquals(assessmentClass.getAssessmentPercentage1(), assessmentClassCopy.getAssessmentPercentage1());
        assertEquals(50, assessmentClass3.getAssessmentPercentage1());
        assertFalse(assessmentClass.getAssessmentPercentage1() == assessmentClass3.getAssessmentPercentage1());
    }

    @Test
    void getAssessmentPercentage2() {
        assertEquals(25, assessmentClassCopy.getAssessmentPercentage2());
        assertEquals(assessmentClass.getAssessmentPercentage2(), assessmentClassCopy.getAssessmentPercentage2());
        assertEquals(25, assessmentClass3.getAssessmentPercentage2());

        AssessmentClass newAssessmentClass = new AssessmentClass(60, 30, 10,
                "COMMERCIAL", "RESIDENTIAL", "");
        assertFalse(assessmentClass.getAssessmentPercentage2() == newAssessmentClass.getAssessmentPercentage2());

    }

    @Test
    void getAssessmentPercentage3() {
        assertEquals(0, assessmentClass.getAssessmentPercentage3());
        assertEquals(assessmentClass.getAssessmentPercentage3(), assessmentClassCopy.getAssessmentPercentage3());
        assertEquals(25, assessmentClass3.getAssessmentPercentage3());
        assertFalse(assessmentClass.getAssessmentPercentage3() == assessmentClass3.getAssessmentPercentage3());
    }

    @Test
    void getAssessmentClass1() {
        assertEquals("COMMERCIAL", assessmentClass.getAssessmentClass1());
        assertEquals(assessmentClass.getAssessmentClass1(), assessmentClassCopy.getAssessmentClass1());
        assertEquals("FARMLAND", assessmentClass3.getAssessmentClass1());
        assertFalse(assessmentClass.getAssessmentClass1().equals(assessmentClass3.getAssessmentClass1()));
    }

    @Test
    void getAssessmentClass2() {
        assertEquals("RESIDENTIAL", assessmentClass.getAssessmentClass2());
        assertEquals(assessmentClass.getAssessmentClass2(), assessmentClassCopy.getAssessmentClass2());
        assertEquals("COMMERCIAL", assessmentClass3.getAssessmentClass2());
        assertFalse(assessmentClass.getAssessmentClass2().equals(assessmentClass3.getAssessmentClass2()));
    }

    @Test
    void getAssessmentClass3() {
        assertEquals("", assessmentClass.getAssessmentClass3());
        assertEquals(assessmentClass.getAssessmentClass3(), assessmentClassCopy.getAssessmentClass3());
        assertEquals("RESIDENTIAL", assessmentClass3.getAssessmentClass3());
        assertFalse(assessmentClass.getAssessmentClass3().equals(assessmentClass3.getAssessmentClass3()));
    }

    @Test
    void testToString() {
        assertEquals("[COMMERCIAL 75%, RESIDENTIAL 25%]", assessmentClass.toString());
        assertEquals(assessmentClass.toString(), assessmentClassCopy.toString());
        assertEquals("[FARMLAND 50%, COMMERCIAL 25%, RESIDENTIAL 25%]", assessmentClass3.toString());
        assertFalse(assessmentClass.toString().equals(assessmentClass3.toString()));
    }

    @Test
    void testEquals() {
        assertTrue(assessmentClass.equals(assessmentClass));

        // Test Percentage 1, 2, 3
        AssessmentClass assessmentClassP1 = new AssessmentClass(60, 20, 20,
                "FARMLAND", "COMMERCIAL", "RESIDENTIAL");
        assertNotEquals(assessmentClass3, assessmentClassP1);

        AssessmentClass assessmentClassP2 = new AssessmentClass(50, 30, 20,
                "FARMLAND", "COMMERCIAL", "RESIDENTIAL");
        assertNotEquals(assessmentClass3, assessmentClassP2);

        AssessmentClass assessmentClassP3 = new AssessmentClass(50, 20, 30,
                "FARMLAND", "COMMERCIAL", "RESIDENTIAL");
        assertNotEquals(assessmentClass3, assessmentClassP3);

        // Test Assessment Class 1, 2, 3
        AssessmentClass assessmentClassC1 = new AssessmentClass(50, 20, 30,
                "OTHER RESIDENTIAL", "COMMERCIAL", "RESIDENTIAL");
        assertNotEquals(assessmentClass3, assessmentClassC1);

        AssessmentClass assessmentClassC2 = new AssessmentClass(50, 20, 30,
                "FARMLAND", "OTHER RESIDENTIAL", "RESIDENTIAL");
        assertNotEquals(assessmentClass3, assessmentClassC2);

        AssessmentClass assessmentClassC3 = new AssessmentClass(50, 20, 30,
                "FARMLAND", "COMMERCIAL", "OTHER RESIDENTIAL");
        assertNotEquals(assessmentClass3, assessmentClassC3);

        assertFalse(assessmentClass.equals(assessmentClass3));
    }

    @Test
    void testHashCode() {
        assertEquals(assessmentClass.hashCode(), assessmentClassCopy.hashCode());
        assertFalse(assessmentClass.hashCode() == assessmentClass3.hashCode());
    }
}