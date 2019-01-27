
package pt.ipp.isep.dei.project.model.device.devicetypes;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.project.TestUtils;
import pt.ipp.isep.dei.project.model.device.Program;
import pt.ipp.isep.dei.project.model.device.ProgramList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * WashingMachine tests class.
 */


class WashingMachineTest {

    @Test
    void getTypeTest() {
        WashingMachine washingMachine = new WashingMachine();
        String expectedResult = "WashingMachine";
        String result = washingMachine.getType();
        assertEquals(expectedResult, result);
    }

    @Test
    void getConsumptionTest() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 0D);
        double expectedResult = 0;
        double result = washingMachine.getConsumption();
        assertEquals(expectedResult, result);
    }

    @Test
    void seeIfGetCapacity() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 5D);
        ProgramList listProgram = washingMachine.getProgramList();
        Program program1 = new Program("programa", 2, 2);
        listProgram.addProgram(program1);
        double expectedResult = 6;
        washingMachine.setCapacity(6);
        double result = washingMachine.getCapacity();
        assertEquals(expectedResult, result);
    }

    @Test
    void getAttributeNamesTest() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 5D);
        ProgramList listProgram = washingMachine.getProgramList();
        Program program1 = new Program("programa", 2, 2);
        listProgram.addProgram(program1);
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("capacity");
        expectedResult.add("programList");
        List<String> result = washingMachine.getAttributeNames();
        assertEquals(expectedResult, result);
    }

    @Test
    void getAttributeValuesTest() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 5D);
        ProgramList listProgram = washingMachine.getProgramList();
        Program program1 = new Program("programa", 2, 2);
        listProgram.addProgram(program1);
        double expectedResult = 5.0;
        Object result = washingMachine.getAttributeValue("capacity");
        assertEquals(expectedResult, result);
    }

    @Test
    void getAttributeValuesTest1() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 5D);
        ProgramList listProgram = washingMachine.getProgramList();
        Program program1 = new Program("programa", 2, 2);
        listProgram.addProgram(program1);
        int expectedResult = 0;
        Object result = washingMachine.getAttributeValue("capacity" + "programList");
        assertEquals(expectedResult, result);
    }

    @Test
    void getAttributeValuesWithCapacityEmptyTest() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 34D);
        ProgramList listProgram = washingMachine.getProgramList();
        Program program1 = new Program("programa", 2, 2);
        listProgram.addProgram(program1);
        double expectedResult = 34.0;
        Object result = washingMachine.getAttributeValue("capacity");
        assertEquals(expectedResult, result);
    }

    @Test
    void setAttributeValueTestCapacity() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue("capacity", 5.0D);
        Object result = washingMachine.getAttributeValue("capacity");
        assertEquals(5.0, result);
    }

    @Test
    void setAttributeValueTestCapacity2() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 23D);
        Object result = washingMachine.getAttributeValue("capacity");
        assertEquals(23.0, result);
    }

    @Test
    void seeIfSetAttributeValueTestTrueWorks() {
        //Arrange
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 5D);
        ProgramList listProgram = washingMachine.getProgramList();
        Program program1 = new Program("programa", 2, 2);
        listProgram.addProgram(program1);
        //Act
        boolean actualResult = washingMachine.setAttributeValue("capacity", 12.0);
        //Assert
        assertEquals(true, actualResult);
    }

    @Test
    void getAttributeValuesTestListProgram() {
        WashingMachine washingMachine = new WashingMachine();
        Program program1 = new Program("programa", 2, 2);
        ProgramList expectedResult = washingMachine.getProgramList();
        expectedResult.addProgram(program1);
        Object result = washingMachine.getAttributeValue("programList");
        assertEquals(expectedResult, result);
    }

    @Test
    void getAttributeValuesTestListProgramWithProgramListEmpty() {
        WashingMachine washingMachine = new WashingMachine();
        ProgramList expectedResult = washingMachine.getProgramList();
        Object result = washingMachine.getAttributeValue("programList");
        assertEquals(expectedResult, result);
    }

    @Test
    void setAttributeValueTestDefault() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue("capacity", 5.0);
        Object result = washingMachine.getAttributeValue("lisbon");
        assertEquals(0, result);
    }

    @Test
    void setAttributeValueTestDefault3() {
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue("programList", 5.0);
        washingMachine.setAttributeValue("capacity", 6.0);
        Object result = washingMachine.getAttributeValue("capacity");
        Object expectedResult = 6.0;
        assertEquals(expectedResult, result);
    }

    @Test
    void setAttributeValueTestFalse() {
        WashingMachine washingMachine = new WashingMachine();
        Program program1 = new Program("programa", 2, 2);
        ProgramList listProgram = washingMachine.getProgramList();
        listProgram.addProgram(program1);
        boolean result = washingMachine.setAttributeValue("lisboa", listProgram);
        assertEquals(false, result);
    }

    @Test
    void setAttributeValueTestFalseAgain() {
        WashingMachine washingMachine = new WashingMachine();
        Object result = washingMachine.setAttributeValue("capacity", 5);
        assertEquals(false, result);
    }

    @Test
    void setAttributeValueTest() {
        WashingMachine washingMachine = new WashingMachine();
        Program program1 = new Program("programa", 2, 2);
        ProgramList listProgram = washingMachine.getProgramList();
        listProgram.addProgram(program1);
        boolean result = washingMachine.setAttributeValue("lisboa", 12);
        assertEquals(false, result);
    }

    @Test
    void testGetAttributeCoveringAllCases() {
        //Arrange
        WashingMachine washingMachine = new WashingMachine();
        washingMachine.setAttributeValue(TestUtils.WM_CAPACITY, 5D);
        Program program1 = new Program("programa", 2, 2);
        ProgramList listProgram = washingMachine.getProgramList();
        listProgram.addProgram(program1);
        // original strings:
        assertEquals(5.0, washingMachine.getAttributeValue("capacity"));
        assertEquals(listProgram, washingMachine.getAttributeValue("programList"));

        // same hash codes, but different strings:
        assertEquals(0, washingMachine.getAttributeValue("\0capacity"));
        assertEquals(0, washingMachine.getAttributeValue("\0programList"));

        // distinct hash code to cover default cases of switches
        assertEquals(0, washingMachine.getAttributeValue(""));
    }
}
