package pt.ipp.isep.dei.project.io.ui;

import pt.ipp.isep.dei.project.controller.SensorSettingsController;
import pt.ipp.isep.dei.project.io.ui.utils.DateUtils;
import pt.ipp.isep.dei.project.io.ui.utils.InputHelperUI;
import pt.ipp.isep.dei.project.io.ui.utils.UtilsUI;
import pt.ipp.isep.dei.project.model.*;
import pt.ipp.isep.dei.project.model.sensor.AreaSensor;
import pt.ipp.isep.dei.project.model.sensor.SensorType;
import pt.ipp.isep.dei.project.model.sensor.SensorTypeService;

import java.util.Date;
import java.util.Scanner;

class SensorSettingsUI {
    private SensorSettingsController controller;

    SensorSettingsUI() {
        this.controller = new SensorSettingsController();
    }

    void run(GeographicAreaList geographicAreaList, SensorTypeService sensorTypeList) {
        if (geographicAreaList.isEmpty()) {
            System.out.println(UtilsUI.INVALID_GA_LIST);
            return;
        }

        boolean activeInput = true;
        int option;
        System.out.println("--------------\n");
        System.out.println("Sensor Settings\n");
        System.out.println("--------------\n");
        while (activeInput) {
            printOptionMessage();
            option = InputHelperUI.getInputAsInt();
            switch (option) {
                case 1:
                    runUS05(sensorTypeList);
                    activeInput = false;
                    break;
                case 2:
                    runUS06(geographicAreaList, sensorTypeList);
                    activeInput = false;
                    break;
                case 3:
                    displayList(sensorTypeList);
                    activeInput = false;
                    break;
                case 0:
                    return;
                default:
                    System.out.println(UtilsUI.INVALID_OPTION);
                    break;
            }
        }
    }

    /* LIST DISPLAY */

    private void displayList(SensorTypeService sensorTypeService) {
        System.out.println(controller.buildSensorTypesString(sensorTypeService));
    }


    /* USER STORY 005 - As an Administrator, I want to define the sensor types. */
    private void runUS05(SensorTypeService sensorTypeList) {
        SensorType sensorType = getInput05(sensorTypeList);
        boolean added = updateModel05(sensorType, sensorTypeList);
        displayState05(added);
    }

    private SensorType getInput05(SensorTypeService sensorTypeList) {
        System.out.print("Enter the sensor type's name: ");
        String name = InputHelperUI.getInputStringAlphabetCharOnly();
        System.out.print("Type the sensor type's unit of measurement: ");
        String unit = InputHelperUI.getInputStringAlphabetCharOnly();
        return controller.createType(sensorTypeList, name, unit);
    }

    private boolean updateModel05(SensorType sensorType, SensorTypeService sensorTypeList) {
        return controller.addTypeSensorToList(sensorType, sensorTypeList);
    }

    private void displayState05(boolean added) {
        if (added) {
            System.out.print("The sensor type has been successfully created.");
        } else {
            System.out.print("The sensor type you are trying to create already exists. Please try again.");
        }
    }

    /* USER STORY 006 - an Administrator, I want to addWithoutPersisting a new sensor and associate it to a geographical area, so that
     one can get measurements of that type in that area */
    private void runUS06(GeographicAreaList geographicAreaList, SensorTypeService sensorTypeList) {
        if (geographicAreaList.isEmpty()) {
            System.out.println(UtilsUI.INVALID_GA_LIST);
            return;
        }
        AreaSensor areaSensor = createSensor(sensorTypeList, geographicAreaList);
        if (!getConfirmation(areaSensor)) {
            return;
        }
        addSensor(areaSensor, geographicAreaList);
    }

    private AreaSensor createSensor(SensorTypeService sensorTypeList, GeographicAreaList geographicAreaList) {
        String id = getInputSensorId();
        String name = getInputSensorName();
        SensorType sensorType = getInputTypeSensor(sensorTypeList);
        Local local = getInputSensorLocal();
        Date startDate = getInputStartDate();
        GeographicArea geographicArea = InputHelperUI.getGeographicAreaByList(geographicAreaList);
        Long geoID = geographicArea.getId();
        return controller.createSensor(id, name, sensorType, local, startDate, geoID);
    }

    private String getInputSensorId() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter the sensor's ID:\t");
        return input.nextLine();
    }

    private String getInputSensorName() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter the sensor's name:\t");
        return input.nextLine();
    }

    private SensorType getInputTypeSensor(SensorTypeService sensorTypeList) {
        Scanner input = new Scanner(System.in);

        System.out.println("\nEnter the sensor type's name:\t");
        String name = InputHelperUI.getInputStringAlphabetCharOnly();

        System.out.println("\nEnter the sensor type's unit of measurement:\t");
        String unit = input.nextLine();

        return controller.createType(sensorTypeList, name, unit);
    }

    private Local getInputSensorLocal() {
        System.out.println("\nNow let's set its GPS localization\n");
        System.out.println("\nEnter the latitude:\t");
        double latitude = InputHelperUI.getInputAsDouble();

        System.out.println("\nEnter Longitude:\t");
        double longitude = InputHelperUI.getInputAsDouble();

        System.out.println("\nEnter Altitude:\t");
        double altitude = InputHelperUI.getInputAsDouble();

        return controller.createLocal(latitude, longitude, altitude);
    }

    private Date getInputStartDate() {
        System.out.println("\nEnter the sensor's starting date:\t");
        return DateUtils.getInputYearMonthDay();
    }

    private boolean getConfirmation(AreaSensor areaSensor) {
        System.out.println("You have created the following sensor:\n" + controller.buildSensorString(areaSensor));
        Scanner input = new Scanner(System.in);
        System.out.println("\n Do you wish to addWithoutPersisting this sensor to a geographic area?\n");
        System.out.println("Yes/No:\t");
        return "yes".equals(input.nextLine());
    }

    private void addSensor(AreaSensor areaSensor, GeographicAreaList geographicAreaList) {
        GeographicArea geographicArea = InputHelperUI.getGeographicAreaByList(geographicAreaList);
        if (controller.addSensorToGeographicArea(areaSensor, geographicArea)) {
            System.out.println("\nSensor has been successfully added to the geographic area.");
        } else {
            System.out.println("\nSensor wasn't added to the selected geographic area.");
        }
    }

    /* UI SPECIFIC METHODS - NOT USED ON USER STORIES */
    private void printOptionMessage() {
        System.out.println("Sensor Settings Options:\n");
        System.out.println("1) Define a new sensor type. (US05)");
        System.out.println("2) Add a new sensor and associate it to a geographical area. (US006)");
        System.out.println("3) Display already defined sensor types.");
        System.out.println("0) (Return to main menu)\n");
    }

}
