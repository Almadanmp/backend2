package Sprint0.Controller;

import Sprint0.Model.TypeArea;
import Sprint0.Model.TypeAreaList;
import Sprint0.UI.MainUI;

/**
 * User Story 01
 * As a system administrator, I wish to define a new type of geographic area, so that later I can classify said geographic area.
 */
public class US01Controller {
 private TypeAreaList mTypeAreaList = new TypeAreaList();
    public US01Controller() {
    }

    /**
     * This method creates a new Type of Geographic Area.
     *
     * @param input - the String name of the Type of Geographic Area.
     * @return true - the Type of Geographic Area was successfully created;
     * false - the name is null.
     */
    public boolean CreateAndAddTypeAreaToList(String input) {
        return MainUI.mTypeAreaList.newTAG(input);
    }

    public String getTypeAreaList(){

        return mTypeAreaList.getTypeAreaList();
    }
}
