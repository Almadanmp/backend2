package pt.ipp.isep.dei.project.controller.controllerweb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ipp.isep.dei.project.dto.AreaSensorDTO;
import pt.ipp.isep.dei.project.dto.GeographicAreaDTO;
import pt.ipp.isep.dei.project.dto.mappers.AreaSensorMapper;
import pt.ipp.isep.dei.project.dto.mappers.GeographicAreaMapper;
import pt.ipp.isep.dei.project.model.Local;
import pt.ipp.isep.dei.project.model.geographicarea.AreaSensor;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicArea;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicAreaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = HibernateJpaAutoConfiguration.class)
class SensorSettingsWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    GeographicAreaRepository geographicAreaRepository;

    @InjectMocks
    SensorSettingsWebController sensorSettingsWebController;

    private long id;
    private AreaSensor validAreaSensor;
    private GeographicArea validGeographicArea;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sensorSettingsWebController).build();
        id = 1;
        validAreaSensor = new AreaSensor("RF12345", "Meteo station ISEP - rainfall", "rainfall", new Local(45, 45, 45), new Date());
        validGeographicArea = new GeographicArea("ISEP", "urban area", 300, 200,
                new Local(45, 45, 45));
        validGeographicArea.setId(id);
        System.out.println(Runtime.getRuntime().freeMemory());
    }

    @Test
    void seeIfIntroWorks() {
        //Arrange

        String expectedResult = "Welcome to the Sensor Settings Menu: \nGET[/sensorsettings/areas] \nGET[/sensorsettings/areas/{id}] " +
                "\nGET[/sensorsettings/areas/{id}/sensors] \nPOST[/areas/{id}/sensors] \nPUT[/sensorsettings/areas/{id}/sensors/{id2}] " +
                "\nDELETE[/sensorsettings/areas/{id}/sensors/{id2}]";

        //Act

        String actualResult = sensorSettingsWebController.intro();

        //Assert

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfIntroFails() {
        //Arrange

        String expectedResult = "Welcome to the Sensor Settings Menu:";

        //Act

        String actualResult = sensorSettingsWebController.intro();

        //Assert

        assertNotEquals(expectedResult, actualResult);
    }

    @Test
    void seeIfRemoveAreaSensorWorks() throws Exception {
        // Arrange

        ArrayList<AreaSensor> areaSensorArrayList = new ArrayList<>();
        areaSensorArrayList.add(validAreaSensor);
        validGeographicArea.setAreaSensors(areaSensorArrayList);
        validGeographicArea.setLocation(new Local(45, 45, 45));

        String id2 = "RF12345";
        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);
        Mockito.doReturn(true).when(this.geographicAreaRepository).removeSensorDTO(GeographicAreaMapper.objectToDTO(validGeographicArea), id2);
        Mockito.doNothing().when(this.geographicAreaRepository).updateAreaDTO(GeographicAreaMapper.objectToDTO(validGeographicArea));

        // Act & Assert
        this.mockMvc.perform(delete("/sensorsettings/areas/1/sensors/RF12345", validAreaSensor))
                .andExpect(status().isOk());
    }

    @Test
    void seeIfRemoveAreaSensorWorksForNotFound() throws Exception {
        // Arrange

        ArrayList<AreaSensor> areaSensorArrayList = new ArrayList<>();
        areaSensorArrayList.add(validAreaSensor);
        validGeographicArea.setAreaSensors(areaSensorArrayList);
        validGeographicArea.setLocation(new Local(45, 45, 45));

        String id2 = "RF12345";
        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);
        Mockito.doReturn(false).when(this.geographicAreaRepository).removeSensorDTO(GeographicAreaMapper.objectToDTO(validGeographicArea), id2);

        // Act & Assert
        this.mockMvc.perform(delete("/sensorsettings/areas/1/sensors/RF12345"))
                .andExpect(status().isNotFound());
    }

    @Test
    void seeIfCreateAreaSensorWorks() {

        // Arrange

        AreaSensor sensor2 = new AreaSensor("test", "test", "rainfall", new Local(45, 45, 45), new Date());
        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = AreaSensorMapper.objectToDTO(sensor2);

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);
        Mockito.doReturn(true).when(this.geographicAreaRepository).addSensorDTO(GeographicAreaMapper.objectToDTO(validGeographicArea), areaSensorDTO);

        // Perform

        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO, id);

        // Assert

        assertEquals(HttpStatus.CREATED, actualResult.getStatusCode());
    }

    @Test
    void seeIfCreateAreaSensorFailsBadRequest() throws Exception {

        // Arrange

        // Perform

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        this.mockMvc.perform(post("/sensorsettings/areas/1/sensors").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"sensorId\": \"RF12345\",\n" +
                        "  \"name\": \"Meteo\",\n" +
                        "  \"typeSensor\": \"temperature\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void seeIfRetrieveGAWorks() throws Exception {

        // Arrange

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        // Perform

        this.mockMvc.perform(get("/sensorsettings/areas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void seeIfRetrieveAllSensorsWorks() throws Exception {

        // Arrange

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        // Perform

        this.mockMvc.perform(get("/sensorsettings/areas/1/sensors"))
                .andExpect(status().isOk());
    }

    @Test
    void seeIfRetrieveAllGeographicAreasWorks() throws Exception {

        // Arrange

        // Perform

        this.mockMvc.perform(get("/sensorsettings/areas"))
                .andExpect(status().isOk());
    }

    @Test
    void seeIfGetAreaSensorWorks() {
        //Arrange

        GeographicAreaDTO geographicAreaDTO = new GeographicAreaDTO();

        geographicAreaDTO.setId(12L);

        AreaSensorDTO areaSensorDTO = new AreaSensorDTO();
        areaSensorDTO.setId("sensor");

        geographicAreaDTO.addSensor(areaSensorDTO);

        Mockito.when(geographicAreaRepository.getDTOById(geographicAreaDTO.getId())).thenReturn(geographicAreaDTO);

        //Act

        AreaSensorDTO actualResult = sensorSettingsWebController.getAreaSensor(12L, "sensor");

        //Assert

        assertEquals(areaSensorDTO, actualResult);
    }

    @Test
    void deactivateAreaSensor() {

        Mockito.doReturn(true).when(geographicAreaRepository).deactivateAreaSensor(any(long.class), any(String.class));

        ResponseEntity<String> expectedResult = new ResponseEntity<>("The Area Sensor has been deactivated.", HttpStatus.OK);

        //Act
        ResponseEntity<Object> actualResult = sensorSettingsWebController.deactivateAreaSensor(1L, "id");

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void addDaughterAreaContainsDaughter() {

        Mockito.doReturn(false).when(geographicAreaRepository).deactivateAreaSensor(any(long.class), any(String.class));

        ResponseEntity<String> expectedResult = new ResponseEntity<>("The Area Sensor is already deactivated", HttpStatus.CONFLICT);

        //Act
        ResponseEntity<Object> actualResult = sensorSettingsWebController.deactivateAreaSensor(6L, "ID");

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void addDaughterAreaNotFound() {

        Mockito.doThrow(NoSuchElementException.class).when(geographicAreaRepository).deactivateAreaSensor(any(long.class), any(String.class));

        ResponseEntity<Object> actualResult = sensorSettingsWebController.deactivateAreaSensor(6L, "id");

        assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
    }


    @Test
    void seeIfCreateAreaSensorFailsWithConflict() {

        AreaSensorDTO areaSensorDTO2 = AreaSensorMapper.objectToDTO(validAreaSensor);

        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO2, 12L);

        Assertions.assertEquals(HttpStatus.CONFLICT, actualResult.getStatusCode());
    }

    @Test
    void seeIfCreateAreaSensorFailsEmptyName() {

        // Arrange

        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = new AreaSensorDTO();
        areaSensorDTO.setName("");
        areaSensorDTO.setId("RF1234");
        areaSensorDTO.setUnits("mm");
        areaSensorDTO.setTypeSensor("temperature");
        areaSensorDTO.setDateStartedFunctioning("2018-10-12");

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        // Perform

        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO, id);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, actualResult.getStatusCode());
    }

    @Test
    void seeIfCreateAreaSensorFailsWrongGA() {

        // Arrange

        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = new AreaSensorDTO();

        // Perform
        assertThrows(IllegalArgumentException.class,
                () -> sensorSettingsWebController.createAreaSensor(areaSensorDTO, new Long(null)));

    }

    @Test
    void seeIfCreateAreaSensorFails2() {

        // Arrange

        AreaSensor sensor2 = new AreaSensor("test", "test", "rainfall", new Local(45, 45, 45), new Date());
        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = AreaSensorMapper.objectToDTO(sensor2);


        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);
        Mockito.when(this.geographicAreaRepository.getDTOById(id)).thenThrow(new IllegalArgumentException());

        // Perform

        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO, id);

        assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
    }

    @Test
    void seeIfCreateAreaSensorFailsNullName() {

        // Arrange

        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = new AreaSensorDTO();
        areaSensorDTO.setName(null);
        areaSensorDTO.setId("RF1234");
        areaSensorDTO.setUnits("mm");
        areaSensorDTO.setTypeSensor("temperature");
        areaSensorDTO.setDateStartedFunctioning("2018-10-12");

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        // Perform


        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO, id);

        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
    }

    @Test
    void seeIfCreateAreaSensorFailsNullId() {

        // Arrange

        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = new AreaSensorDTO();
        areaSensorDTO.setName("RF1234");
        areaSensorDTO.setId(null);
        areaSensorDTO.setUnits("mm");
        areaSensorDTO.setTypeSensor("temperature");
        areaSensorDTO.setDateStartedFunctioning("2018-10-12");

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        // Perform


        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO, id);

        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
    }

    @Test
    void seeIfCreateAreaSensorFailsNullDate() {

        // Arrange

        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = new AreaSensorDTO();
        areaSensorDTO.setName("RF1234");
        areaSensorDTO.setId("RF1234");
        areaSensorDTO.setUnits("mm");
        areaSensorDTO.setTypeSensor("temperature");
        areaSensorDTO.setDateStartedFunctioning(null);

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        // Perform


        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO, id);

        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
    }

    @Test
    void seeIfCreateAreaSensorFailsNullType() {

        // Arrange

        List<AreaSensor> sensors = new ArrayList<>();
        sensors.add(validAreaSensor);
        validGeographicArea.setAreaSensors(sensors);
        AreaSensorDTO areaSensorDTO = new AreaSensorDTO();
        areaSensorDTO.setName("RF1234");
        areaSensorDTO.setId("RF1234");
        areaSensorDTO.setUnits("mm");
        areaSensorDTO.setTypeSensor(null);
        areaSensorDTO.setDateStartedFunctioning("2018-10-12");

        Mockito.doReturn(GeographicAreaMapper.objectToDTO(validGeographicArea)).when(this.geographicAreaRepository).getDTOById(id);

        // Perform

        ResponseEntity<Object> actualResult = sensorSettingsWebController.createAreaSensor(areaSensorDTO, id);

        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
    }
}