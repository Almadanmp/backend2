package pt.ipp.isep.dei.project.model.room;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ipp.isep.dei.project.model.Reading;
import pt.ipp.isep.dei.project.model.device.*;
import pt.ipp.isep.dei.project.model.device.devicespecs.FridgeSpec;
import pt.ipp.isep.dei.project.model.device.devicespecs.KettlerSpec;
import pt.ipp.isep.dei.project.model.device.devicespecs.WaterHeaterSpec;
import pt.ipp.isep.dei.project.model.sensortype.SensorType;
import pt.ipp.isep.dei.project.repository.RoomSensorRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Room tests class.
 */
@ExtendWith(MockitoExtension.class)
class RoomTest {
    // Common testing artifacts for this class.


    private static Room validRoom; // Room with a valid temperature sensor with valid readings, and a valid device.
    private RoomSensor firstValidSensor; // Valid temperature sensor with valid readings.
    private RoomSensor secondValidSensor; // Valid temperature sensor without readings.
    private Device validDevice; // Valid device, namely of WaterHeater type.
    private Reading validReading; // Valid temperature reading at February 2, 2018, 00:00:00.

    @Mock
    RoomSensorRepository roomSensorRepository;

    @BeforeEach
    void arrangeArtifacts() {
        validRoom = new Room("Bedroom", "Double Bedroom", 2, 30, 40, 10, "Room1", "Grid1");
        firstValidSensor = new RoomSensor("T23875", "tempOne", new SensorType("temperature", "Celsius"), new Date(), "RoomDF");
        firstValidSensor.setActive(true);
        secondValidSensor = new RoomSensor("T1234", "tempTwo", new SensorType("temperature", "Celsius"), new Date(), "RoomDF");
        secondValidSensor.setActive(true);
        validDevice = new WaterHeater(new WaterHeaterSpec());
        validDevice.setName("WaterHeater");
        validDevice.setNominalPower(21.0);
        validDevice.setAttributeValue(WaterHeaterSpec.HOT_WATER_TEMP, 12D);
        validDevice.setAttributeValue(WaterHeaterSpec.VOLUME_OF_WATER, 40D);
        validDevice.setAttributeValue(WaterHeaterSpec.PERFORMANCE_RATIO, 234D);
        validDevice.setAttributeValue(WaterHeaterSpec.VOLUME_OF_WATER_HEAT, 30D);
        validRoom.addDevice(validDevice);
        validReading = new Reading(21, new GregorianCalendar(2018, Calendar.FEBRUARY, 2).
                getTime(), "C", "Test");

    }

    @Test
    void seeIfGetRoomSensorByIDWorks() {
        //Arrange

        validRoom.addSensor(firstValidSensor);
        validRoom.addSensor(secondValidSensor);

        //Act

        RoomSensor actualResult = validRoom.getRoomSensorByID("T1234");

        //Assert

        assertEquals(secondValidSensor, actualResult);
    }

    @Test
    void seeIfGetRoomSensorByIDWorksWhenSensorDoesNotExist() {
        //Arrange

        validRoom.addSensor(firstValidSensor);

        // Assert

        assertThrows(IllegalArgumentException.class,
                () -> validRoom.getRoomSensorByID("invalidSensorID"));
    }


    @Test
    void seeIfConstructorIsAccessed() {
        // Assert

        Room room = new Room();

        // Act

        String actualResult = room.getId();

        // Assert

        assertNull(actualResult);
    }

    @Test
    void seeIfRemoveDeviceWorks() {
        // Act

        boolean actualResult = validRoom.removeDevice(validDevice);

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void seeIfRemoveDeviceWorksNoDevice() {
        // Arrange

        validRoom.removeDevice(validDevice);

        // Act

        boolean actualResult = validRoom.removeDevice(validDevice);

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfBuildDeviceListStringWorks() {
        // Arrange

        String expectedResult = "---------------\n" +
                "0) device Name: WaterHeater, device Type: WaterHeater, device Nominal Power: 21.0\n" +
                "---------------\n";

        // Act

        String actualResult = validRoom.buildDeviceListString();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfEqualsWorksNull() {
        // Act

        boolean result = validRoom.equals(null); // Needed for Sonarqube testing purposes.

        // Assert

        assertFalse(result);
    }

    @Test
    void seeIfEqualsWorkDifClass() {
        // Arrange

        SensorType testType = new SensorType("Rainfall", "l/m2");

        // Act

        boolean result = validRoom.equals(testType); // Needed for sonarqube testing purposes.

        // Assert

        assertFalse(result);
    }

    @Test
    void seeIfPrintRoomWorks() {
        // Arrange
        String expectedResult = "Bedroom, 2, 30.0, 40.0, 10.0.\n";

        // Act

        String actualResult = validRoom.buildString();

        // Assert

        assertEquals(expectedResult, actualResult);
    }


    @Test
    void seeIfGetNominalPowerWorksNoDevices() {
        // Arrange

        validRoom.removeDevice(validDevice);

        // Act

        double actualResult = validRoom.getNominalPower();

        // Assert

        Assertions.assertEquals(0, actualResult);
    }

    @Test
    void seeIfGetNominalPowerWorks() {
        // Arrange

        double expectedResult = 21;

        // Act

        double actualResult = validRoom.getNominalPower();

        // Assert

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetEstimateConsumptionByDeviceTypeWorks() {
        // Arrange

        Room room = new Room("Room", "Description", 2, 2, 2, 2,"House", "EnergyGrid");
        Device fridge = new Fridge(new FridgeSpec());
        fridge.setName("FridgeOne");
        fridge.setNominalPower(20);

        room.addDevice(fridge);
        room.addDevice(validDevice);

        double expectedResult = 97.97112;

        // Act

        double actualResult = room.getEstimateConsumptionOverTimeByDeviceType("WaterHeater", 1440);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddDeviceWorksDuplicate() {
        // Act

        boolean actualResult = validRoom.addDevice(validDevice);

        // Assert

        assertFalse(actualResult);
    }

    @Test
    void seeIfAddDeviceWorksTrue() {
        // Arrange

        validRoom.removeDevice(validDevice);

        // Act

        boolean actualResult = validRoom.addDevice(validDevice);

        // Assert

        assertTrue(actualResult);
    }

    @Test
    void getRoomDevicesOfGivenTypeSuccess() {
        // Arrange

        DeviceList expectedResult = new DeviceList();
        expectedResult.add(validDevice);

        // Act

        DeviceList actualResult = validRoom.getDevicesOfGivenType("WaterHeater");

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getRoomDevicesOfGivenTypeFails() {
        // Arrange

        DeviceList expectedResult = new DeviceList();

        // Act

        DeviceList actualResult = validRoom.getDevicesOfGivenType("MassageChair");

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetDeviceListWorks() {
        // Arrange

        DeviceList expectedResult = new DeviceList();
        expectedResult.add(validDevice);

        // Act

        DeviceList actualResult = validRoom.getDeviceList();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetDeviceListWorksNoDevices() {
        // Arrange

        validRoom.removeDevice(validDevice);
        DeviceList expectedResult = new DeviceList();

        // Act

        DeviceList actualResult = validRoom.getDeviceList();

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddRooMDevicesToDeviceListWorks() {
        // Arrange

        DeviceList expectedResult = new DeviceList();
        expectedResult.add(validDevice);
        DeviceList actualResult = new DeviceList();

        // Act

        validRoom.addRoomDevicesToDeviceList(actualResult);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfAddRoomDevicesToDeviceListWorksAlreadyContained() {
        // Arrange

        DeviceList expectedResult = new DeviceList();
        expectedResult.add(validDevice);
        DeviceList actualResult = new DeviceList();
        actualResult.add(validDevice);

        // Act

        validRoom.addRoomDevicesToDeviceList(actualResult);

        // Assert

        assertEquals(expectedResult, actualResult);
    }


    @Test
    void seeIfGetEnergyConsumptionWorks() {
        // Arrange

        double expectedResult = 1.4287455;

        // Act

        double actualResult = validRoom.getEnergyConsumption(21);

        // Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getByIndexWithEmptyDeviceList() {
        //Arrange

        Room noDevicesRoom = new Room("noDevices", "noDevices", 3, 24, 25, 3, "Room1", "Grid1");

        //Act

        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> noDevicesRoom.getDeviceByIndex(0));

        //Assert

        assertEquals("The device list is empty.", exception.getMessage());
    }

    @Test
    void deviceListSize() {
        //Arrange

        Room emptyDeviceList = new Room("emptyDeviceList", "emptyDeviceList", 2, 20, 20, 3, "Room1", "Grid1");

        //Act

        int actualResult1 = emptyDeviceList.getDeviceListSize();

        //Assert Empty List

        Assertions.assertEquals(0, actualResult1);

        //Act

        int actualResult2 = validRoom.getDeviceListSize();

        //Assert One Grid

        Assertions.assertEquals(1, actualResult2);
    }

    @Test
    void seeIfIsDeviceListEmptyWorks() {
        //Arrange

        Room emptyDeviceList = new Room("emptyDeviceList", "emptyDeviceList", 2, 20, 20, 3, "Room1", "Grid1");

        //Act

        boolean actualResult1 = emptyDeviceList.isDeviceListEmpty();
        boolean actualResult2 = validRoom.isDeviceListEmpty();

        //Assert

        assertTrue(actualResult1);
        assertFalse(actualResult2);
    }

    @Test
    void seeIfGetDeviceByIndexWorks() {

        //Act

        Device actualResult = validRoom.getDeviceByIndex(0);

        //Assert

        assertEquals(validDevice, actualResult);
    }

    @Test
    void seeIfGetDeviceByIndexThrowsException() {

        //Arrange

        Room emptyDeviceList = new Room("emptyDeviceList", "emptyDeviceList", 2, 20, 20, 3, "Room1", "Grid1");

        //Assert

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> emptyDeviceList.getDeviceByIndex(0));
    }

    @Test
    void seeIfBuildDevicesStringByTypeWorks() {

        //Arrange

        Room emptyDeviceList = new Room("emptyDeviceList", "emptyDeviceList", 2, 20, 20, 3, "Room1", "Grid1");
        Device kettler = new Kettler(new KettlerSpec());
        validRoom.addDevice(kettler);
        //Act

        String actualResult1 = emptyDeviceList.buildDevicesStringByType("WaterHeater");
        String actualResult2 = validRoom.buildDevicesStringByType("WaterHeater");


        //Assert

        assertEquals("", actualResult1);
        assertEquals("Device type: WaterHeater | Device name: WaterHeater | Nominal power: 21.0 | Room: Bedroom | \n", actualResult2);
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
    void seeIfGetRoomSensorsOfGivenTypeWorks() {
        //Arrange
        List<RoomSensor> roomSensorList = new ArrayList<>();
        RoomSensor humiditySensor = new RoomSensor("H1", "HumidityOne", new SensorType("humidity", "g/m3"), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), validRoom.getId());
        roomSensorList.add(firstValidSensor);
        roomSensorList.add(humiditySensor);
        List<RoomSensor> expectedResult = new ArrayList<>();
        expectedResult.add(firstValidSensor);
        validRoom.setRoomSensors(roomSensorList);
        //Act
        List<RoomSensor> actualResult = validRoom.getRoomSensorsOfGivenType("temperature");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetCurrentRoomTemperatureWorks() {
        //Arrange
        List<Reading> readingList = new ArrayList<>();
        readingList.add(validReading);
        List<RoomSensor> roomSensorList = new ArrayList<>();
        firstValidSensor.setReadings(readingList);
        roomSensorList.add(firstValidSensor);
        readingList.add(validReading);
        roomSensorList.add(firstValidSensor);
        validRoom.setRoomSensors(roomSensorList);
        double expectedResult = 21;
        //Act
        double actualResult = validRoom.getCurrentRoomTemperature();
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetCurrentRoomTemperatureThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validRoom.getCurrentRoomTemperature());
    }

    @Test
    void seeIfGetMaxTemperatureOnGivenDayWorks() {
        //Arrange
        List<Reading> readingList = new ArrayList<>();
        readingList.add(validReading);
        Reading reading = new Reading(30, new GregorianCalendar(2018, Calendar.FEBRUARY, 2).
                getTime(), "C", "Test");
        readingList.add(reading);
        List<RoomSensor> roomSensorList = new ArrayList<>();
        firstValidSensor.setReadings(readingList);
        roomSensorList.add(firstValidSensor);
        readingList.add(validReading);
        roomSensorList.add(firstValidSensor);
        validRoom.setRoomSensors(roomSensorList);
        //Act
        double actualResult = validRoom.getMaxTemperatureOnGivenDay(new GregorianCalendar(2018, Calendar.FEBRUARY, 2).getTime());
        //Assert
        assertEquals(30, actualResult);
    }

    @Test
    void seeIfGetMaxTemperatureOnGivenDayThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> validRoom.getMaxTemperatureOnGivenDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    void seeIfGetMaxTemperatureOnGivenDayThrowsNoSuchElementException() {
        //Arrange
        List<Reading> readingList = new ArrayList<>();
        readingList.add(validReading);
        List<RoomSensor> roomSensorList = new ArrayList<>();
        firstValidSensor.setReadings(readingList);
        roomSensorList.add(firstValidSensor);
        readingList.add(validReading);
        roomSensorList.add(firstValidSensor);
        validRoom.setRoomSensors(roomSensorList);
        //Assert
        assertThrows(NoSuchElementException.class, () -> validRoom.getMaxTemperatureOnGivenDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    void seeIfAddSensorWorks() {
        //Act
        boolean actualResult = validRoom.addSensor(firstValidSensor);
        boolean actualResult1 = validRoom.addSensor(firstValidSensor);
        //Assert
        assertTrue(actualResult);
        assertFalse(actualResult1);
    }

    @Test
    void seeIfGetSensorWorks() {
        //Arrange
        validRoom.addSensor(firstValidSensor);
        RoomSensor expectedResult = firstValidSensor;
        //Act
        RoomSensor actualResult = validRoom.getSensor(0);
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetRoomSensorsWorks() {
        //Arrange
        List<RoomSensor> expectedResult = new ArrayList<>();
        expectedResult.add(firstValidSensor);
        validRoom.setRoomSensors(expectedResult);
        //Act
        List<RoomSensor> actualResult = validRoom.getRoomSensors();
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfGetHouseIdWorks(){
        //Arrange
        String expectedResult ="Room1";
        //Act
        String actualResult = validRoom.getHouseID();
        //Assert
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void seeIfGetEnergyGridIdWorks() {
        //Arrange
        String expectedResult = "Grid1";
        //Act
        String actualResult = validRoom.getEnergyGridID();
        //Assert
        assertEquals(expectedResult,actualResult);
    }
}