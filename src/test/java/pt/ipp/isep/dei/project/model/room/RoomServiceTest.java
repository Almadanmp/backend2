package pt.ipp.isep.dei.project.model.room;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ipp.isep.dei.project.controller.ReaderController;
import pt.ipp.isep.dei.project.model.Reading;
import pt.ipp.isep.dei.project.model.device.Device;
import pt.ipp.isep.dei.project.model.device.DeviceList;
import pt.ipp.isep.dei.project.model.device.WaterHeater;
import pt.ipp.isep.dei.project.model.device.devicespecs.WaterHeaterSpec;
import pt.ipp.isep.dei.project.model.energy.EnergyGridService;
import pt.ipp.isep.dei.project.model.sensortype.SensorType;
import pt.ipp.isep.dei.project.repository.RoomRepository;
import pt.ipp.isep.dei.project.repository.RoomSensorRepository;
import pt.ipp.isep.dei.project.repository.SensorTypeRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RoomList tests class.
 */
@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    private Room validRoom;
    private Device validDevice;
    private RoomSensor firstValidRoomSensor;
    private RoomSensor secondValidRoomSensor;
    private RoomSensor thirdValidRoomSensor;
    private Date validDate1; // Date 21/11/2018
    private Date validDate2; // Date 03/09/2018


    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomSensorRepository roomSensorRepository;
    @Mock
    private SensorTypeRepository sensorTypeRepository;

    private RoomService validRoomService;

    private static final Logger logger = Logger.getLogger(ReaderController.class.getName());

    @BeforeEach
    void arrangeArtifacts() {
        MockitoAnnotations.initMocks(this);
        validRoomService = new RoomService(this.roomRepository, this.roomSensorRepository, this.sensorTypeRepository);
        validRoom = new Room("Kitchen", "1st Floor Kitchen", 1, 4, 5, 3, "Room1", "Grid1");
        validRoomService.add(validRoom);
        validDevice = new WaterHeater(new WaterHeaterSpec());
        validDevice.setName("WaterHeater");
        validDevice.setNominalPower(21.0);
        validDevice.setAttributeValue(WaterHeaterSpec.HOT_WATER_TEMP, 12D);
        validDevice.setAttributeValue(WaterHeaterSpec.VOLUME_OF_WATER, 40D);
        validDevice.setAttributeValue(WaterHeaterSpec.PERFORMANCE_RATIO, 234D);
        validDevice.setAttributeValue(WaterHeaterSpec.VOLUME_OF_WATER_HEAT, 30D);
        SimpleDateFormat validSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            validDate1 = validSdf.parse("21/11/2018 00:00:00");
            validDate2 = validSdf.parse("03/09/2018 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        firstValidRoomSensor = new RoomSensor("T32875", "SensorOne", new SensorType("Temperature", "Celsius"), validDate1, "RoomDFS");
        firstValidRoomSensor.setActive(true);
        secondValidRoomSensor = new RoomSensor("T32876", "SensorTwo", new SensorType("Temperature", "Celsius"), new Date(), "RoomDFS");
        secondValidRoomSensor.setActive(true);
        thirdValidRoomSensor = new RoomSensor("T32877", "SensorThree", new SensorType("Rainfall", "l/m2"), new Date(), "RoomDFS");
    }

    @Test
    void seeIfAddAreaReadingsWorksWhenSensorIDIsInvalid() {
        // Arrange

        List<Reading> readings = new ArrayList<>();
        Reading reading = new Reading(21D, validDate1, "C", "sensorID");
        readings.add(reading);

        List<Room> rooms = new ArrayList<>();
        rooms.add(validRoom);
        validRoom.addSensor(firstValidRoomSensor);

        Mockito.when(roomRepository.findAll()).thenReturn(rooms);

        int expectedResult = 0;

        //Act

        int actualResult = validRoomService.addRoomReadings("invalidSensor", readings, logger);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddAreaReadingsWorks() {
        // Arrange

        List<Reading> readings = new ArrayList<>();
        Reading reading = new Reading(21D, validDate1, "C", "sensorID");
        readings.add(reading);

        List<Room> rooms = new ArrayList<>();
        rooms.add(validRoom);
        validRoom.addSensor(firstValidRoomSensor);

        Mockito.when(roomRepository.findAll()).thenReturn(rooms);

        int expectedResult = 1;

        //Act

        int actualResult = validRoomService.addRoomReadings("T32875", readings, logger);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddReadingsToRoomSensorWorks() {
        // Arrange

        List<Reading> readings = new ArrayList<>();
        Reading reading = new Reading(21D, validDate1, "C", "T32875");
        readings.add(reading);

        int expectedResult = 1;

        //Act

        int actualResult = validRoomService.addReadingsToRoomSensor(firstValidRoomSensor, readings, logger);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddReadingsToRoomSensorWorksWhenReadingIsFromBeforeSensorActivatingDate() {
        // Arrange

        List<Reading> readings = new ArrayList<>();
        Reading reading = new Reading(21D, validDate2, "C", "sensorID");
        readings.add(reading);

        int expectedResult = 0;

        //Act

        int actualResult = validRoomService.addReadingsToRoomSensor(firstValidRoomSensor, readings, logger);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddReadingsToRoomSensorWorksWhenReadingAlreadyExists() {
        // Arrange

        List<Reading> readings = new ArrayList<>();
        Reading reading = new Reading(21D, validDate1, "C", "sensorID");
        readings.add(reading);

        firstValidRoomSensor.addReading(reading);

        int expectedResult = 0;

        //Act

        int actualResult = validRoomService.addReadingsToRoomSensor(firstValidRoomSensor, readings, logger);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddReadingsToRoomSensorWorksWhenListIsEmpty() {
        // Arrange

        List<Reading> readings = new ArrayList<>();

        int expectedResult = 0;

        //Act

        int actualResult = validRoomService.addReadingsToRoomSensor(firstValidRoomSensor, readings, logger);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetRoomContainingSensorWithGivenIdWorks() {
        // Arrange

        List<Room> validList = new ArrayList<>();
        validRoom.addSensor(firstValidRoomSensor);
        validRoom.addSensor(secondValidRoomSensor);
        validList.add(validRoom);

        Mockito.when(roomRepository.findAll()).thenReturn(validList);

        //Act

        Room actualResult = validRoomService.getRoomContainingSensorWithGivenId("T32876");

        // Assert

        assertEquals(validRoom, actualResult);
    }

    @Test
    void seeIfGetRoomContainingSensorWithGivenIdWorksWhenSensorIdDoesNotExist() {
        // Arrange

        List<Room> emptyList = new ArrayList<>();

        Mockito.when(roomRepository.findAll()).thenReturn(emptyList);

        // Assert

        assertThrows(IllegalArgumentException.class,
                () -> validRoomService.getRoomContainingSensorWithGivenId("invalidSensorID"));
    }

    @Test
    void seeIfGetAllRoomsWorksNull() {
        // Arrange
        Mockito.when(roomRepository.findAll()).thenReturn(null);
        List<Room> expectedResult = new ArrayList<>();

        // Act
        List<Room> actualResult = validRoomService.getAllRooms();

        // Assert
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void seeIfRemoveRoom() {

        Mockito.when(roomRepository.findById(validRoom.getId())).thenReturn((Optional.of(validRoom)));


        //Assert
        assertTrue(validRoomService.removeRoom(validRoom));
    }

    @Test
    void seeIfDoNotRemoveRoom() {
        validRoomService.updateRoom(validRoom);
        //Assert
        assertFalse(validRoomService.removeRoom(validRoom));
    }
//
//    @Test
//    void seeIfAddRoom() {
//        Room room = new Room("Kitchen", "1st Floor Kitchen", 1, 4, 5, 3, "Room1", "Grid1");
//
//
//        Mockito.when(roomRepository.findByName(room.getName())).thenReturn(room);
//
//        assertTrue(validRoomService.addRoom(room));
//    }

    @Test
    void seeIfAddRoomCreate() {


        Room room = new Room("Kitchen", "1st Floor Kitchen", 1, 4, 5, 3, "Room1", "Grid1");

        validRoomService.addRoom(room);


        assertTrue(validRoomService.contains(room));
    }

    @Test
    void seeIfgetlistOfRooms() {

        List<Room> roomList = new ArrayList<>();

        Room room = new Room("Kitchen", "1st Floor Kitchen", 1, 4, 5, 3, "Room1", "Grid1");
        roomList.add(room);
        validRoomService.addRoom(room);


        assertEquals(roomList, validRoomService.getRooms());
    }

    @Test
    void seeIfGetDB() {
        String mockId = "SensorOne";

        Room room = new Room("Kitchen", "1st Floor Kitchen", 1, 4, 5, 3, "Room1", "Grid1");


        Mockito.when(roomRepository.findById(mockId)).thenReturn(Optional.of(room));

        Room result = validRoomService.getDB(mockId);

        assertEquals(result.getId(), room.getId());
        assertEquals(result.getId(), room.getId());
    }

    @Test
    void seeIfGetDBdNoSensor() {
        String mockId = "SensorOne";

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> validRoomService.getDB(mockId));

    }

    @Test
    void seeIfCreateRoom() {

    }

    @Test
    void seeIfIdExists() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(validRoom);
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        //Assert
        assertTrue(validRoomService.idExists(validRoom.getId()));
    }

    @Test
    void seeIfIdNotExists() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(validRoom);
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        //Assert
        assertFalse(validRoomService.idExists("Hall"));
    }

    @Test
    void seeIfBuildRoomListStringWorksEmptyListDB() {

        List<Room> rooms = new ArrayList<>();

        // Act

        String expectedResult = "Invalid List - List is Empty\n";

        // Assert

        assertEquals(expectedResult, validRoomService.buildRoomsAsString(rooms));
    }

    @Test
    void seeIfBuildRoomListStringWorksEmptyList() {


        // Act

        String expectedResult = "Invalid List - List is Empty\n";

        // Assert

        assertEquals(expectedResult, validRoomService.buildString());
    }

    @Test
    void seeIfBuildRoomListStringWorksList() {
        // Act
        List<Room> rooms = new ArrayList<>();
        rooms.add(validRoom);
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        String expectedResult = "---------------\n" +
                "0) Designation: Kitchen | Description: 1st Floor Kitchen | House Floor: 1 | Width: 4.0 | Length: 5.0 | Height: 3.0\n" +
                "---------------\n";

        // Assert

        assertEquals(expectedResult, validRoomService.buildString());
    }

    @Test
    void seeIfBuildRoomListStringWorksListDB() {
        // Act
        List<Room> rooms = new ArrayList<>();
        rooms.add(validRoom);
        String expectedResult = "---------------\n" +
                "Kitchen) Description: 1st Floor Kitchen | House Floor: 1 | Width: 4.0 | Length: 5.0 | Height: 3.0\n" +
                "---------------\n";

        // Assert

        assertEquals(expectedResult, validRoomService.buildRoomsAsString(rooms));
    }


    @Test
    void seeIfEqualsWorksSameObject() {
        // Arrange

        validRoomService.add(validRoom);

        // Act

        boolean actualResult = validRoom.equals(validRoom); // Needed for Sonarqube testing purposes.

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfGetDeviceList() {
        DeviceList deviceList = new DeviceList();
        validRoom.setDeviceList(deviceList);

        assertEquals(deviceList, validRoom.getDeviceList());
    }

    @Test
    void seeIfEqualsDifferentListContents() {

        // Arrange

        Room testRoom = new Room("Balcony", "4th Floor Balcony", 4, 2, 4, 3, "Room1", "Grid1");
        validRoomService.add(testRoom);
        validRoomService.add(validRoom);

        // Act

        boolean actualResult = validRoom.equals(validRoomService);

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfEqualsDifferentObjectTypes() {
        // Arrange

        Room room2 = new Room("Balcony", "3rd Floor Balcony", 3, 2, 4, 3, "Room1", "Grid1");
        validRoomService.add(validRoom);

        // Act

        boolean actualResult = validRoom.equals(room2); // Necessary for Sonarqube testing purposes.

        //Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfGetByIndexWorks() {
        //Arrange
        Room room = new Room("room", "Double Bedroom", 2, 20, 20, 4, "Room1", "Grid1");
        validRoomService.add(room);

        //Act

        Room actualResult1 = validRoomService.getRoom(0);
        Room actualResult2 = validRoomService.getRoom(1);

        //Assert

        assertEquals(validRoom, actualResult1);
        assertEquals(room, actualResult2);
    }

    @Test
    void getByIndexEmptyRoomList() {
        //Arrange

        RoomService emptyRoomService = new RoomService();

        //Act

        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> emptyRoomService.getRoom(0));

        //Assert

        assertEquals("The room list is empty.", exception.getMessage());
    }

    @Test
    void seeItGetDeviceListByTypeWorks() {
        // Arrange

        DeviceList expectedResult = new DeviceList();

        // Act

        DeviceList actualResult = validRoomService.getDeviceList();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeItGetDailyConsumptionByDevice() {
        validRoom.addDevice(validDevice);
        double expectedResult = 6.0;

        // Act

        double actualResult = validRoomService.getDailyConsumptionByDeviceType("WaterHeater", 89);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetNominalPower() {
        validRoom.addDevice(validDevice);
        //Assert
        assertEquals(21.0, validRoomService.getNominalPower());
    }


//    @Test
//    void seeIfBuildDeviceListByType() {
//        //Arrange
//        validRoom.addDevice(validDevice);
//
//        //Act
//        StringBuilder expectedResult = new StringBuilder();
//        expectedResult.append("Device type: WaterHeater | Device name: WaterHeater | Nominal power: 21.0 | Room: Kitchen | ");
//        // Assert
//
//        assertEquals(expectedResult, validRoomService.buildDeviceListByType("WaterHeater"));
//    }

//    @Test
//    void getElementsAsArray() {
//
//        //Arrange
//
//        Room[] expectedResult1 = new Room[0];
//        Room[] expectedResult2 = new Room[1];
//        Room[] expectedResult3 = new Room[2];
//
//        RoomService validRoomService2 = new RoomService();
//        validRoomService2.add(validRoom);
//        validRoomService2.add(new Room("room", "Single Bedroom", 2, 20, 20, 3, "Room1", "Grid1"));
//
//        expectedResult2[0] = validRoom;
//        expectedResult3[0] = validRoom;
//        expectedResult3[1] = new Room("room", "Single Bedroom", 2, 20, 20, 3, "Room1", "Grid1");
//
//        //Act
//
//        Room[] actualResult1 = validRoomService.getElementsAsArray();
//        Room[] actualResult2 = validRoomService.getElementsAsArray();
//        Room[] actualResult3 = validRoomService2.getElementsAsArray();
//
//        //Assert
//
//        assertArrayEquals(expectedResult1, actualResult1);
//        assertArrayEquals(expectedResult2, actualResult2);
//        assertArrayEquals(expectedResult3, actualResult3);
//    }

    @Test
    void seeIfCreateRoomWorks() {
        //Arrange

        Room room = new Room("kitchen", "Ground Floor Kitchen", 0, 15, 10, 2, "Room1", "Grid1");
        Room roomExpected = new Room("kitchen", "Ground Floor Kitchen", 0, 15, 10, 2, "Room1", "Grid1");

        //Act

        Room roomActual1 = validRoomService.createRoom("kitchen", "Ground Floor Kitchen", 0, 15, 10, 2, "Room1", "Grid1");

        //Assert

        assertEquals(roomExpected, roomActual1);

        //Arrange to check if room is created when it already exists in list

        validRoomService.add(room);

        //Act

        Room roomActual2 = validRoomService.createRoom("kitchen", "Ground Floor Kitchen", 0, 15, 10, 2, "Room1", "Grid1");

        //Assert
        assertEquals(roomExpected, roomActual2);
    }


    @Test
    void hashCodeDummyTest() {
        // Arrange

        int expectedResult = 1;

        // Act

        int actualResult = validRoom.hashCode();

        // Assert

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfEqualsWorks() {
        // Arrange

        validRoomService.add(validRoom);

        //Act

        boolean actualResult = validRoomService.equals(validRoomService);

        // Assert

        assertTrue(actualResult);
    }


    @Test
    void seeIfEqualsWorksFalseDifferentObject() {
        // Arrange

        EnergyGridService testList = new EnergyGridService();

        //Act

        boolean actualResult = validRoomService.equals(testList); // Needed for SonarQube testing purposes.

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfGetAllSensor() {

        List<RoomSensor> roomSensors = new ArrayList<>();
        validRoomService.addRoom(secondValidRoomSensor);

        Mockito.when(roomSensorRepository.findAll()).thenReturn(roomSensors);

        assertEquals(roomSensors, validRoomService.getAllSensor());
    }

    @Test
    void seeIfEqualsWorksOnSameObject() {
        //Act

        boolean actualResult = validRoomService.equals(validRoomService); // Required for Sonarqube testing purposes.

        //Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfEqualsWorksOnDiffObject() {
        //Act

        boolean actualResult = validRoomService.equals(20D); // Required for Sonarqube testing purposes.

        //Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfToStringWorks() {
        // Arrange

        List<RoomSensor> roomSensors = new ArrayList<>();
        roomSensors.add(secondValidRoomSensor);
        roomSensors.add(thirdValidRoomSensor);
        validRoom.setRoomSensors(roomSensors);
        String expectedResult = "---------------\n" +
                "ID: T32876 | SensorTwo | Type: Temperature | Active\n" +
                "ID: T32877 | SensorThree | Type: Rainfall | Active\n" +
                "---------------\n";

        // Act

        String actualResult = validRoom.buildRoomSensorsAsString();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfToStringWorksEmpty() {
        // Arrange
        String expectedResult = "Invalid List - List is Empty\n";

        // Act

        String actualResult = validRoom.buildRoomSensorsAsString();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

}