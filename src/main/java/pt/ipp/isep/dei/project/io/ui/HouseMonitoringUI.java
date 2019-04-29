package pt.ipp.isep.dei.project.io.ui;

import pt.ipp.isep.dei.project.controller.HouseMonitoringController;
import pt.ipp.isep.dei.project.dto.RoomDTO;
import pt.ipp.isep.dei.project.io.ui.utils.DateUtils;
import pt.ipp.isep.dei.project.io.ui.utils.InputHelperUI;
import pt.ipp.isep.dei.project.io.ui.utils.MenuFormatter;
import pt.ipp.isep.dei.project.io.ui.utils.UtilsUI;
import pt.ipp.isep.dei.project.model.geographicarea.AreaSensor;
import pt.ipp.isep.dei.project.model.house.House;
import pt.ipp.isep.dei.project.model.room.Room;
import pt.ipp.isep.dei.project.model.room.RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;


public class HouseMonitoringUI {
    private final HouseMonitoringController houseMonitoringController;
    private final String was = " was ";
    private static final String RAINFALL = "rainfall";
    private static final String TEMPERATURE = "temperature";
    private final List<String> menuOptions;

    public HouseMonitoringUI() {
        this.houseMonitoringController = new HouseMonitoringController();
        menuOptions = new ArrayList<>();
        menuOptions.add("Get Current Temperature in a House Area. (US600)");
        menuOptions.add("Get The Total Rainfall on a specific day in a House Area. (US620)");
        menuOptions.add("Get The Average Rainfall on a day interval in a House Area. (US623)");
        menuOptions.add("Get the Last Coldest Day (lower maximum temperature) in the House" +
                "Area in a given period. (US630)");
        menuOptions.add("Get the First Hottest Day (higher maximum temperature) in the House" +
                "Area in a given period. (US631)");
        menuOptions.add("Get the day with the highest temperature amplitude in the House Area in a given period." +
                "(US633)");
        menuOptions.add("(Return to main menu)");
    }

    void run(House house, RoomService roomService) {
        boolean activeInput = false;
        int option;
        System.out.println("--------------\n");
        System.out.println("House Monitoring\n");
        System.out.println("--------------\n");
        while (!activeInput) {
            MenuFormatter.showMenu("House Monitoring", menuOptions);
            option = InputHelperUI.getInputAsInt();
            switch (option) {
                case 1:
                    runUS600(house);
                    activeInput = true;
                    break;
                case 2:
                    runUS620(house);
                    activeInput = true;
                    break;
                case 3:
                    runUS623(house);
                    activeInput = true;
                    break;
                case 4:
                    runUS630(house);
                    activeInput = true;
                    break;
                case 5:
                    runUS631(house);
                    activeInput = true;
                    break;
                case 6:
                    runUS633(house);
                    activeInput = true;
                    break;
                case 0:
                    return;
                default:
                    System.out.println(UtilsUI.INVALID_OPTION);
                    break;
            }
        }
    }

    /**
     * US600
     * As a Regular User, I want to get the current temperature in the house area. If, in the
     * first element with temperature sensors of the hierarchy of geographical areas that
     * includes the house, there is more than one temperature sensor, the nearest one
     * should be used.
     */
    private void runUS600(House house) {
        if (!houseMonitoringController.isMotherAreaValid(house)) {
            return;
        }
        updateModel600(house);
    }

    private void updateModel600(House house) {
        AreaSensor closestSensorToHouse;
        try {
            closestSensorToHouse = houseMonitoringController.getClosesSensorByTypeToHouse(house, TEMPERATURE);
            double currentTemp = houseMonitoringController.getHouseAreaTemperature(closestSensorToHouse);
            System.out.println("The current temperature in the house area is: " + currentTemp + "°C.");
        } catch (IllegalArgumentException illegal) {
            System.out.println(illegal.getMessage());
        }

    }

    /**
     * US620UI: As a Regular User, I want to get the total rainfall in the house area for a given day.
     */
    private void runUS620(House house) {
        if (!houseMonitoringController.isMotherAreaValid(house)) {
            return;
        }
        System.out.println("Please enter the desired date.");
        Date date = DateUtils.getInputYearMonthDay();
        updateAndDisplayModelUS620(house, date);
    }

    private void updateAndDisplayModelUS620(House house, Date date) {
        double result;
        AreaSensor areaSensor;
        try {
            areaSensor = houseMonitoringController.getClosesSensorByTypeToHouse(house, RAINFALL);
            result = houseMonitoringController.getTotalRainfallOnGivenDay(date, areaSensor);
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        printResultMessageUS620(date, result);
    }

    private void printResultMessageUS620(Date date, double result) {
        String dateFormatted = DateUtils.formatDateNoTime(date);
        System.out.println("The average rainfall on " + dateFormatted + was + result + "%.");
    }


     /* US623: As a Regular User, I want to get the average daily rainfall in the house area for a
      given period (days), as it is needed to assess the garden’s watering needs.*/

    private void runUS623(House house) {
        if (!houseMonitoringController.isMotherAreaValid(house)) {
            return;
        }
        System.out.println("Please enter the start date.");
        Date startDate = DateUtils.getInputYearMonthDay();
        Date endDate = DateUtils.getInputYearMonthDay();
        System.out.println("Please enter the end date.");
        updateAndDisplayUS623(house, startDate, endDate);
    }

    /**
     * method to get the start date
     *
     * @return date
     */
    private Date getStartDate() {
        System.out.println("Please enter the start date.");
        return DateUtils.getInputYearMonthDay();
    }

    /**
     * Method to get the end date
     *
     * @return date
     */
    private Date getEndDate() {
        System.out.println("Please enter the end date.");
        return DateUtils.getInputYearMonthDay();
    }

    private void updateAndDisplayUS623(House house, Date startDate, Date endDate) {
        double result623;
        AreaSensor closestAreaSensor;
        try {
            closestAreaSensor = houseMonitoringController.getClosesSensorByTypeToHouse(house, RAINFALL);
            result623 = houseMonitoringController.getAverageRainfallInterval(closestAreaSensor, startDate, endDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        String starDateFormatted = DateUtils.formatDateNoTime(startDate);
        String endDateFormatted = DateUtils.formatDateNoTime(endDate);
        System.out.println("The average rainfall between " + starDateFormatted + " and " + endDateFormatted + was
                + result623 + "%.");
    }

    /**
     * US630 : As a Regular User, I want to get the last coldest day (lower maximum temperature)
     * in the house area in a given period.
     */

    private void runUS630(House house) {
        if (!houseMonitoringController.isMotherAreaValid(house)) {
            return;
        }
        Date startDate = getStartDate();
        Date endDate = getEndDate();
        updateAndDisplayUS630(house, startDate, endDate);
    }

    private void updateAndDisplayUS630(House house, Date startDate, Date endDate) {
        Date dateResult630;
        AreaSensor closestSensorToHouse;
        Double temperatureValue;
        try {
            closestSensorToHouse = houseMonitoringController.getClosesSensorByTypeToHouse(house, TEMPERATURE);
            dateResult630 = houseMonitoringController.getLastColdestDayInInterval(closestSensorToHouse, startDate, endDate);
            temperatureValue = houseMonitoringController.getReadingValueOnGivenDay(closestSensorToHouse, dateResult630);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        String dateResultFormatted = DateUtils.formatDateNoTime(dateResult630);
        String dateStartDateFormatted = DateUtils.formatDateNoTime(startDate);
        String dateEndDateFormatted = DateUtils.formatDateNoTime(endDate);
        System.out.println("The last coldest day between " + dateStartDateFormatted + " and " + dateEndDateFormatted + was
                + dateResultFormatted + ". The temperature on that day was " + temperatureValue);
    }

    /**
     * US631 : As a Regular User, I want to get the first hottest day (higher maximum temperature)
     * in the house area in a given period.
     */

    private void runUS631(House house) {
        if (!houseMonitoringController.isMotherAreaValid(house)) {
            return;
        }
        Date startDate = getStartDate();
        Date endDate = getEndDate();
        updateAndDisplayUS631(house, startDate, endDate);
    }

    private void updateAndDisplayUS631(House house, Date startDate, Date endDate) {
        Date dateUS631;
        AreaSensor closestSensorToHouse;
        Double temperatureValue;
        try {
            closestSensorToHouse = houseMonitoringController.getClosesSensorByTypeToHouse(house, TEMPERATURE);
            dateUS631 = houseMonitoringController.getFirstHottestDayInPeriod(closestSensorToHouse, startDate, endDate);
            temperatureValue = houseMonitoringController.getReadingValueOnGivenDay(closestSensorToHouse, dateUS631);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        String formattedUS631Date = DateUtils.formatDateNoTime(dateUS631);
        UtilsUI.printBox("The first day with the hottest temperature in the given", "period was " + formattedUS631Date
                + ". The temperature on that day was " + temperatureValue);
    }

    /* US633:  As Regular User, I want to get the day with the highest temperature amplitude in the house area in a
    given period. */
    private void runUS633(House house) {
        if (!houseMonitoringController.isMotherAreaValid(house)) {
            return;
        }
        Date startDate = getStartDate();
        Date endDate = getEndDate();
        updateAndDisplayUS633(house, startDate, endDate);
    }

    private void updateAndDisplayUS633(House house, Date startDate, Date endDate) {
        Date resultDate633;
        double resultValue633;
        AreaSensor closestSensorToHouse;

        try {
            closestSensorToHouse = houseMonitoringController.getClosesSensorByTypeToHouse(house, TEMPERATURE);
            resultDate633 = houseMonitoringController.getHighestTempAmplitudeDate(closestSensorToHouse, startDate, endDate);
            resultValue633 = houseMonitoringController.getTempAmplitudeValueByDate(closestSensorToHouse, resultDate633);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        String dateResultFormatted = DateUtils.formatDateNoTime(resultDate633);
        System.out.println("The day with the highest temperature amplitude was " + dateResultFormatted + ", with a" +
                " temperature amplitude of " + resultValue633 + "ºC.");
    }
}
