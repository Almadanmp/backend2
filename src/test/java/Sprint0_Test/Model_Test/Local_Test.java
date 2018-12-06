package Sprint0_Test.Model_Test;

import Sprint0.Model.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class Local_Test {
    @Test
    public void seeIfGetSetLatitudeWorks() {
        //Arrange
        double expectedResult = 21;
        double actualResult;
        Local c = new Local(21, 3);

        //Act
        actualResult = c.getLatitude();

        //Assert
        assertEquals(expectedResult, actualResult, 0.001);
    }

    @Test
    public void seeIfGetSetLongitudeWorks() {
        //Arrange
        double expectedResult = 3;
        double actualResult;
        Local c = new Local(21, 3);

        //Act
        actualResult = c.getLongitude();

        //Assert
        assertEquals(expectedResult, actualResult, 0.001);
    }

    @Test
    public void seeIfGetSetAltitudeWorks() {
        //Arrange
        double expectedResult = 57;
        double actualResult;
        Local c = new Local(21, 3, 210);

        //Act
        c.setAltitude(57);
        actualResult = c.getAltitude();

        //Assert
        assertEquals(expectedResult, actualResult, 0.001);
    }

    @Test
    public void seeIfGetAltitudeBuilderWorks() {
        //Arrange
        double expectedResult = 210;
        double actualResult;
        Local c = new Local(21, 3, 210);

        //Act
        actualResult = c.getAltitude();

        //Assert
        assertEquals(expectedResult, actualResult, 0.001);
    }

    @Test
    public void seeIfEqualsWorksSameObjects() {
        //Arrange
        Local l1 = new Local(23, 42, 2);
        Local l2 = l1;
        boolean expectedResult = true;
        boolean actualResult;

        //Act
        actualResult = l1.equals(l2);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfEqualsWorksNotInstance() {
        //Arrange
        Local l1 = new Local(23, 42, 2);
        TypeSensor t1 = new TypeSensor("Rua");
        boolean expectedResult = false;
        boolean actualResult;

        //Act
        actualResult = l1.equals(t1);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfEqualsWorksDifferentObject(){
        //Arrange
        Local l1 = new Local(23, 42, 2);
        Local l2 = new Local(21,21,5);
        boolean expectedResult = false;
        boolean actualResult;

        //Act
        actualResult = l1.equals(l2);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfEqualsWorksDifferentLatitude(){
        //Arrange
        Local l1 = new Local(23, 21, 5);
        Local l2 = new Local(21,21,5);
        boolean expectedResult = false;
        boolean actualResult;

        //Act
        actualResult = l1.equals(l2);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfEqualsWorksDifferentLongitude(){
        //Arrange
        Local l1 = new Local(21, 23, 5);
        Local l2 = new Local(21,21,5);
        boolean expectedResult = false;
        boolean actualResult;

        //Act
        actualResult = l1.equals(l2);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfEqualsWorksDifferentAltitude(){
        //Arrange
        Local l1 = new Local(21, 21, 6);
        Local l2 = new Local(21,21,5);
        boolean expectedResult = true;
        boolean actualResult;

        //Act
        actualResult = l1.equals(l2);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void hashCodeDummyTest(){
        Local l1 = new Local(21,21,5);
        int expectedResult = 1;
        int actualResult = l1.hashCode();
        assertEquals(expectedResult,actualResult);
    }
    @Test
    public void seeIfWeGetLinearDistanceInKmBetweenTwoLocations() {
        //Arrange
        Local porto = new Local(41.1496100,-8.6109900);
        Local sidney = new Local(-33.865143,151.209900);
        double expectedResult = 18064.77;

        //Act
        double result = porto.getLinearDistanceBetweenLocalsInKm(sidney);

        //Assert
        assertEquals(expectedResult,result,0.01);
    }
    @Test
    public void seeIfWeDoNotGetLinearDistanceInKmBetweenTwoLocations() {
        //Arrange
        Local porto = new Local(41.1496100,-8.6109900);
        Local lisboa = new Local(38.7166700,-9.1333300,45);
        double expectedResult = 300;

        //Act
        double result = porto.getLinearDistanceBetweenLocalsInKm(lisboa);

        //Assert
        assertNotSame(expectedResult,result);
    }

    @Test
    public void seeIfWeGetLinearDistanceInKmBetweenTwoLocationsWithConstructorWith3Parameters() {
        //Arrange
        Local porto = new Local(41.1496100,-8.6109900,97);
        Local lisboa = new Local(38.7166700,-9.1333300,45);
        double expectedResult = 274.15;

        //Act
        double result = lisboa.getLinearDistanceBetweenLocalsInKm(porto);

        //Assert
        assertEquals(expectedResult,result,0.01);
    }
    @Test
    public void seeIfWeGetLinearDistanceInKmBetweenTwoLocationsChangingTheLatitude() {
        //Arrange
        Local porto = new Local(30,-8.6109900,97);
        Local lisboa = new Local(38.7166700,-9.1333300,45);
        porto.setLatitude(41.1496100);
        double expectedResult = 274.15;

        //Act
        double result = lisboa.getLinearDistanceBetweenLocalsInKm(porto);

        //Assert
        assertEquals(expectedResult,result,0.01);
    }

    @Test
    public void seeIfAreaIsContainedInArea() {
        //Arrange
        TypeArea t1 = new TypeArea("Terriola");
        TypeArea t2 = new TypeArea("Cidade");
        Local l1 = new Local(20, 15);
        GeographicArea a1 = new GeographicArea(t1,l1);
        Local l2 = new Local(20, 20);
        GeographicArea a2 = new GeographicArea(t2,l2);
        Local v1 = new Local (15,20);
        Local v2 = new Local (20,15);
        Local v3 = new Local(10,25);
        Local v4 = new Local(25,10);
        //Act
        a1.setBottomRightVertex(v2);
        a1.setTopLeftVertex(v1);
        a2.setBottomRightVertex(v4);
        a2.setTopLeftVertex(v3);
        boolean result = l1.isAreaContainedInAnotherArea(a1,a2);

        //Assert
        assertTrue(result);
    }

}
