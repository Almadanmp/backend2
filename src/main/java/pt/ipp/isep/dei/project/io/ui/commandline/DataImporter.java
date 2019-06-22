package pt.ipp.isep.dei.project.io.ui.commandline;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ipp.isep.dei.project.controller.controllercli.HouseConfigurationController;
import pt.ipp.isep.dei.project.controller.controllercli.ReaderController;
import pt.ipp.isep.dei.project.io.ui.utils.InputHelperUI;
import pt.ipp.isep.dei.project.model.areatype.AreaTypeRepository;
import pt.ipp.isep.dei.project.model.bridgeservices.EnergyGridRoomService;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicAreaRepository;
import pt.ipp.isep.dei.project.model.house.House;
import pt.ipp.isep.dei.project.model.sensortype.SensorTypeRepository;
import pt.ipp.isep.dei.project.model.user.User;
import pt.ipp.isep.dei.project.model.user.UserRepository;
import pt.ipp.isep.dei.project.model.repository.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class DataImporter {

    @Autowired
    private SensorTypeCrudRepo sensorTypeCrudRepo;
    @Autowired
    private AreaTypeCrudRepo areaTypeCrudRepo;
    @Autowired
    private GeographicAreaCrudRepo geographicAreaCrudRepo;
    @Autowired
    private EnergyGridCrudRepo energyGridCrudRepo;
    @Autowired
    private RoomCrudRepo roomCrudRepo;

    @Autowired
    GeographicAreaRepository geographicAreaRepository;
    @Autowired
    SensorTypeRepository sensorTypeRepository;
    @Autowired
    AreaTypeRepository areaTypeRepository;
    @Autowired
    EnergyGridRoomService energyGridRoomService;

    @Autowired
    InputHelperUI inputHelperUI;
    @Autowired
    GASettingsUI gaSettingsUI;
    @Autowired
    HouseConfigurationUI houseConfigurationUI;
    @Autowired
    private ReaderController readerController;
    @Autowired
    private HouseConfigurationController houseConfigurationController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    void importData(House house) {
        System.out.println("Clearing data...");
        sensorTypeCrudRepo.deleteAll();
        areaTypeCrudRepo.deleteAll();
        geographicAreaCrudRepo.deleteAll();
        energyGridCrudRepo.deleteAll();
        roomCrudRepo.deleteAll();
        System.out.println("Data successfully cleared.");
        System.out.println("Importing files...");
        System.out.println("Loading...");
        FileInputUtils fileUtils = new FileInputUtils();
        try {
            fileUtils.getSensorTypeConfig();
            fileUtils.addSensorTypesToRepository(sensorTypeRepository);
        } catch (IOException ae) {
            ae.printStackTrace();
        }
        try {
            fileUtils.getAreaTypeConfig();
            fileUtils.addAreatypesToRepository(areaTypeRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputHelperUI.acceptPathJSONorXMLAndReadFile("src/test/resources/geoAreaFiles/DataSet_sprint07_GA.json", geographicAreaRepository, sensorTypeRepository, areaTypeRepository);
        readerController.readJSONAndDefineHouse(house, "src/test/resources/houseFiles/DataSet_sprint06_HouseData.json");
        houseConfigurationController.readSensors("src/test/resources/houseSensorFiles/DataSet_sprint07_HouseSensors.json");
        createUsers();
        System.out.println("...");
        System.out.println("Data imported successfully");

    }

    private void createUsers() {

        // Delete all
        this.userRepository.deleteAll();

        // Crete users
        User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN", "None");

        User powerUser = new User("powerUser", passwordEncoder.encode("power123"), "POWER", "None");

        User roomOwner = new User("roomOwner", passwordEncoder.encode("room123"), "ROOMOWNER", "None");

        User regular = new User("regularUser", passwordEncoder.encode("regular123"), "REGULAR", "House Monitoring");

        List<User> users = Arrays.asList(admin, powerUser, roomOwner, regular);

        // Save to db
        this.userRepository.saveAll(users);

    }


}
