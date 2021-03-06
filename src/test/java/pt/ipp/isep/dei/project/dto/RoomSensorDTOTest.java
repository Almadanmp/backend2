package pt.ipp.isep.dei.project.dto;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomSensorDTOTest {

    @Test
    void seeIfSetUnitWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setUnits("Unit");

        //Act

        String actualResult1 = houseSensorDTO1.getUnits();

        //Assert
        assertEquals("Unit", actualResult1);
    }

    @Test
    void seeIfSetIdWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setSensorId("Id");

        //Act

        String actualResult1 = houseSensorDTO1.getSensorId();

        //Assert
        assertEquals("Id", actualResult1);
    }

    @Test
    void seeIfSetReadingList() {
        //Arrange

        SimpleDateFormat validSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try {
            date = validSdf.parse("10/01/2018 09:59:59");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ReadingDTO> readingDTOList = new ArrayList<>();
        ReadingDTO readingDTO = new ReadingDTO();
        readingDTO.setDate(date);
        readingDTO.setSensorId("Id");
        readingDTOList.add(readingDTO);

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setReadingList(readingDTOList);

        List<ReadingDTO> expectedResult = new ArrayList<>();
        expectedResult.add(readingDTO);

        //Act

        List<ReadingDTO> actualResult1 = houseSensorDTO1.getReadingList();

        //Assert
        assertEquals(expectedResult, actualResult1);
    }

    @Test
    void seeIfSetRoomIdWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setRoomID("Id");

        //Act

        String actualResult1 = houseSensorDTO1.getRoomID();

        //Assert
        assertEquals("Id", actualResult1);
    }

    @Test
    void seeIfSetTypeSensorWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setTypeSensor("Type");

        //Act

        String actualResult1 = houseSensorDTO1.getType();

        //Assert
        assertEquals("Type", actualResult1);
    }

    @Test
    void seeIfSetActiveWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setActive(true);

        //Act

        boolean actualResult1 = houseSensorDTO1.getActive();

        //Assert

        assertTrue(actualResult1);
    }

    @Test
    void seeIfSetInactiveWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setActive(false);

        //Act

        boolean actualResult1 = houseSensorDTO1.getActive();

        //Assert

        assertFalse(actualResult1);
    }


    @Test
    void seeIfEqualsWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setName("Name1");

        RoomSensorDTO houseSensorDTO2 = new RoomSensorDTO();
        houseSensorDTO2.setName("Name1");

        RoomSensorDTO houseSensorDTO3 = new RoomSensorDTO();
        houseSensorDTO3.setName("Name2");

        //Act

        boolean actualResult1 = houseSensorDTO1.equals(houseSensorDTO1);
        boolean actualResult2 = houseSensorDTO1.equals(houseSensorDTO2);
        boolean actualResult3 = houseSensorDTO1.equals(houseSensorDTO3);
        boolean actualResult4 = houseSensorDTO1.equals(4D);

        //Assert
        assertTrue(actualResult1);
        assertTrue(actualResult2);
        assertFalse(actualResult3);
        assertFalse(actualResult4);
    }

    @Test
    void seeIfHashCodeWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();

        //Assert
        assertEquals(1, houseSensorDTO1.hashCode());
    }

    @Test
    void seeIfSetDateWorks() {
        //Arrange

        RoomSensorDTO houseSensorDTO1 = new RoomSensorDTO();
        houseSensorDTO1.setDateStartedFunctioning("2/2/2018");

        //Act

        String actualResult1 = houseSensorDTO1.getDateStartedFunctioning();

        //Assert
        assertEquals("2/2/2018", actualResult1);
    }
}