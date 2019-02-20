package pt.ipp.isep.dei.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * TypeAreaList tests class.
 */

class TypeAreaListTest {

    @Test
    void newTAG() {
        TypeAreaList newList = new TypeAreaList();
        boolean result = newList.newTAG("cidade");
        assertTrue(result);
    }

    @Test
    void seeIfNewTAGWorksWithAnother() {
        TypeArea type = new TypeArea("rua");
        TypeAreaList newList = new TypeAreaList();
        newList.addTypeArea(type);
        boolean result = newList.newTAG("cidade");
        assertTrue(result);
    }

    @Test
    void seeIfNewTAGDoesNotWorkWhenDuplicatedISAdded() {
        TypeArea tipo = new TypeArea("cidade");
        TypeAreaList expectedResult = new TypeAreaList();
        expectedResult.addTypeArea(tipo);
        boolean result = expectedResult.newTAG("cidade");
        assertFalse(result);
    }

    @Test
    void seeIfNewTAGDoesNotWorkWhenNullIsAdded() {
        TypeArea tipo = new TypeArea("cidade");
        TypeAreaList list = new TypeAreaList();
        list.addTypeArea(tipo);
        boolean result = list.newTAG(null);
        assertFalse(result);
    }

    @Test
    void seeIfNewTAGDoesNotWorkWhenNameIsEmpty() {
        TypeArea type = new TypeArea("cidade");
        TypeAreaList list = new TypeAreaList();
        list.addTypeArea(type);
        boolean result = list.newTAG("");
        assertFalse(result);
    }

    @Test
    void seeIfNewTAGDoesNotWorkWhenNumbersAreAdded() {
        TypeArea type = new TypeArea("cidade");
        TypeAreaList list = new TypeAreaList();
        list.addTypeArea(type);
        boolean result = list.newTAG("cidade1");
        assertFalse(result);
    }

    @Test
    void seeIfPrintGAWholeList() {
        TypeArea type1 = new TypeArea("cidade");
        TypeArea type2 = new TypeArea("distrito");
        TypeArea type3 = new TypeArea("aldeia");
        TypeAreaList list = new TypeAreaList();
        list.addTypeArea(type1);
        list.addTypeArea(type2);
        list.addTypeArea(type3);
        list.addTypeArea(type1);
        String expectedResult = "---------------\n" +
                "0) Name: cidade \n" +
                "1) Name: distrito \n" +
                "2) Name: aldeia \n" +
                "---------------\n";
        String result;
        result = list.buildGATypeWholeListString(list);
        assertEquals(expectedResult, result);
    }
    @Test
    void isEmpty() {
        //Arrange
        TypeArea type1 = new TypeArea("city");
        TypeArea type2 = new TypeArea("village");
        TypeAreaList list1 = new TypeAreaList(); //EMPTY LIST
        TypeAreaList list2 = new TypeAreaList(); //ONE TYPE AREA
        TypeAreaList list3 = new TypeAreaList(); //TWO TYPE AREAS

        list2.addTypeArea(type1);
        list3.addTypeArea(type1);
        list3.addTypeArea(type2);

        //Act
        boolean actualResult1 = list1.isEmpty();
        boolean actualResult2 = list2.isEmpty();
        boolean actualResult3 = list3.isEmpty();

        //Assert
        assertTrue(actualResult1);
        assertFalse(actualResult2);
        assertFalse(actualResult3);
    }
}