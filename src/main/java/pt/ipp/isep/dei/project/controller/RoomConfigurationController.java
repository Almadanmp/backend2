package pt.ipp.isep.dei.project.controller;

import pt.ipp.isep.dei.project.model.*;
import pt.ipp.isep.dei.project.model.devicetypes.DeviceType;
import pt.ipp.isep.dei.project.model.devicetypes.Fridge;
import pt.ipp.isep.dei.project.model.devicetypes.WashingMachine;
import pt.ipp.isep.dei.project.model.devicetypes.WaterHeater;

import java.util.List;

public class RoomConfigurationController {

    private Room mRoom;
    private Sensor mSensor;
    private Device mDevice;

    /**
     * Empty constructor.
     */

    public RoomConfigurationController() {
        /*
         * Builder RoomConfigurationController() with no parameters.
         * It will only be used in ui to apply methods on given inputs.
         */
    }

    //  SHARED METHODS
    /* USER STORY 210 - As an Administrator, I want to add a new device to a room from the list of available
    device types, so that I can configure it. - MARIA MEIRELES */

    public void createDevice(String deviceName, double nominalPower, DeviceSpecs deviceSpecs) {
        this.mDevice = new Device(deviceName, nominalPower, deviceSpecs);
    }


    /**
     *
     * @param roomName is the name of the room we want to look for.
     * @param house    is the house where we want to look for the room.
     * @return is the room in the House with the name that matches given string. Is null if no Room matches.
     */

    Room getRoomFromHouseByName(String roomName, House house) {
        for (Room r : house.getRoomList()) {
            if (r.getRoomName().equals(roomName)) {
                mRoom = r;
            }
        }
        return mRoom;
    }

    /**
     *
     * @param house is the house we want to print the roomList from.
     * @return builds a string from the House's room list.
     */

    public String printRoomList(House house) {
        return house.printRoomList();
    }

    /**
     *
     * @param room is the room we want to print.
     * @return builds a string from given room.
     */

    public String printRoom(Room room) {
        return room.printRoom();
    }

    /**
     *
     * @param listOfIndexesOfRoom is a list of integers that represent positions in a list.
     * @param house is the house where we want to get lists from.
     * @return builds a string from the individual elements in the House's RoomList that are contained in the positions
     * given by the list of indexes.
     */

    public String printRoomElementsByIndex(List<Integer> listOfIndexesOfRoom, House house) {
        return house.printRoomsByIndex(listOfIndexesOfRoom);
    }

    /**
     *
     * @param listOfIndexesOfDevice is a list of integers that represent positions in a list.
     * @param room is the room where we want to get the device list from.
     * @return builds a string from the individual elements in the DeviceList that are contained in the positions
     * given by the list of indexes.
     */

    public String printDeviceElementsByIndex(List<Integer> listOfIndexesOfDevice, Room room) {
        return room.getObjectDeviceList().printElementsByIndex(listOfIndexesOfDevice);
    }

    /**
     *
     * @param input is the name of room we want to look for.
     * @param house is the house where we want to look for rooms.
     * @return is a list of integers representing positions in the house's roomList of rooms whose name matches
     * input string.
     */

    public List<Integer> matchRoomIndexByString(String input, House house) {
        return house.matchRoomIndexByString(input);
    }


    /*USER STORY 230 - As a Room Owner [or Power User, or Administrator], I want to know the total
    nominal power of a room, i.e. the sum of the nominal power of all devices in the
    room. - TERESA VARELA */

    /** This method receives a room and returns that room's total nominal power as a double
     * @param room is the room to be tested
     * @return room's total nominal power (double)
     */
    public double getRoomNominalPower(Room room) {
        return room.getNominalPower();
    }


    /* USER STORY 253 - As an Administrator, I want to add a new sensor to a room from the list of available
    sensor types, in order to configure it. - ANDRÉ RUA */

    /**
     * @param name is the name of the sensor we want to look for.
     * @param ga   is the ga where we want to see if the sensor exists.
     * @return is true if the geoArea contains a sensor with given name, false if it doesn't.
     */

    boolean checkIfGAContainsSensorByString(String name, GeographicArea ga) {
        for (Sensor s : ga.getSensorList().getSensorList()) {
            if (s.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param sensorName is the name of the sensor we want to look for
     * @param ga is the ga we want to look for the sensor in.
     * @return is the sensor in the Geo Area with the name that matches given string. Is null if no sensor matches.
     */

    Sensor getSensorFromGAByName(String sensorName, GeographicArea ga) {
        for (Sensor s : ga.getSensorList().getSensorList()) {
            if (s.getName().equals(sensorName))
                mSensor = s;
        }
        return mSensor;
    }

    /**
     *
     * @param input is the name of sensor we want to look for.
     * @param slist is the sensorlist where we want to look for sensors.
     * @return is a list of integers representing positions in the sensorlist of sensors whose name matches
     * input string.
     */

    public List<Integer> matchSensorIndexByString(String input, SensorList slist) {
        return slist.matchSensorListIndexByString(input);
    }

    public List<Integer> matchDeviceIndexByString(String input, Room room ){
        return room.getObjectDeviceList().matchDeviceIndexByString(input);
    }
    /**
     *
     * @param sensorList is the sensor list to print.
     * @return builds a string from given sensor list.
     */

    public String printSensorList(SensorList sensorList) {
        return sensorList.printSensorWholeList(sensorList);
    }

    /**
     *
     * @param sensor is sensor we want to print.
     * @return builds a string from given sensor.
     */

    public String printSensor(Sensor sensor) {
        return sensor.printSensor();
    }

    /**
     *
     * @param device the device we want to print.
     * @return string with the given device.
     */
    public String printDevice(Device device) {
        return device.printDevice();
    }

    /**
     *
     * @param room the room we want to print the list of devices from.
     * @return string with all the devices in the given room.
     */
    public String printDeviceList(Room room){
        return room.printListOfDevicesFromRoom(room);
    }


    public String printDeviceType(DeviceType deviceType){return deviceType.printDeviceType(deviceType);}

    /**
     *
     * @param
     * @return
     */
    public String printDeviceTypeList(List<DeviceType> deviceTypeList){
        DeviceType deviceType= DeviceType.WATER_HEATER;
        for(int i=0;i<deviceTypeList.size();i++){
            deviceType = deviceTypeList.get(i);
        }

        return deviceType.printDeviceTypeByIndex();
    }



    public void setVolumeWater(double input, Device device){
        if(device.getDeviceType() == DeviceType.WATER_HEATER){
            WaterHeater waterHeater = new WaterHeater();
            waterHeater.setVolumeOfWater(input);
        }
    }

    public void setHotWaterTemp(double input, Device device){
        if(device.getDeviceType() == DeviceType.WATER_HEATER){
            WaterHeater waterHeater = new WaterHeater();
            waterHeater.setHotWaterTemperature(input);
        }
    }

    public void setColdWaterTemp(double input, Device device){
        if(device.getDeviceType() == DeviceType.WATER_HEATER){
            WaterHeater waterHeater = new WaterHeater();
            waterHeater.setColdWaterTemperature(input);
        }
    }

    public void setPerformanceRatio(double input, Device device){
        if(device.getDeviceType() == DeviceType.WATER_HEATER){
            WaterHeater waterHeater = new WaterHeater();
            waterHeater.setPerformanceRatio(input);
        }
    }


    public void setCapacity(double input, Device device){
        if(device.getDeviceType() == DeviceType.WASHING_MACHINE){
            WashingMachine waterHeater = new WashingMachine();
            waterHeater.setmCapacity(input);
        }
    }
    public void setFreezerCapacity(double input, Device device){
        if(device.getDeviceType() == DeviceType.FRIDGE){
            Fridge fridge = new Fridge();
            fridge.setFreezerCapacity(input);
        }
    }

    public void setRefrigeratorCapacity(double input, Device device){
        if(device.getDeviceType() == DeviceType.FRIDGE){
            Fridge fridge = new Fridge();
            fridge.setmRefrigeratorCapacity(input);
        }
    }

    /**
     *
     * @param input the new name we want to give to the device.
     * @param device the device we want to change the name from.
     */
    public void setDeviceName(String input, Device device) {
        device.setmName(input);
    }


    /**
     *
     * @param input the new nominal power we want to give to the device.
     * @param device the device we want to change the nominal power from.
     */
    public void setNominalPower(Double input, Device device){
        device.setNominalPower(input);
    }

    /**
     *
     * @param listOfIndexesOfSensor is a list of integers that represent positions in a list.
     * @param sensorList is the sensorList where we want to get sensors from.
     * @return builds a string from the individual elements in the SensorList that are contained in the positions
     * given by the list of indexes.
     */

    public String printSensorElementsByIndex(List<Integer> listOfIndexesOfSensor, SensorList sensorList) {
        return sensorList.printElementsByIndex(listOfIndexesOfSensor);
    }
    public String printTypeList(List<TypeSensor> typeList) {
        StringBuilder result = new StringBuilder(new StringBuilder("---------------\n"));
        if (typeList.isEmpty()) {
            return "Invalid List - List is Empty\n";
        }
        for (int i = 0; i < typeList.size(); i++) {
            TypeSensor aux = typeList.get(i);
            result.append(i).append(") Name: ").append(aux.getName()).append(" | ");
            result.append("Units: ").append(aux.getUnits()).append("\n");
        }
        result.append("---------------\n");
        return result.toString();
    }
    public boolean addSensorToRoom(Room room,Sensor sensor) {
        return (room.addSensor(sensor));
    }
}
