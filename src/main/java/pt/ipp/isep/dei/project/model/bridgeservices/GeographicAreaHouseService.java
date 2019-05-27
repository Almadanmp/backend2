package pt.ipp.isep.dei.project.model.bridgeservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipp.isep.dei.project.dto.DateIntervalDTO;
import pt.ipp.isep.dei.project.dto.DateValueDTO;
import pt.ipp.isep.dei.project.io.ui.utils.DateUtils;
import pt.ipp.isep.dei.project.model.Local;
import pt.ipp.isep.dei.project.model.Reading;
import pt.ipp.isep.dei.project.model.ReadingUtils;
import pt.ipp.isep.dei.project.model.geographicarea.AreaSensor;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicArea;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicAreaRepository;
import pt.ipp.isep.dei.project.model.geographicarea.SensorUtils;
import pt.ipp.isep.dei.project.model.house.House;
import pt.ipp.isep.dei.project.model.house.HouseRepository;

import java.util.*;

@Service
public class GeographicAreaHouseService implements pt.ipp.isep.dei.project.dddplaceholders.Service {

    @Autowired
    private GeographicAreaRepository geographicAreaRepository;

    @Autowired
    private HouseRepository houseRepository;


    /**
     * This method calculates the average temperature in a given date.
     *
     * @param date is used to determine the day in which we want to calculate the average.
     * @return the average temperature value for the 24 hours of the given date.
     */
    double getGeographicAreaAverageTemperature(Date date, House house) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date d1 = calendar.getTime(); // gets date at 00:00:00

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date d2 = calendar.getTime(); // gets date at 23:59:59

        // gets and returns average readings on the closest AreaSensor to the house
        Long motherAreaID = house.getMotherAreaID();
        GeographicArea houseMotherArea = geographicAreaRepository.getByID(motherAreaID);
        AreaSensor houseClosestSensor = getClosestAreaSensorOfGivenType("temperature", house, houseMotherArea);
        return getAverageReadingsBetweenFormattedDates(d1, d2, houseClosestSensor);
    }

    Double getAverageReadingsBetweenFormattedDates(Date minDate, Date maxDate, AreaSensor areaSensor) {
        List<Reading> sensorReadingsBetweenDates = getReadingListBetweenFormattedDates(minDate, maxDate, areaSensor);
        if (sensorReadingsBetweenDates.isEmpty()) {
            return Double.NaN;
        }
        return ReadingUtils.getSensorReadingAverageValue(sensorReadingsBetweenDates);
    }

    List<Reading> getReadingListBetweenFormattedDates(Date initialDate, Date finalDate, AreaSensor areaSensor) {
        List<Reading> finalList = new ArrayList<>();
        List<Reading> result = areaSensor.getReadings();
        for (Reading r : result) {
            if (ReadingUtils.isReadingDateBetweenTwoDates(r.getDate(), initialDate, finalDate)) {
                finalList.add(r);
            }
        }
        return finalList;
    }

    public List<Reading> getReadingsBelowCategoryILimit(List<Reading> readingValues, House house) {
        List<Reading> allReadings = new ArrayList<>();
        for (Reading r : readingValues) {
            double temperature = getGeographicAreaAverageTemperature(r.getDate(), house);
            if (!Double.isNaN(temperature) && categoryICalculusTemperaturesLowerThanAverage(r, temperature)) {
                allReadings.add(r);
            }
        }
        return allReadings;
    }


    public List<Reading> getReadingsBelowCategoryIILimit(List<Reading> readingValues, House house) {
        List<Reading> allReadings = new ArrayList<>();
        for (Reading r : readingValues) {
            double temperature = getGeographicAreaAverageTemperature(r.getDate(), house);
            if (!Double.isNaN(temperature) && categoryIICalculusTemperaturesLowerThanAverage(r, temperature)) {
                allReadings.add(r);
            }
        }
        return allReadings;
    }

    public List<Reading> getReadingsBelowCategoryIIILimit(List<Reading> readingValues, House house) {
        List<Reading> allReadings = new ArrayList<>();
        for (Reading r : readingValues) {
            double temperature = getGeographicAreaAverageTemperature(r.getDate(), house);
            if (!Double.isNaN(temperature) && categoryIIICalculusTemperaturesLowerThanAverage(r, temperature)) {
                allReadings.add(r);
            }
        }
        return allReadings;
    }

    /**
     * Method to check id a given reading is below the comfort temperature for category I.
     *
     * @param reading         - Reading to get value.
     * @param areaTemperature - outside average temperature for the given date
     * @return true if the reading is above the comfort level.
     */
    boolean categoryICalculusTemperaturesLowerThanAverage(Reading reading, double areaTemperature) {
        double minT = 0.33 * areaTemperature + 18.8 - 2;
        return reading.getValue() < minT;
    }

    /**
     * Method to check id a given reading is below the comfort temperature for category II.
     *
     * @param reading         - Reading to get value.
     * @param areaTemperature - outside average temperature for the given date
     * @return true if the reading is above the comfort level.
     */
    boolean categoryIICalculusTemperaturesLowerThanAverage(Reading reading, double areaTemperature) {
        double minT = 0.33 * areaTemperature + 18.8 - 3;
        return reading.getValue() < minT;
    }

    /**
     * Method to check id a given reading is below the comfort temperature for category III.
     *
     * @param reading         - Reading to get value.
     * @param areaTemperature - outside average temperature for the given date
     * @return true if the reading is above the comfort level.
     */
    boolean categoryIIICalculusTemperaturesLowerThanAverage(Reading reading, double areaTemperature) {
        double minT = 0.33 * areaTemperature + 18.8 - 4;
        return reading.getValue() < minT;
    }

    public List<Reading> getReadingsAboveCategoryILimit(List<Reading> readingValues, House house) {
        List<Reading> allReadings = new ArrayList<>();
        for (Reading r : readingValues) {
            double temperature = getGeographicAreaAverageTemperature(r.getDate(), house);
            if (!Double.isNaN(temperature) && categoryICalculusAboveAverage(r, temperature)) {
                allReadings.add(r);
            }
        }
        return allReadings;
    }

    public List<Reading> getReadingsAboveCategoryIILimit(List<Reading> readingValues, House house) {
        List<Reading> allReadings = new ArrayList<>();
        for (Reading r : readingValues) {
            double temperature = getGeographicAreaAverageTemperature(r.getDate(), house);
            if (!Double.isNaN(temperature) && categoryIICalculusAboveAverage(r, temperature)) {
                allReadings.add(r);
            }
        }
        return allReadings;
    }

    public List<Reading> getReadingsAboveCategoryIIILimit(List<Reading> readingValues, House house) {
        List<Reading> allReadings = new ArrayList<>();
        for (Reading r : readingValues) {
            double temperature = getGeographicAreaAverageTemperature(r.getDate(), house);
            if (!Double.isNaN(temperature) && categoryIIICalculusAboveAverage(r, temperature)) {
                allReadings.add(r);
            }
        }
        return allReadings;
    }

    /**
     * Method to check id a given reading is above the comfort temperature for category I.
     *
     * @param reading         - Reading to get value.
     * @param areaTemperature - outside average temperature for the given date
     * @return true if the reading is above the comfort level.
     */
    boolean categoryICalculusAboveAverage(Reading reading, double areaTemperature) {
        double minT = 0.33 * areaTemperature + 18.8 + 2;
        return reading.getValue() > minT;
    }

    /**
     * Method to check id a given reading is above the comfort temperature for category II.
     *
     * @param reading         - Reading to get value.
     * @param areaTemperature - outside average temperature for the given date
     * @return true if the reading is above the comfort level.
     */
    boolean categoryIICalculusAboveAverage(Reading reading, double areaTemperature) {
        double minT = 0.33 * areaTemperature + 18.8 + 3;
        return reading.getValue() > minT;
    }

    /**
     * Method to check id a given reading is above the comfort temperature for category III.
     *
     * @param reading         - Reading to get value.
     * @param areaTemperature - outside average temperature for the given date
     * @return true if the reading is above the comfort level.
     */
    boolean categoryIIICalculusAboveAverage(Reading reading, double areaTemperature) {
        double minT = 0.33 * areaTemperature + 18.8 + 4;
        return reading.getValue() > minT;
    }

    /**
     * This method returns the sensor closest to the house. If more than one sensor is close to it,
     * the one with the most recent reading should be used.
     *
     * @param sensorType the type of sensor to check
     * @return the closest sensor.
     */
    public AreaSensor getClosestAreaSensorOfGivenType(String sensorType, House house, GeographicArea geographicArea) {

        AreaSensor areaSensor;

        List<AreaSensor> minDistSensor = new ArrayList<>();

        List<AreaSensor> gaAreaSensors = geographicArea.getSensors();
        List<AreaSensor> sensorsOfGivenType = SensorUtils.getAreaSensorsOfGivenType(gaAreaSensors, sensorType);

        if (!sensorsOfGivenType.isEmpty()) {
            double minDist = getMinDistanceToSensorOfGivenType(sensorsOfGivenType, house);

            minDistSensor = getAreaSensorsByDistanceToHouse(sensorsOfGivenType, house, minDist);
        }
        if (minDistSensor.isEmpty()) {
            throw new NoSuchElementException("ERROR: There are no Sensors with that Sensor Type");
        }
        if (minDistSensor.size() > 1) {

            areaSensor = SensorUtils.getMostRecentlyUsedAreaSensor(minDistSensor);
        } else {
            areaSensor = minDistSensor.get(0);
        }
        return areaSensor;
    }

    private double getMinDistanceToSensorOfGivenType(List<AreaSensor> areaSensors, House house) {
        List<Double> arrayList = getSensorsDistanceToHouse(areaSensors, house);
        return Collections.min(arrayList);
    }

    /**
     * This method receives a house and the distance of the sensor closest to it,
     * goes through the sensor list and returns the sensors closest to house.
     *
     * @param house   the House of the project
     * @param minDist the distance to the sensor
     * @return AreaSensorList with sensors closest to house.
     **/
    public List<AreaSensor> getAreaSensorsByDistanceToHouse(List<AreaSensor> areaSensors, House house, double minDist) {
        List<AreaSensor> finalList = new ArrayList<>();
        for (AreaSensor s : areaSensors) {
            if (Double.compare(minDist, getDistanceToHouse(s, house)) == 0) {
                finalList.add(s);
            }
        }
        return finalList;
    }

    /**
     * Method that returns the distance between the sensor and the house.
     *
     * @param house is the house we want to calculate the distance to.
     * @return a double that represents the distance between the house and the sensor.
     */
    double getDistanceToHouse(AreaSensor areaSensor, House house) {
        Local l = house.getLocation();
        return areaSensor.getLocal().getLinearDistanceBetweenLocalsInKm(l);
    }

    /**
     * calculates distance from the house to the sensor.
     *
     * @param areaSensor sensor from where to calculate the distance
     * @return returns the distance between sensor and the house
     */
    double calculateDistanceToSensor(AreaSensor areaSensor, House house) {
        Local l = areaSensor.getLocal();
        return house.getLocation().getLinearDistanceBetweenLocalsInKm(l);
    }

    /**
     * Goes through the sensor list, calculates sensors distance to house and
     * returns values in ArrayList.
     *
     * @param house to calculate closest distance
     * @return List of sensors distance to house
     */
    private List<Double> getSensorsDistanceToHouse(List<AreaSensor> areaSensors, House house) {
        ArrayList<Double> arrayList = new ArrayList<>();
        for (AreaSensor areaSensor : areaSensors) {
            arrayList.add(calculateDistanceToSensor(areaSensor, house));
        }
        return arrayList;
    }

    /**
     * Method for US633 - Web Controller Version
     *
     * @param dateIntervalDTO date interval
     * @return string with date and amplitude value
     */
    public DateValueDTO getHighestTemperatureAmplitude(DateIntervalDTO dateIntervalDTO) {
        if (!DateUtils.isDateDTOValid(dateIntervalDTO)) {
            throw new IllegalArgumentException("ERROR: Malformed Dates: Initial and End dates are both " +
                    "required (Initial date must be before End date).");
        }
        House house = houseRepository.getHouses().get(0);
        Long geographicAreaID = house.getMotherAreaID();
        GeographicArea geographicArea = geographicAreaRepository.getByID(geographicAreaID);
        if (geographicArea == null) {
            throw new NoSuchElementException("ERROR: There is no Geographic Area with the selected ID.");
        }
        AreaSensor areaSensor = getClosestAreaSensorOfGivenType("temperature", house, geographicArea);
        Date date = areaSensor.getDateHighestAmplitudeBetweenDates(dateIntervalDTO.getInitialDate(), dateIntervalDTO.getEndDate());
        double value = areaSensor.getAmplitudeValueFromDate(date);
        return new DateValueDTO(date, value);
    }


}
