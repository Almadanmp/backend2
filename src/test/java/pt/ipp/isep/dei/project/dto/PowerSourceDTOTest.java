package pt.ipp.isep.dei.project.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.project.model.device.WaterHeater;
import pt.ipp.isep.dei.project.model.device.devicespecs.WaterHeaterSpec;

import static org.junit.jupiter.api.Assertions.*;

class PowerSourceDTOTest {
    // Common testing artifacts for testing in this class.
    private PowerSourceDTO validDTO;

    @BeforeEach
    void arrangeArtifacts() {
        validDTO = new PowerSourceDTO();
    }

    @Test
    void seeIfGetSetNameWorks() {
        // Arrange

        validDTO.setName("Test");
        String expectedResult = "Test";

        // Act

        String actualResult = validDTO.getName();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetSetMaxPowerOutputWorks() {
        // Arrange

        validDTO.setMaxPowerOutput(31);

        // Act

        double result = validDTO.getMaxPowerOutput();

        // Assert

        assertEquals(31D, result);
    }

    @Test
    void seeIfGetSetMaxEnergyStorageWorks() {
        // Arrange

        validDTO.setMaxEnergyStorage(14);

        // Act

        double result = validDTO.getMaxEnergyStorage();

        // Assert

        assertEquals(14, result);
    }

    @Test
    void seeIfEqualsWorks() {
        // Arrange

        validDTO.setName("Mock");
        PowerSourceDTO testDTO = new PowerSourceDTO();
        testDTO.setName("Mock");

        // Assert

        assertEquals(validDTO, testDTO);
    }

    @Test
    void seeIfEqualsWorksFalse() {
        // Arrange

        validDTO.setName("Mock");
        PowerSourceDTO testDTO = new PowerSourceDTO();
        testDTO.setName("NotMock");

        // Assert

        assertNotEquals(validDTO, testDTO);
    }

    @Test
    void seeIfEqualsWorksOnItself() {
        assertEquals(validDTO, validDTO);
    }

    @Test
    void seeIfHashcodeWorks() {
        assertEquals(1, validDTO.hashCode());
    }


    @Test
    void seeIfEqualsWorksNotAnInstance() {
        assertNotEquals(validDTO, new WaterHeater(new WaterHeaterSpec()));
    }

}
