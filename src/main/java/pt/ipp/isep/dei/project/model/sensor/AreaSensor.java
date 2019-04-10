package pt.ipp.isep.dei.project.model.sensor;

import pt.ipp.isep.dei.project.model.GeographicArea;
import pt.ipp.isep.dei.project.model.House;
import pt.ipp.isep.dei.project.model.Local;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a Sensor.
 * It is defined by a name, type of sensor, localization and the date it started functioning.
 * It contains a list with one or more weather readings.
 */
@Entity
public class AreaSensor {

    @Id
    private String id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_sensor_id")
    private SensorType sensorType;

    @Embedded
    @JoinColumn(name = "local_id")
    private Local local;

    private Date dateStartedFunctioning;

    @Transient
    private ReadingService readingService;

    private Long geographicAreaId;

    private boolean active;


    /**
     * Empty constructor to import Sensors from a XML file.
     */
    public AreaSensor() {
        readingService = new ReadingService();
        this.active = true;
    }

    /**
     * Sensor() constructor with 5 parameters.
     *
     * @param id                     is the id we want to set to the Sensor.
     * @param name                   is the name we want to set to the Sensor.
     * @param sensorType             is the Type of the Sensor.
     * @param local                  is the Local of the Sensor.
     * @param dateStartedFunctioning is the Date that the Sensor Started Working.
     */
    public AreaSensor(String id, String name, SensorType sensorType, Local local, Date dateStartedFunctioning,
                      Long geographicAreaId) {
        setId(id);
        setName(name);
        this.sensorType = sensorType;
        this.local = local;
        this.dateStartedFunctioning = dateStartedFunctioning;
        readingService = new ReadingService();
        this.active = true;
        this.geographicAreaId = geographicAreaId;
    }

    /**
     * Setter: Id
     *
     * @param id is the id we want to set to the sensor.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter: name
     *
     * @param name is the name we want to set to the sensor.
     */
    public void setName(String name) {
        if (isSensorNameValid(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Please Insert Valid Name");
        }
    }

    public Date getDateStartedFunctioning() {
        return this.dateStartedFunctioning;
    }

    /**
     * Getter: Id
     *
     * @return a string that represents the name of the sensor.
     */
    public String getId() {
        return (this.id);
    }

    public void setGeographicAreaId(Long geographicAreaId) {
        this.geographicAreaId = geographicAreaId;
    }

    public Long getGeographicAreaId() {
        return this.geographicAreaId;
    }

    /**
     * Getter: name
     *
     * @return a string that represents the name of the sensor.
     */
    public String getName() {
        return (this.name);
    }

    /**
     * Getter: type sensor
     *
     * @return the Type of the Sensor.
     */
    public SensorType getSensorType() {
        return (this.sensorType);
    }

    /**
     * Getter: local
     *
     * @return the Local of the Sensor.
     */
    public Local getLocal() {
        return (this.local);
    }

    /**
     * Getter: reading list
     *
     * @return the areaReadingList of the sensor.
     */
    public ReadingService getReadingService() {
        return readingService;
    }

    /**
     * Setter: reading list
     *
     * @param readingService is the areaReadingList we want to set to the sensor.
     */
    public void setReadingService(ReadingService readingService) {
        if (readingService != null) {
            this.readingService = readingService;
        }
    }

    public boolean isActive() {
        return this.active;
    }

    /**
     * Settter: sets the sensor active
     */
    public void setActive(boolean status) {
        this.active = status;
    }

    /**
     * Method to activate an deactivated sensor, and vice versa
     *
     * @return active or not
     */
    public boolean deactivateSensor() {
        if (isActive()) {
            this.active = false;
            return true;
        } else {
            return false;
        }
    }


    /**
     * Checks if reading already exists in reading list and in case the
     * reading is new, adds it to the reading list. Only adds readings if the sensor is active.
     *
     * @param reading the reading to be added to the list
     * @return true in case the reading is new and it is added
     * or false in case the reading already exists
     **/
    public boolean addReading(Reading reading) {
        if (this.active) {
            return readingService.addReading(reading);
        }
        return false;
    }


    /**
     * Method to restrain input name so they cant be null or empty.
     *
     * @param name name inserted by user
     * @return will return true if the name is valid or it will throw an exception if Invalid
     */
    private boolean isSensorNameValid(String name) {
        return (name != null && !name.isEmpty());
    }


    /**
     * Method that checks if the Sensor is contained in a given Geographical Area.
     *
     * @param area is the area we want to check if the sensor is in.
     * @return true if the sensor is in the given area, false otherwise.
     */
    boolean isContainedInArea(GeographicArea area) {
        double latS = this.getLocal().getLatitude();
        double longS = this.getLocal().getLongitude();
        Local areaLocal = area.getLocal();
        double latTopVert = areaLocal.getLatitude() + (area.getWidth() / 2);
        double longTopVert = areaLocal.getLongitude() - (area.getLength() / 2);
        double latBotVert = areaLocal.getLatitude() - (area.getWidth() / 2);
        double longBotVert = areaLocal.getLongitude() + (area.getLength() / 2);
        return (latS <= latTopVert && latS >= latBotVert && longS <= longBotVert && longS >= longTopVert);
    }

    /**
     * Method that returns the distance between the sensor and the house.
     *
     * @param house is the house we want to calculate the distance to.
     * @return a double that represents the distance between the house and the sensor.
     */
    double getDistanceToHouse(House house) {
        Local l = house.getLocation();
        return this.local.getLinearDistanceBetweenLocalsInKm(l);
    }

    /**
     * Method to print details that are required for a Sensor to be different from another Sensor (equals -
     * name, type area and local).
     *
     * @return returns a string with Sensor Parameters
     */
    public String buildString() {

        if (this.getLocal() == null) {
            return this.name + ", " + this.sensorType.getName() + ". ";
        }
        String result;

        result = this.name + ", " + this.sensorType.getName() + ", " +
                this.local.getLatitude() + "º lat, " + this.local.getLongitude() + "º long \n";
        return result;
    }

    /**
     * Method to print info if a sensor is active or not.
     */
    String printActive() {
        if (!this.active) {
            return "Deactivated";
        }
        return "Active";
    }

    /**
     * This method goes through the sensor reading list and return
     * the most recent reading date.
     *
     * @return most recent reading date in sensor
     **/

    Date getMostRecentReadingDate() {
        return this.readingService.getMostRecentReadingDate();
    }

    /**
     * This method returns the sensor type name.
     *
     * @return he sensor type name.
     **/

    String getSensorTypeName() {
        return this.sensorType.getName();
    }

    /**
     * This method checks if the sensor's reading list is valid.
     *
     * @return true if valid, false if invalid.
     **/
    public boolean isReadingListEmpty() {
        return this.readingService.isEmpty();
    }

    /**
     * This method receives an interval, goes through the sensor's reading list and returns the
     * average reading values between the interval given.
     *
     * @param initialDate start of interval
     * @param endDate     end of interval
     * @return average reading value between interval
     * @author Daniela - US623
     ***/
    public double getAverageReadingsBetweenDates(Date initialDate, Date endDate) {
        return this.readingService.getAverageReadingsBetweenDates(initialDate, endDate);
    }

    /**
     * This method receives an interval, goes through the sensor's reading list and returns the date with the
     * highest amplitude reading value between the interval given.
     *
     * @param initialDate start of interval
     * @param endDate     end of interval
     * @return date with the highest amplitude reading value between interval
     * @author Daniela - US633
     ***/
    public Date getDateHighestAmplitudeBetweenDates(Date initialDate, Date endDate) {
        return this.readingService.getDateHighestAmplitudeBetweenDates(initialDate, endDate);
    }

    /**
     * This method receives a date, goes through the sensor's reading list and returns the highest amplitude reading
     * value on that date.
     *
     * @param date start of interval
     * @return highest amplitude reading value on date
     * @author Daniela - US633
     ***/
    public double getHighestAmplitudeInDate(Date date) {
        return this.readingService.getAmplitudeValueFromDate(date);
    }


    /**
     * US630
     * This method joins a lot of other methods used to fulfil the US 630 (As a Regular User,
     * I want to get the last coldest day (lower maximum temperature) in the house area in a given period) and
     * it returns a Reading within an interval from a AreaReadingList that represents the last coldest day in the
     * given period (lower maximum temperature).
     *
     * @param initialDate is the Initial Date of the period.
     * @param endDate     is the Final Date of the period.
     * @return a Reading that represents the Last Coldest Day in a Given Period (Lower Maximum Temperature).
     */
    public Date getLastColdestDayInGivenInterval(Date initialDate, Date endDate) {
        return this.readingService.getLastColdestDayInGivenInterval(initialDate, endDate);
    }

    /**
     * US631
     * This method returns a DATE for the first hottest day (higher maximum temperature) in the house area in a given period
     * (higher maximum temperature).
     *
     * @param startDate is the Initial Date of the period.
     * @param endDate   is the Final Date of the period.
     * @return a Reading that represents the Last Coldest Day in a Given Period (Lower Maximum Temperature).
     */

    public Date getFirstHottestDayInGivenPeriod(Date startDate, Date endDate) {
        return this.readingService.getFirstHottestDayInGivenPeriod(startDate, endDate);
    }

    /**
     * This method receives a date of a given day, goes through the sensor's reading list and
     * returns the total reading values of that day.
     *
     * @param day date of day
     * @return total reading values of that day
     ***/
    public double getTotalValueReadingsOnGivenDay(Date day) {
        return this.readingService.getValueReadingsInDay(day);
    }

    /**
     * This method goes through the sensor's reading list and
     * returns the most recent reading value.
     *
     * @return sensor's most recent reading value.
     ***/
    public double getMostRecentValueReading() {
        return this.readingService.getMostRecentValue();
    }


    @Override
    public boolean equals(Object testObject) {
        if (this == testObject) {
            return true;
        }
        if (!(testObject instanceof AreaSensor)) {
            return false;
        }
        AreaSensor areaSensor = (AreaSensor) testObject;
        return this.getName().equals(areaSensor.getName());
    }

    @Override
    public int hashCode() {
        return 1;
    }
}