package pt.ipp.isep.dei.project.services.units;

import javassist.Modifier;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class UnitHelperTest {

    @Test
    void testConstructorIsPrivate() throws IllegalStateException {
        try {
            Constructor<UnitHelper> constructor = UnitHelper.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException i) {
        } catch (InvocationTargetException a) {
        } catch (NoSuchMethodException ns) {
        }
    }

    @Test
    void seeIfGetUserTemperatureDefaultWorks() throws IOException {
        Properties props = new Properties();
        String propFileName = "resources/units.properties";
        String expectedResult = "Celsius";
        FileInputStream input = new FileInputStream(propFileName);
        props.load(input);
        //ACT
        String actualResult = UnitHelper.getUserTemperatureDefault(propFileName);
        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfThrowIOExceptionWorks() {
        assertThrows(IOException.class,
                () -> {
                    Properties props = new Properties();
                    String propFileName = "resources/abcd.efgh";
                    FileInputStream input = new FileInputStream(propFileName);
                    props.load(input);
                });
    }

    @Test
    void seeIfGetApplicationTemperatureConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Celsius";

        // Act

        String actualResult = UnitHelper.getApplicationTemperatureConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetUserTemperatureConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Celsius";

        // Act

        String actualResult = UnitHelper.getUserTemperatureConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetApplicationRainfallConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Millimeter";

        // Act

        String actualResult = UnitHelper.getApplicationRainfallConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetUserRainfallConfigWorks() throws IOException {
        // Arrange

        String expectedResult = "Millimeter";

        // Act

        String actualResult = UnitHelper.getUserRainfallConfig();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

//    @Test
//    void seeIfGetApplicationRainfallDefaultWorks(){
//        // Arrange
//
//        String propFileName = "invalid_path";
//
//        // Act
//
//        Throwable exception = assertThrows(IOException.class, () -> UnitHelper.getApplicationRainfallDefault(propFileName));
//
//        // Assert
//
//        assertEquals("ERROR: Unable to process configuration file.", exception.getMessage());
//
//    }

    @Test
    void seeIfGetUserRainfallDefaultWorks() {
        // Arrange

        String propFileName = "invalid_path";

        // Act

        Throwable exception = assertThrows(IOException.class, () -> UnitHelper.getUserRainfallDefault(propFileName));

        // Assert

        assertEquals("ERROR: Unable to process configuration file.", exception.getMessage());

    }

    @Test
    void seeIfConvertToSystemDefaultTemperatureWorks() throws IOException {
        // Arrange

        double expectedResult = -223.14999999999998;
        double valueToConvert = 50;
        Unit unit = new Kelvin();

        // Act

        double actualResult = UnitHelper.convertToSystemDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertToSystemDefaultRainfallWorks() throws IOException {
        // Arrange

        double expectedResult = 50;
        double valueToConvert = 50;
        Unit unit = new LiterPerSquareMeter();

        // Act

        double actualResult = UnitHelper.convertToSystemDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertToUserDefaultTemperatureWorks() throws IOException {
        // Arrange

        double expectedResult = -223.14999999999998;
        double valueToConvert = 50;
        Unit unit = new Kelvin();

        // Act

        double actualResult = UnitHelper.convertToUserDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertToUserDefaultRainfallWorks() throws IOException {
        // Arrange

        double expectedResult = 50;
        double valueToConvert = 50;
        Unit unit = new LiterPerSquareMeter();

        // Act

        double actualResult = UnitHelper.convertToUserDefault(valueToConvert, unit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertUnitToSystemDefaultWorks() {
        // Arrange

        Unit expectedResult = new Celsius();
        Unit givenUnit = new Fahrenheit();

        // Act

        Unit actualResult = UnitHelper.convertUnitToSystemDefault(givenUnit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertUnitToSystemDefaultWorksIfReceivesNull() {
        // Act

        Unit actualResult = UnitHelper.convertUnitToSystemDefault(null);

        // Assert

        assertNull(actualResult);
    }

    @Test
    void seeIfConvertUnitToSystemDefaultRainfallWorks() {
        // Arrange

        Unit expectedResult = new Millimeter();
        Unit givenUnit = new LiterPerSquareMeter();

        // Act

        Unit actualResult = UnitHelper.convertUnitToSystemDefault(givenUnit);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfConvertStringToUnitWorks() {
        // Arrange

        Unit expectedResult = new Celsius();
        String givenUnitString = "Celsius";

        // Act

        Unit actualResult = UnitHelper.convertStringToUnit(givenUnitString);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

//    @Test
//    void seeIfConvertStringToUnitWorksWithWrongString() {
//        // Arrange
//
//        String givenUnitString = "WrongString";
//
//        // Act
//
//        Unit actualResult = UnitHelper.convertStringToUnit(givenUnitString);
//
//        // Assert
//
//        assertNull(actualResult);
//    }

    @Test
    void seeIfGetReaderClassToInstanceWorks() throws IOException {
        // Arrange

        String expectedResult = "Fahrenheit";
        String givenUnitString = "F";

        // Act

        String actualResult = UnitHelper.getReaderClassToInstance(givenUnitString);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

}
