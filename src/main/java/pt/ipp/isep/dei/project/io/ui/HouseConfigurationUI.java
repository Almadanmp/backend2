package pt.ipp.isep.dei.project.io.ui;

import pt.ipp.isep.dei.project.controller.HouseConfigurationController;
import pt.ipp.isep.dei.project.model.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

class HouseConfigurationUI {
    private HouseConfigurationController controller;
    private String mTypeAreaName;
    private TypeArea mTypeGA;

    /** ------ SHARED METHODS -------- **/

    /** ------ OPÇÃO LISTAR POR NOMES/POR LISTA - TYPE AREA -------- **/

    private void getInputTypeArea(TypeAreaList typeAreaList) {
        this.controller = new HouseConfigurationController(typeAreaList);
        System.out.println(
                "We need to know what is the type of Geographic Area you want.\n" + "Would you like to:\n" + "1)Type the Geographic Area Type name;\n" + "2) Choose it from a list;\n" +
                        "0) Return;");
        boolean activeInput = false;
        while (!activeInput) {
            int option = readInputNumberAsInt();
            switch (option) {
                case 1:
                    getInputTypeAreaName();
                    if (!getTypeAreaByName(typeAreaList)) {
                        System.out.println("Unable to select a Geographic Area. Returning to main menu.");
                        return;
                    }
                    activeInput = true;
                    break;
                case 2:
                    getInputTypeAreaByList(typeAreaList);
                    activeInput = true;
                    break;
                case 0:
                    return;
                default:
                    System.out.println(INVALID_OPTION);
                    break;
            }
        }
    }

    private boolean getInputTypeAreaName() {
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Please type the name of the Geographic Area Type Where: ");
        this.mTypeAreaName = mScanner.nextLine();
        return (!(this.mTypeAreaName.equals("exit")));
    }

    private boolean getTypeAreaByName(TypeAreaList typeAreaList) {
        List<Integer> listOfIndexesTypeArea = this.controller.matchTypeAreaIndexByString(this.mTypeAreaName, typeAreaList);

        while (listOfIndexesTypeArea.isEmpty()) {
            System.out.println("There is no Geographic Area Type with that name. Please insert the name of a Geographic Area Type" +
                    " that exists or type 'exit' to cancel and create a new Geographic Area Type on the Main Menu.");
            if (!getInputTypeAreaName()) {
                return false;
            }
            listOfIndexesTypeArea = this.controller.matchTypeAreaIndexByString(this.mTypeAreaName, typeAreaList);
        }

        if (listOfIndexesTypeArea.size() > 1) {
            System.out.println("There are multiple Geographic Area Types with that name. Please choose the right one.");
            System.out.println(this.controller.printTypeAreaElementsByIndex(listOfIndexesTypeArea, typeAreaList));
            int aux = readInputNumberAsInt();
            if (listOfIndexesTypeArea.contains(aux)) {
                this.mTypeGA = typeAreaList.getTypeAreaList().get(aux);
                System.out.println("You have chosen the following Geographic Area Type:");
                System.out.println(this.controller.printTypeArea(this.mTypeGA));
            } else {
                System.out.println(INVALID_OPTION);
            }
        } else {
            System.out.println("You have chosen the following Geographic Area Type:");
            this.mTypeGA = typeAreaList.getTypeAreaList().get(listOfIndexesTypeArea.get(0));
            System.out.println(this.controller.printTypeArea(this.mTypeGA));
        }
        return true;
    }


    private void getInputTypeAreaByList(TypeAreaList typeAreaList) {
        boolean activeInput = false;
        System.out.println("Please select the Geographic Area Type from the list: ");

        while (!activeInput) {
            this.controller.printGATypeList(typeAreaList);
            int aux = readInputNumberAsInt();
            if (aux >= 0 && aux < typeAreaList.getTypeAreaList().size()) {
                this.mTypeGA = typeAreaList.getTypeAreaList().get(aux);
                activeInput = true;
                //TODO fazer um print bonito
                System.out.println("You have chosen the following Geographic Area Type:");
                System.out.println(this.controller.printTypeArea(this.mTypeGA));
            } else {
                System.out.println(INVALID_OPTION);
            }
        }
    }


    /** ------ OPÇÃO LISTAR POR NOMES/POR LISTA -  GEOGRAPHIC AREAS -------- **/

    private void getInputGeographicArea(GeographicAreaList newGeoListUi) {
        System.out.println(
                "We need to know what Geographic Area you want to work with. This can be the Geographic Area your house is in, or any Geographic Area you want to change.\n" + "Would you like to:\n" + "1) Type the Geographic Area name;\n" + "2) Choose it from a list;\n" +
                        "0) Return;");
        boolean activeInput = false;
        while (!activeInput) {
            int option = readInputNumberAsInt();
            switch (option) {
                case 1:
                    getInputGeographicAreaName();
                    if (!getGeographicAreaByName(newGeoListUi)) {
                        System.out.println("Unable to select a Geographic Area. Returning to main menu.");
                        return;
                    }
                    activeInput = true;
                    break;
                case 2:
                    getInputGeographicAreaByList(newGeoListUi);
                    activeInput = true;
                    break;
                case 0:
                    return;
                default:
                    System.out.println(INVALID_OPTION);
                    break;
            }
        }
    }

    private boolean getGeographicAreaByName(GeographicAreaList newGeoListUi) {
        HouseConfigurationController ctrl = new HouseConfigurationController(newGeoListUi);
        List<Integer> listOfIndexesGeographicAreas = ctrl.matchGeographicAreaIndexByString(geoName, newGeoListUi);

        while (listOfIndexesGeographicAreas.isEmpty()) {
            System.out.println("There is no Geographic Area with that name. Please insert the name of a Geographic Area" +
                    " that exists or  Type 'exit' to cancel and create a new Geographic Area on the Main Menu.");
            if (!getInputGeographicAreaNameUS()) {
                return false;
            }
            listOfIndexesGeographicAreas = ctrl.matchGeographicAreaIndexByString(geoName, newGeoListUi);
        }

        if (listOfIndexesGeographicAreas.size() > 1) {
            System.out.println("There are multiple Geographic Areas with that name. Please choose the right one.");
            System.out.println(ctrl.printGeoGraphicAreaElementsByIndex(listOfIndexesGeographicAreas, newGeoListUi));
            int aux = readInputNumberAsInt();
            if (listOfIndexesGeographicAreas.contains(aux)) {
                mGeoArea = newGeoListUi.getGeographicAreaList().get(aux);
                System.out.println("You have chosen the following Geographic Area:");
                System.out.println(ctrl.printGA(mGeoArea));
            } else {
                System.out.println(INVALID_OPTION);
            }
        } else {
            System.out.println("You have chosen the following Geographic Area:");
            mGeoArea = newGeoListUi.getGeographicAreaList().get(listOfIndexesGeographicAreas.get(0));
            System.out.println(ctrl.printGA(mGeoArea));
        }
        return true;
    }

    private boolean getInputGeographicAreaNameUS() {
        System.out.println("Please type the name of the Geographic Area Where Your House Is Located.");
        Scanner scanner = new Scanner(System.in);
        this.geoName = scanner.nextLine();
        return (!("exit".equals(geoName)));
    }


    private void getInputGeographicAreaByList(GeographicAreaList newGeoListUi) {
        boolean activeInput = false;
        System.out.println("Please select the Geographic Area in which your House is in from the list: ");
        while (!activeInput) {
            HouseConfigurationController controller = new HouseConfigurationController();
            controller.printGAList(newGeoListUi);
            int aux = readInputNumberAsInt();
            if (aux >= 0 && aux < newGeoListUi.getGeographicAreaList().size()) {
                mGeoArea = newGeoListUi.getGeographicAreaList().get(aux);
                activeInput = true;
                //TODO fazer um print bonito
                System.out.println("You have chosen the following Geographic Area:");
                System.out.println((mGeoArea.printGeographicArea()));
            } else {
                System.out.println(INVALID_OPTION);
            }
        }
    }

    /** ------ OPÇÃO LISTAR POR NOMES/POR LISTA - HOUSES -------- **/

    private void getInputHouse(GeographicArea mGeoArea) {
        System.out.println(
                "We need to know which one is your house.\n" + "Would you like to:\n" + "1) Type the name of your House;\n" + "2) Choose it from a list;\n" +
                        "0) Return;");
        int option = readInputNumberAsInt();
        switch (option) {
            case 1:
                getInputHouseName();
                if (!getHouseByName(mGeoArea)) {
                    System.out.println("Unable to select a House. Returning to main menu.");
                    return;
                }
                break;
            case 2:
                getInputHouseByList();
                break;
            case 0:
                return;
            default:
                System.out.println(INVALID_OPTION);
                break;
        }
    }

    private boolean getInputHouseName() {
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Please type the name of the House you want to access.");
        this.mHouseName = mScanner.nextLine();
        return (!(mHouseName.equals("exit")));
    }

    private boolean getHouseByName(GeographicArea mGeoArea) {
        HouseConfigurationController controller = new HouseConfigurationController();
        List<Integer> listOfIndexesHouses = controller.matchHouseIndexByString(mHouseName, mGeoArea);

        while (listOfIndexesHouses.isEmpty()) {
            System.out.println("There is no House Area with that name. Please insert the name of a House" +
                    " that exists or  Type 'exit' to cancel and create a new House on the Main Menu.");
            if (!getInputHouseName()) {
                return false;
            }
            listOfIndexesHouses = controller.matchHouseIndexByString(mHouseName, mGeoArea);
        }
        if (listOfIndexesHouses.size() > 1) {
            System.out.println("There are multiple Houses with that name. Please choose the right one.");
            System.out.println(controller.printHouseElementsByIndex(listOfIndexesHouses, mGeoArea));
            int aux = readInputNumberAsInt();
            if (listOfIndexesHouses.contains(aux)) {
                this.mHouse = mGeoArea.getHouseList().getHouseList().get(aux);
                System.out.println("You have chosen the following House:");
              System.out.println(controller.printHouse(mHouse));
            } else {
                System.out.println(INVALID_OPTION);
            }
        } else {
            System.out.println("You have chosen the following House:");
            this.mHouse = mGeoArea.getHouseList().getHouseList().get(0);
            System.out.println(controller.printHouse(mHouse));
        }
        return true;
    }


    private void getInputHouseByList() {
        if (mGeoArea.getHouseList().getHouseList().isEmpty()) {
            System.out.print("Invalid House List - List Is Empty\n");
            return;
        }
        boolean activeInput = false;
        System.out.println("Please select one of the existing houses on the selected geographic area: ");
        while (!activeInput) {
            HouseConfigurationController controller = new HouseConfigurationController();
            controller.printHouseList(this.mGeoArea);
            int aux = readInputNumberAsInt();
            if (aux >= 0 && aux < mGeoArea.getHouseList().getHouseList().size()) {
                mHouse = mGeoArea.getHouseList().getHouseList().get(aux);
                activeInput = true;
            } else {
                System.out.println(INVALID_OPTION);
            }
        }
    }

    private int readInputNumberAsInt() {
        Scanner mScanner = new Scanner(System.in);
        while (!mScanner.hasNextDouble()) {
            System.out.println(INVALID_OPTION);
            mScanner.next();
        }
        Double option = mScanner.nextDouble();
        return option.intValue();
    }

    /** ------ OPÇÃO LISTAR POR NOMES/POR LISTA - ENERGY GRIDS -------- **/

    private void getInputEnergyGrid(){
        System.out.println(
                "We need to know which one is your energy grid.\n" + "Would you like to:\n" + "1) Type the name of your grid;\n" + "2) Choose it from a list;\n" +
                        "0) Return;");
        int option = readInputNumberAsInt();
        switch (option) {
            case 1:
                getInputGridName();
                if (!getGridByName()) {
                    System.out.println("Unable to select a Grid. Returning to main menu.");
                    return;
                }
                break;
            case 2:
                getInputGridByList();
                break;
            case 0:
                return;
            default:
                System.out.println(INVALID_OPTION);
                break;
        }
    }

    private boolean getGridByName(){
        HouseConfigurationController controller = new HouseConfigurationController();
        List<Integer> listOfIndexesGrids = controller.matchGridIndexByString(mGridName, mHouse);
        while (listOfIndexesGrids.isEmpty()) {
            System.out.println("There is no EnergyGrid with that name. Please insert the name of a Grid" +
                    " that exists or  Type 'exit' to cancel and create a new Grid on the Main Menu.");
            if (!getInputGridName()) {
                return false;
            }
            listOfIndexesGrids = controller.matchGridIndexByString(mGridName, mHouse);
        }
        if (listOfIndexesGrids.size() > 1) {
            System.out.println("There are multiple Energy Grids with that name. Please choose the right one.");
            System.out.println(controller.printEnergyGridByIndex(listOfIndexesGrids));
            int aux = readInputNumberAsInt();
            if (listOfIndexesGrids.contains(aux)) {
                this.mEnergyGrid = mHouse.getmEGList().getEnergyGridList().get(aux);
                System.out.println("You have chosen the following grid:");
                System.out.println(controller.printEnergyGrid(mEnergyGrid));
            } else {
                System.out.println(INVALID_OPTION);
            }
        } else {
            System.out.println("You have chosen the following grid:");
            this.mEnergyGrid = mHouse.getmEGList().getEnergyGridList().get(0);
            System.out.println(controller.printEnergyGrid(mEnergyGrid));
        }
        return true;
    }

    private boolean getInputGridName() {
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Please type the name of the Grid you want to access.");
        this.mGridName = mScanner.nextLine();
        return (!(mGridName.equals("exit")));
    }

    private void getInputGridByList(){
        if (mHouse.getmEGList().getEnergyGridList().isEmpty()) {
            System.out.print("Invalid Grid List - List Is Empty\n");
            return;
        }
        boolean activeInput = false;
        System.out.println("Please select one of the existing grids on the selected house: ");
        while (!activeInput) {
            HouseConfigurationController controller = new HouseConfigurationController();
            System.out.println(controller.printGridList(this.mHouse));
            int aux = readInputNumberAsInt();
            if (aux >= 0 && aux < mHouse.getmEGList().getEnergyGridList().size()) {
                this.mEnergyGrid = mHouse.getmEGList().getEnergyGridList().get(aux);
                activeInput = true;
            } else {
                System.out.println(INVALID_OPTION);
            }
        }
    }

    /** ------ OPÇÃO LISTAR POR NOMES/POR LISTA - ROOMS -------- **/

    private void getInputRoom() {
        System.out.println(
                "We need to know which one is your room.\n" + "Would you like to:\n" + "1) Type the name of your Room;\n" + "2) Choose it from a list;\n" +
                        "0) Return;");
        int option = readInputNumberAsInt();
        switch (option) {
            case 1:
                getInputRoomName();
                if (!getRoomByName()) {
                    System.out.println("Unable to select a Room. Returning to main menu.");
                    return;
                }
                break;
            case 2:
                getInputRoomByList();
                break;
            case 0:
                return;
            default:
                System.out.println(INVALID_OPTION);
                break;
        }
    }
    private void getInputRoomUS149() {
        System.out.println(
                "We need to know which one is your room.\n" + "Would you like to:\n" + "1) Type the name of your Room;\n" + "2) Choose it from a list;\n" +
                        "0) Return;");
        int option = readInputNumberAsInt();
        switch (option) {
            case 1:
                getInputRoomName();
                if (!getRoomByName()) {
                    System.out.println("Unable to select a Room. Returning to main menu.");
                    return;
                }
                break;
            case 2:
                getInputRoomByListInEG();
                break;
            case 0:
                return;
            default:
                System.out.println(INVALID_OPTION);
                break;
        }
    }

    private boolean getInputRoomName() {
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Please type the name of the Room you want to access.");
        this.mRoomName = mScanner.nextLine();
        return (!(this.mRoomName.equals("exit")));
    }

    private boolean getRoomByName() {
        HouseConfigurationController controller = new HouseConfigurationController();
        List<Integer> listOfIndexesRoom = controller.matchRoomIndexByString(mRoomName, mHouse);

        while (listOfIndexesRoom.isEmpty()) {
            System.out.println("There is no Room with that name. Please insert the name of a Room" +
                    " that exists or  Type 'exit' to cancel and create a new Room on the Main Menu.");
            if (!getInputRoomName()) {
                return false;
            }
            listOfIndexesRoom = controller.matchRoomIndexByString(mRoomName, mHouse);
        }
        if (listOfIndexesRoom.size() > 1) {
            System.out.println("There are multiple Houses with that name. Please choose the right one.");
            System.out.println(controller.printRoomElementsByIndex(listOfIndexesRoom, mHouse));
            int aux = readInputNumberAsInt();
            if (listOfIndexesRoom.contains(aux)) {
                this.mRoom = mHouse.getmRoomList().getListOfRooms().get(aux);
                System.out.println("You have chosen the following Room:");
                System.out.println(controller.printRoom(mRoom));
            } else {
                System.out.println(INVALID_OPTION);
            }
        } else {
            System.out.println("You have chosen the following Room:");
            this.mRoom = mHouse.getmRoomList().getListOfRooms().get(0);
            System.out.println(controller.printRoom(mRoom));
        }
        return true;
    }


    private void getInputRoomByList() {
        if (mHouse.getmRoomList().getListOfRooms().size() == 0) {
            System.out.print("Invalid Room List - List Is Empty\n");
            return;
        }
        boolean activeInput = false;
        System.out.println("Please select one of the existing rooms on the selected House: ");

        while (!activeInput) {
            HouseConfigurationController controller = new HouseConfigurationController();
            controller.printRoomList(mHouse);
            int aux = readInputNumberAsInt();
            if (aux >= 0 && aux < mHouse.getmRoomList().getListOfRooms().size()) {
                this.mRoom = mHouse.getmRoomList().getListOfRooms().get(aux);
                activeInput = true;
            } else {
                System.out.println(INVALID_OPTION);
            }
        }
    }

    private void getInputRoomByListInEG() {
        if (mEnergyGrid.getmListOfRooms().getListOfRooms().size() == 0) {
            System.out.print("Invalid Room List - List Is Empty\n");
            return;
        }
        boolean activeInput = false;
        System.out.println("Please select one of the existing rooms on the selected House: ");

        while (!activeInput) {
            HouseConfigurationController controller = new HouseConfigurationController();
            controller.printRoomListOfEG(mEnergyGrid);
            int aux = readInputNumberAsInt();
            if (aux >= 0 && aux < mEnergyGrid.getmListOfRooms().getListOfRooms().size()) {
                this.mRoom = mEnergyGrid.getmListOfRooms().getListOfRooms().get(aux);
                activeInput = true;
            } else {
                System.out.println(INVALID_OPTION);
            }
        }
    }

    /** ------ GET ROOM CHARACTERISTICS -------- **/

    private void getInputRoomCharacteristics() {
        Scanner input = new Scanner(System.in);

        //GET ROOM DESIGNATION
        System.out.println("Please insert the room name: ");
        this.mRoomName = input.nextLine();

        //GET ROOM HOUSE FLOOR
        System.out.println("Please insert your room's house floor: ");
        while (!input.hasNextInt()) {
            System.out.println("Please insert a valid number.");
        }
        this.mRoomHouseFloor = input.nextInt();

        //GET ROOM DIMENSIONS
        System.out.println("Please insert your room's dimensions in square meters: ");
        while (!input.hasNextDouble()) {
            System.out.println("Please insert a valid number.");
        }
        this.mRoomDimensions = input.nextDouble();
    }


    /** ------ USER STORIES -------- **/

     /**
      * US001UI
      */

     private boolean mTypeAreaListCreated;

     void runUS01UI(TypeAreaList list) {
         getInputUS01();
         updateModelUS01(list);
         displayStateUS01();
     }

     private void getInputUS01() {
         Scanner scanner = new Scanner(System.in);
         System.out.print("Please insert the name of the new Geographic Area Type: ");
         while (!scanner.hasNext("[a-zA-Z_]+")) {
             System.out.println("That's not a valid name a Type Area. Please insert only Alphabetic Characters");
             scanner.next();
         }
         this.mTypeAreaName = scanner.next();
     }

     private void updateModelUS01(TypeAreaList list) {
         HouseConfigurationController ctrl = new HouseConfigurationController(list);
         this.mTypeAreaListCreated = ctrl.createAndAddTypeAreaToList(mTypeAreaName);
     }

     private void displayStateUS01() {
         if (mTypeAreaListCreated) {
             System.out.println("Success, you have inserted a new Type of Geographic Area.");
         } else {
             System.out.println("Failed, you have inserted an invalid or repeated Type of Geographic Area.");
         }
     }

    /**
     * User Story 02
     * <p>
     * As a System Administrator I want to obtain a list of the types of Geographical Areas previously stated.
     * <p>
     * Class responsible for presenting the list.
     */

    public void runUS02(TypeAreaList list) {
        this.mActive = true;
        while (this.mActive) {
            updateModelUS02(list);
            displayStateUS02();
        }
    }

    private void updateModelUS02(TypeAreaList list) {
        HouseConfigurationController ctrl = new HouseConfigurationController(list);
        System.out.println(ctrl.getTypeAreaList());
    }

    private void displayStateUS02() {
        this.mActive = true;
        System.out.println("\nList finished.");
        mActive = false;
    }

    /**
     * * User Story 03
     * As a System Administrator I want to Create a new Geographic Area.
     * This class is responsible for handling user input.
     * It calls the respective US03 controller.
     */

    private String nameOfGeoArea;
    private double geoAreaLat;
    private double geoAreaLong;
    private boolean areaAddedResult;
    private Scanner scanner;

    HouseConfigurationUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * US 03 view - runUS135 method.
     * Reads necessary user inputs .
     * Calls controller to create the GA and add it to the list (received as a parameter).
     * Displays result information to the user.
     *
     * @param newGeoListUi the list where new GA shall be added to
     */
    public void runUS03(GeographicAreaList newGeoListUi, TypeAreaList typeAreaList) {
        this.mActive = true;
        while (this.mActive) {
            getInputNameNewAreaUS03();
            getInputTypeOfAreaUS03();
            getLocalGeoAreaUS03();
            updateGeoAreaUS03(typeAreaList);
            updateModelUS03(newGeoListUi);
            displayStateUS03();
        }
    }

    private void getInputNameNewAreaUS03() {
        this.nameOfGeoArea = readInputString("name");
    }

    private void getInputTypeOfAreaUS03() {
        this.mTypeAreaName = readInputString("Type Area");
    }

    private void getLocalGeoAreaUS03() {
        this.geoAreaLat = readInputNumber("Latitude");
        this.geoAreaLong = readInputNumber("Longitude");
    }

    private void updateGeoAreaUS03(TypeAreaList typeAreaList) {
        System.out.print("The Geographic Area you want to create is " + nameOfGeoArea + " with the type " + mTypeAreaName +
                " and its localization is on " + geoAreaLat + " latitude " + geoAreaLong + " longitude.\n");
        typeAreaList.newTAG(mTypeAreaName);
    }

    private void updateModelUS03(GeographicAreaList newGeoListUi) {
        HouseConfigurationController controller = new HouseConfigurationController();
        this.areaAddedResult = controller.addNewGeoAreaToList(newGeoListUi, nameOfGeoArea, mTypeAreaName, geoAreaLat, geoAreaLong);
    }

    private void displayStateUS03() {
        if (areaAddedResult) {
            System.out.print("The Geographic Area has been successfully added.");
        } else
            System.out.print("The Geographic Area hasn't been added to the list. " +
                    "There is already an area with those input values.");
        this.mActive = false;
    }

    private String createInputMsg(String inputType) {
        return "Please Insert " + inputType + " for the New Geographic Area: ";
    }

    private String createInvalidStringMsg(String inputType) {
        return "That's not a valid " + inputType + ". Please insert only Alphabetic Characters";
    }

    private String createInvalidNumberMsg(String inputType) {
        return "That's not a valid " + inputType + ". Please insert only Numbers";
    }

    private String readInputString(String inputType) {
        System.out.print(createInputMsg(inputType));

        while (!scanner.hasNext("[a-zA-Z\\sà-ùÀ-Ù]*")) {
            System.out.println(createInvalidStringMsg(inputType));
            scanner.next();
        }
        return scanner.next();
    }

    private double readInputNumber(String inputType) {
        System.out.print(createInputMsg(inputType));

        while (!scanner.hasNextDouble()) {
            System.out.println(createInvalidNumberMsg(inputType));
            scanner.next();
        }
        return scanner.nextDouble();
    }

    /**
     * US004 As an Administrator, I want to get a list of existing geographical areas of a given
     * type.
     */


    public void runUS04(TypeAreaList typeAreaList, GeographicAreaList geographicAreaList) {
        this.controller = new HouseConfigurationController();
        this.mGeographicAreaList = geographicAreaList;
        this.active = true;
        while(this.active) {
            getInputTypeArea(typeAreaList);
            if (!matchGAByTypeArea()) {
                this.active = false;
                return;
            } else {
                displayGAListByTypeArea();
                this.active = false;
            }
        }
    }

    private boolean matchGAByTypeArea() {
        if((this.mGeographicAreaList.getGeographicAreaList().isEmpty()) || (this.mGeographicAreaList == null)) {
            System.out.print("The list of Geographic Areas is currently empty.\n Please return to main menu and add a Geographic Area to the list first.");
            return false;
        }
        else {
            this.mGeographicAreaList = this.controller.matchGAByTypeArea(this.mGeographicAreaList, this.mTypeGA);
            this.mTypeAreaName = this.controller.getTypeAreaName(this.mTypeGA);
            return true;
        }
    }

    private void displayGAListByTypeArea() {
        if (this.mGeographicAreaList.getGeographicAreaList().isEmpty()) {
            System.out.println("There are no Geographic Areas of that Area Type.");
        } else {
            System.out.println("Geographic Areas of the type " + this.mTypeAreaName + ":\n");
            this.controller.printGAList(this.mGeographicAreaList);
        }
    }

    /**
     * US005
     */

    private String mTypeSensor;
    private String mNameSensor;
    private boolean mActive;
    private boolean mTypeAdded;

    void runUS05(SensorList list) {
        this.mActive = true;
        while (this.mActive) {
            getInput05Sensor05();
            getInput05();
            updateModel05(list);
            displayState05();
        }
    }

    private void getInput05Sensor05() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type the name of the sensor to add the type to: ");
        this.mNameSensor = scanner.next();
    }

    private void getInput05() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type the type of sensor you want to assign to the sensor: ");
        while (!scanner.hasNext("[a-zA-Z_]+")) {
            System.out.println("That's not a valid name of Type Area. Please insert only Alphabetic Characters");
            scanner.next();
        }
        this.mTypeSensor = scanner.next();
    }

    private void updateModel05(SensorList list) {
        HouseConfigurationController controller = new HouseConfigurationController(list);
        this.mTypeAdded = controller.setTypeSensor(mNameSensor, mTypeSensor);
    }

    private void displayState05() {
        if (mTypeAdded) {
            System.out.print("The type has been successfully assigned.");
        } else System.out.print("The type of sensor wasn't added. There's no sensor with that name.");
        mActive = false;
    }

    /**
     * US006
     */

    private boolean active;
    private String sensorName;
    private String sensorType;
    private double sensorLat;
    private double sensorLong;
    private double sensorAlt;
    private int dataYear;
    private int dataMonth;
    private int dataDay;
    private Sensor mSensor;
    private String mGeographicAreaName;
    private SensorList mSensorList;
    private GeographicAreaList mGeographicAreaList;

    HouseConfigurationUI(SensorList s, GeographicAreaList a) {
        this.mSensorList = s;
        this.mGeographicAreaList = a;
        active = false;
        // placeholder
    }

    void run06() {
        this.active = true;
        while (this.active) {
            getInput06();
            updateUS06();
            displayUS06();
            getInputPart206();
            updateAndDisplayUS06Part206();
            this.active = false;
        }
    }

    private void getInput06() {
        Scanner input = new Scanner(System.in);

        //Console title
        System.out.println("***************************************************\n" +
                "************** Sensor Addition Menu ***************\n" +
                "****************** sWitCh 2018 ********************\n" +
                "***************************************************\n");

        System.out.println("**********  New Sensor Input  ***********\n");

        // Name Getter
        System.out.println("\nEnter Sensor Name:\t");
        this.sensorName = input.next();
        System.out.println("You entered sensor " + sensorName);

        // Type Getter
        System.out.println("\nEnter Sensor type:\t");

        while (!input.hasNext("[a-zA-Z]+")) {
            input.next();
            System.out.println("Not a valid type. Try again");
        }

        this.sensorType = input.next();
        System.out.println("You entered type " + sensorType);
        input.nextLine();
        // Local Getter
        System.out.println("\nEnter Sensor Localization:\t");
        System.out.println("\nEnter Latitude:\t");
        while (!input.hasNextDouble()) {
            input.next();
            System.out.println("Not a valid latitude. Try again");
        }
        this.sensorLat = input.nextDouble();
        input.nextLine();
        System.out.println("\nEnter Longitude:\t");
        while (!input.hasNextDouble()) {
            input.next();
            System.out.println("Not a valid longitude. Try again");
        }
        this.sensorLong = input.nextDouble();
        System.out.println("\nEnter Altitude:\t");
        while (!input.hasNextDouble()) {
            input.next();
            System.out.println("Not a valid altitude. Try again");
        }
        this.sensorAlt = input.nextDouble();
        input.nextLine();
        System.out.println("You entered sensor on coordinates  " + sensorLat + "  ,  " + sensorLong + "  ,  " + sensorAlt);

        // Date Getter
        System.out.println("\nEnter Sensor starting date:\t");
        System.out.println("\nEnter the year:\t");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Not a valid year. Try again");
        }
        this.dataYear = input.nextInt();
        input.nextLine();
        System.out.println("\nEnter the Month:\t");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Not a valid month. Try again");
        }
        this.dataMonth = input.nextInt();
        input.nextLine();
        System.out.println("\nEnter the Day:\t");
        while (!input.hasNextInt()) {
            input.next();
            System.out.println("Not a valid day. Try again");
        }
        this.dataDay = input.nextInt();
        System.out.println("You entered the date successfully!");
        input.nextLine();
    }

    private void updateUS06() {
        HouseConfigurationController ctrl06 = new HouseConfigurationController();
        Local mLocal = ctrl06.createLocal(this.sensorLat, this.sensorLong, this.sensorAlt);
        TypeSensor mType = ctrl06.createType(this.sensorType);
        Date mDate = ctrl06.createDate(this.dataYear, this.dataMonth, this.dataDay);
        this.mSensor = ctrl06.createSensor(this.sensorName, mType, mLocal, mDate);
    }

    private void displayUS06() {
        this.active = true;
        HouseConfigurationController ctrl06 = new HouseConfigurationController();
        if (ctrl06.addSensor(mSensor, mSensorList)) {
            System.out.println("\n \n Sensor has been successfully added to the list");
        } else {
            System.out.println("\n \nSensor could not be added to the list.");
        }
    }

    private void getInputPart206() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n Add sensor to Geographic Area?\n");
        System.out.println("Yes/No:\t");
        if ("Yes".equals(input.nextLine()) || "yes".equals(input.nextLine()) || "Y".equals(input.nextLine()) || "y".equals(input.nextLine())) {
            System.out.println("Type the name of the Geographic Area which the sensor will be added to");
            System.out.println("\nEnter Geographic Area Name:\t");
            this.mGeographicAreaName = input.nextLine();
            System.out.println("You entered  " + this.mGeographicAreaName);
        } else {
            System.out.println("Exiting");
        }
    }

    private void updateAndDisplayUS06Part206() {

        HouseConfigurationController ctrl06 = new HouseConfigurationController();
        if (ctrl06.addSensorToGeographicArea(mGeographicAreaName, mGeographicAreaList, mSensorList)) {
            System.out.println("\nSensor has been successfully added to the Geographic Area");
        } else {
            System.out.println("\nSensor could not be added to the Area.");
        }
    }

    /**
     * US07 UI
     */

    private GeographicAreaList mGeoList;
    private String mNameGeographicAreaDaughter;
    private String mNameGeographicAreaMother;

    void runUS07(GeographicAreaList newGeoListUi) {
        this.mActive = true;
        this.mGeoList = newGeoListUi;
        while (this.mActive) {
            if (newGeoListUi.getGeographicAreaList().isEmpty()) {
                System.out.println("The list is empty.");
                return;
            } else {
                getInputGeographicArea(newGeoListUi);
                this.mNameGeographicAreaMother = mGeoArea.getName();
                displayGeoArea(mNameGeographicAreaMother, newGeoListUi);
                getInputGeographicArea(newGeoListUi);
                this.mNameGeographicAreaDaughter = mGeoArea.getName();
                displayGeoArea(mNameGeographicAreaDaughter, newGeoListUi);
                updateStateUS07();
                displayStateUS07();
            }
        }
    }

    private boolean displayGeoListUS07() {
        HouseConfigurationController ctrl = new HouseConfigurationController(mGeoList);
        if (ctrl.getGeographicAreaList().getGeographicAreaList().isEmpty()) {
            System.out.println(ctrl.printGeographicAreaListNames());
            return false;
        } else {
            System.out.println(ctrl.printGeographicAreaListNames());
            return true;
        }
    }

    private void displayGeoArea(String name, GeographicAreaList list) {
        HouseConfigurationController ctrl = new HouseConfigurationController(list);
        if (ctrl.validateGeoArea(name)) {
            System.out.println("Success, you have inserted a valid Geographic Area.");
        } else {
            System.out.println("Unsuccess, you have inserted a non-existing Geographic Area.");
        }
    }

    private void updateStateUS07() {
        HouseConfigurationController ctrl = new HouseConfigurationController(mGeoList);
        GeographicArea daughterArea = ctrl.matchGeoArea(mNameGeographicAreaDaughter);
        GeographicArea motherArea = ctrl.matchGeoArea(mNameGeographicAreaMother);
        ctrl.setMotherArea(daughterArea, motherArea);
    }

    private void displayStateUS07() {
        System.out.print("The Geographic Area " + mNameGeographicAreaDaughter + " is contained in " + mNameGeographicAreaMother + "\n");
        active = false;
    }

    /**
     * US08 UI
     */

    private String mNameGeographicAreaContained;
    private String mNameGeographicAreaContainer;

    void runUS08(GeographicAreaList list) {
        this.mActive = true;
        while (this.mActive) {
            getInputGeographicContainerUS08(list);
            getInputGeographicContainedUS08(list);
            verifyAndDisplayStateUS08(list);
        }
    }

    /**
     * getInputGeographicContainer()
     * this method makes the user define the NAME of the GeographicArea CONTAINER
     */
    private void getInputGeographicContainerUS08(GeographicAreaList list) {
       getInputGeographicArea(list);
       this.mNameGeographicAreaContainer = mGeoArea.getName();
    }

    /**
     * getInputGeographicContainer()
     * this method makes the user define the NAME of the GeographicArea CONTAINED
     */
    private void getInputGeographicContainedUS08(GeographicAreaList list) {
        getInputGeographicArea(list);
        this.mNameGeographicAreaContained = mGeoArea.getName();
    }

    /**
     * @param list is the MainUI List
     *             First we check if the Geographic Areas that we are testing exist in the MainUI list.
     *             Then we check the GeographicAreaContained for its flag
     *             And finally it tests the flag (Geographic Area) is equal to the testing GeographicArea Container
     */

    private void verifyAndDisplayStateUS08(GeographicAreaList list) {
        HouseConfigurationController controller = new HouseConfigurationController(list);
        if (!(controller.matchGeographicAreas(mNameGeographicAreaContained, mNameGeographicAreaContainer))) {
            System.out.println("The given areas are invalid!");
            return;
        }
        if (!(controller.seeIfItsContained())) {
            System.out.println(mNameGeographicAreaContained + " is NOT contained in " + mNameGeographicAreaContainer);
            this.mActive = false;
            return;
        }
        System.out.println(mNameGeographicAreaContained + " is contained in " + mNameGeographicAreaContainer);
        this.mActive = false;
        return;
    }


    /**
     * US101 UI
     */

    private double mHouseLat;
    private double mHouseLon;
    private String mHouseAddress;
    private String mHouseZipCode;
    private House mHouse;
    private String geoName;
    private GeographicArea mGeoArea;
    private static final String INVALID_OPTION = "Please enter a valid option";


    void runUS101(GeographicAreaList list) {
        Scanner mScanner = new Scanner(System.in);
        this.controller = new HouseConfigurationController();
        if (list == null || list.getGeographicAreaList().isEmpty()) {
            System.out.println("Invalid Geographic Area List - List Is Empty");
            return;
        }
        getInputGeographicArea(list);
        if (mGeoArea == null) {
            System.out.println("Unable to select a Geographic Area. Returning to main menu.");
            return;
        }
        getInputHouse(mGeoArea);
        if (mHouse == null) {
            System.out.println("Unable to select a house. Returning to main menu.");
            return;
        }
        getInputHouseCharacteristicsUS101();
        updateModelUS101();
        displayStateUS101();
    }

    private void getInputHouseCharacteristicsUS101() {

        Scanner scanner = new Scanner(System.in);

        //gethouseaddress
        System.out.print("Please, type the address of the house: ");
        this.mHouseAddress = scanner.nextLine();


        //getzipcode
        System.out.print("Please, type the Zip Code of the house: ");
        this.mHouseZipCode = scanner.nextLine();


        //getlatitude
        System.out.print("Please, type the latitude: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Please,try again. Only numbers this time:");
            scanner.next();
        }
        this.mHouseLat = scanner.nextDouble();


        //getlongitude
        System.out.print("Please, type the latitude: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Please,try again. Only numbers this time:");
            scanner.next();
        }
        this.mHouseLon = scanner.nextDouble();

    }

    private void updateModelUS101() {
        HouseConfigurationController ctrl = new HouseConfigurationController();
        ctrl.setHouseLocal(mHouseLat, mHouseLon, mHouse);
        ctrl.setHouseZIPCode(mHouseZipCode, mHouse);
        ctrl.setHouseAddress(mHouseAddress, mHouse);
    }

    private void displayStateUS101() {
        System.out.println("You have successfully changed the location of the house " + mHouse.getHouseDesignation() + ". \n" + "Address: " +
                mHouseAddress + ". \n" + "ZipCode: " + mHouseZipCode + ". \n" + "Latitude: " + mHouseLat + ". \n" +
                "Longitude: " + mHouseLon + ". \n");
    }

    /**
     * US 105UI
     */

    private String mRoomName;
    private int mRoomHouseFloor;
    private double mRoomDimensions;

    void runUS105(GeographicAreaList gaList) {
        Scanner mScanner = new Scanner(System.in);
        this.controller = new HouseConfigurationController();
        getInputRoomCharacteristics();
        updateInputRoom();
        displayStateRoom();
        if (gaList == null || gaList.getGeographicAreaList().size() == 0) {
            System.out.println("Invalid Geographic Area List - List Is Empty");
            return;
        }
        getInputGeographicArea(gaList);
        if (mGeoArea == null) {
            System.out.println("Unable to select a Geographic Area. Returning to main menu.");
            return;
        }
        getInputHouse(mGeoArea);
        if (mHouse == null) {
            System.out.println("Unable to select a house. Returning to main menu.");
            return;
        }
        updateRoomAndDisplayState();

    }

    private void updateInputRoom() {
        this.controller.createNewRoom(mRoomName, mRoomHouseFloor, mRoomDimensions);
    }

    private void displayStateRoom() {
        //SHOW ROOM ENTERED BY USER
        if (mRoomHouseFloor == 1) {
            System.out.println("Your new room is called " + mRoomName + ", it is located on the " + mRoomHouseFloor + "st floor and has " + mRoomDimensions + " square meters.");
        } else if (mRoomHouseFloor == 2) {
            System.out.println("Your new room is called " + mRoomName + ", it is located on the " + mRoomHouseFloor + "nd floor and has " + mRoomDimensions + " square meters.");
        } else if (mRoomHouseFloor == 3) {
            System.out.println("Your new a room is called " + mRoomName + ", it is located on the " + mRoomHouseFloor + "rd floor and has " + mRoomDimensions + " square meters.");
        } else {
            System.out.println("Your new a room is called " + mRoomName + ", it is located on the " + mRoomHouseFloor + "th floor and has " + mRoomDimensions + " square meters.");
        }
    }

    private boolean getInputGeographicAreaName() {
        Scanner mScanner = new Scanner(System.in);
        System.out.println("Please type the name of the Geographic Area Where Your House Is Located.");
        this.geoName = mScanner.nextLine();
        return (!(geoName.equals("exit")));
    }


    private void getInputHouse() {
        if (mGeoArea.getHouseList().getHouseList().size() == 0) {
            System.out.print("Invalid House List - List Is Empty\n/**/");
            return;
        }

        boolean activeInput = false;
        System.out.println("Please select one of the existing houses on the selected geographic area: ");

        while (!activeInput) {
            this.controller.printHouseList(mGeoArea);
            int aux = readInputNumberAsInt();
            if (aux >= 0 && aux < mGeoArea.getHouseList().getHouseList().size()) {
                mHouse = mGeoArea.getHouseList().getHouseList().get(aux);
                activeInput = true;
            } else {
                System.out.println(INVALID_OPTION);
            }
        }
    }

    private void updateRoomAndDisplayState() {
        String mHouseName = controller.getHouseName(this.mHouse);
        if (controller.addRoomToHouse(this.mHouse)) {
            System.out.println("The room " + this.mRoomName + " has been added to house " + mHouseName + ".");
        } else {
            System.out.println("The room you entered already exists in house " + mHouseName + ".");
        }
        this.active = false;
    }

    /**
     * US108
     **/

    private String mHouseName;
    private Room mRoom;

    void runUS108UI(GeographicAreaList newGeoListUi) {
        Scanner mScanner = new Scanner(System.in);
        this.controller = new HouseConfigurationController();

        if (newGeoListUi == null || newGeoListUi.getGeographicAreaList().size() == 0) {
            System.out.println("Invalid Geographic Area List - List Is Empty");
            return;
        }
        getInputGeographicArea(newGeoListUi);
        if (mGeoArea == null) {
            System.out.println("Unable to select a Geographic Area. Returning to main menu.");
            return;
        }
        getInputHouse(mGeoArea);
        if (mHouse == null) {
            System.out.println("Unable to select a house. Returning to main menu.");
            return;
        }
        getInputRoom();
        getInputRoomCharacteristics();
        updateInputRoom();
        editRoom();
    }

    private void editRoom() {
        this.controller.editRoom(this.mRoom, this.mRoomName, this.mRoomHouseFloor, this.mRoomDimensions);
        System.out.println("The room is now called " + this.mRoomName + ", it is located on the " + this.mRoomHouseFloor + " floor and has " + this.mRoomDimensions + " square meters.");
    }
    /**
     * US 130 UI
     */

    void runUS130(HouseList houseList) {
        this.controller = new HouseConfigurationController(houseList);
        getInputHouseName();
        getInputAndAddEnergyGrid();
        updateEnergyGridList();
    }

    private void getInputAndAddEnergyGrid() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the designation of the energy grid you want to create: ");
        String name = scanner.next();
        System.out.println("Set the maximum potency of this energy grid: ");
        double maxPower = scanner.nextDouble();
        controller.createEnergyGrid(name, maxPower);
    }

    private void updateEnergyGridList() {
        if (controller.addEnergyGridToHouse()) {
            System.out.println("The energy grid was successfully added to the selected house.");
        } else {
            System.out.println("The energy grid was NOT added to the selected house.");
        }
    }

    /**
     * US135 UI
     */

    void runUS135(HouseList houseList) {
        this.controller = new HouseConfigurationController(houseList);
        getInputAndUpdateHouseName();
        getInputAndSelectEnergyGrid();
        getInputAndCreatePowerSource();
        updateModelAndDisplayState();
    }

    private void getInputAndUpdateHouseName() {
        System.out.println("Please insert the house name that you want to add a power source to one of its energy grids: ");
        Scanner scanner = new Scanner(System.in);
        String houseName = scanner.nextLine();
        if (controller.seeIfHouseExistsInHouseList(houseName)) {
            System.out.println("The house you have inserted is on the list.");
        } else {
            System.out.println("The house you have inserted is not on the list.");
        }
    }

    private void getInputAndSelectEnergyGrid() {
        System.out.println(controller.seeIfEnergyGridListIsEmptyAndShowItsContent());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the designation of the energy grid you want to add a power source to: ");
        String name = scanner.next();
        if (controller.selectEnergyGrid(name)) {
            System.out.println("The energy grid was selected with success.");
        }
    }

    private void getInputAndCreatePowerSource() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the designation of the power source you want to add: ");
        String name = scanner.next();
        System.out.println("Type the maximum power output of the power source you want to add: ");
        double maxPowerOutput = scanner.nextDouble();
        System.out.println("Type the maximum energy storage of the power source you want to add (type 0 if the power source can't storage energy.): ");
        double maxEnergyStorage = scanner.nextDouble();
        controller.createPowerSource(name, maxPowerOutput, maxEnergyStorage);
    }

    private void updateModelAndDisplayState() {
        if (controller.addPowerSourceToEnergyGrid()) {
            System.out.println("The power source was added with success!");
        } else {
            System.out.println("The power source was NOT added to the energy grid!");
        }
    }

    /**
     * US145
     */

    private EnergyGrid mEnergyGrid;

    private String mGridName;

    void runUS145(GeographicAreaList list) {
        getInputGeographicArea(list);
        getInputHouse(mGeoArea);
        getInputEnergyGrid();
        displayRoomList(mEnergyGrid);
    }

    private void displayRoomList(EnergyGrid energyGrid){
        HouseConfigurationController controller = new HouseConfigurationController();
        System.out.println(controller.printRooms(energyGrid.getmListOfRooms()));
    }
    /**
     * US147
     */

    void runUS147(GeographicAreaList list) {
        getInputGeographicArea(list);
        getInputHouse(mGeoArea);
        getInputEnergyGrid();
        getInputRoom();
        if (updateStateEnergyGridUS147(mEnergyGrid, mRoom)){
            System.out.println("Room successfully added to the grid!");
        }
        else System.out.println("It wasn't possible to add the room. Please try again.");
    }

    private boolean updateStateEnergyGridUS147(EnergyGrid grid, Room room){
        HouseConfigurationController controller = new HouseConfigurationController();
        return controller.addRoomToTheGrid(grid, room);
    }
    /**
     * US149
     */

    void runUS149(GeographicAreaList list) {
        getInputGeographicArea(list);
        getInputHouse(mGeoArea);
        getInputEnergyGrid();
        getInputRoomUS149();
        if (updateStateEnergyGrid(mEnergyGrid, mRoom)){
            System.out.println("Room successfully removed from grid!");
        }
        else System.out.println("It wasn't possible to remove the room. Please try again.");
    }

    private boolean updateStateEnergyGrid(EnergyGrid grid, Room room){
        HouseConfigurationController controller = new HouseConfigurationController();
        return controller.removeRoomFromGrid(grid, room);
    }
}