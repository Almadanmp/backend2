package pt.ipp.isep.dei.project.dto;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class RoomSensorDTO extends ResourceSupport {

    private String sensorId;
    private String name;
    private String typeSensor;
    private String units;
    private String dateStartedFunctioning;
    private String roomID;
    private boolean active;
    private List<ReadingDTO> readingList;

    public RoomSensorDTO() {
        readingList = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String id) {
        this.sensorId = id;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return typeSensor;
    }

    /**
     * Method that stores a String as the DTO's type.
     *
     * @param typeSensor is the string we want to store.
     */
    public void setTypeSensor(String typeSensor) {
        this.typeSensor = typeSensor;
    }


    /**
     * Method that retrieves the date at which the sensorDTO started functioning, as a string.
     *
     * @return the date at which the sensor started functioning, as a string.
     */

    public String getDateStartedFunctioning() {
        return dateStartedFunctioning;
    }

    /**
     * Method that stores a string as the date at which the DTO started functioning.
     *
     * @param dateStartedFunctioning is the date that we want to store.
     */

    public void setDateStartedFunctioning(String dateStartedFunctioning) {
        this.dateStartedFunctioning = dateStartedFunctioning;
    }

    public List<ReadingDTO> getReadingList() {
        return new ArrayList<>(readingList);
    }

    public void setReadingList(List<ReadingDTO> readingList) {
        this.readingList = new ArrayList<>(readingList);
    }

    @Override
    public boolean equals(Object testDTO) {
        if (this == testDTO) {
            return true;
        }
        if (!(testDTO instanceof RoomSensorDTO)) {
            return false;
        }
        RoomSensorDTO localVariable = (RoomSensorDTO) testDTO;
        return (localVariable.getName().equals(this.name));
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
