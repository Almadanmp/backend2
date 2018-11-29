package Sprint_0;


import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Geographic_Area_Test {

    @Test
    public void seeIfGetSetTypeWorks() {
        //Arrange
        TypeArea t1 = new TypeArea("Rua");
        Local l1 = new Local(38, 7);
        TypeArea expectedResult = t1;
        TypeArea actualResult;
        Geographic_Area c = new Geographic_Area(t1, l1);

        //Act
        actualResult = c.getTypeArea();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfGetSetLocalWorks() {
        //Arrange
        TypeArea t1 = new TypeArea("Rua");
        Local l1 = new Local(38, 7);
        Geographic_Area c = new Geographic_Area(t1, l1);
        boolean expectedResult = true;

        //Act
        c.setLocal(l1);
        boolean actualResult = c.getLocal().equals(new Local(38, 7));

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfGetSetSensorListWorks() {
        //Arrange
        TypeArea t1 = new TypeArea("Rua");
        Local l1 = new Local(21, 38, 40);
        Sensor s1 = new Sensor("Vento", new TypeSensor("Atmosphere"), new Local(12, 31, 21), new Date());
        SensorList list1 = new SensorList(s1);
        SensorList expectedResult = new SensorList(new Sensor[]{s1});
        SensorList actualResult;
        Geographic_Area c = new Geographic_Area(t1, l1, new SensorList(s1));

        //Act
        c.setSensorList(list1);
        actualResult = c.getSensorList();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfSetSensorListRefusesListWithDuplicates() {
        //Arrange
        Sensor s1 = new Sensor("Vento", new TypeSensor("Atmosphere"), new Local(12, 31, 21), new Date());
        Sensor s2 = new Sensor("Vento", new TypeSensor("Atmosphere"), new Local(12, 31, 21), new Date());
        Sensor s3 = new Sensor("Chuva", new TypeSensor("Atmosphere"), new Local(12, 31, 21), new Date());
        SensorList l1 = new SensorList(new Sensor[]{s1, s2, s3});
        Geographic_Area c = new Geographic_Area(new TypeArea("Rua"), new Local(12, 35, 2), new SensorList(s1));
        boolean expectedResult = false;
        boolean actualResult;

        //Act
        actualResult = c.setSensorList(l1);

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void seeIfcalculateDistanceToGAWorks(){
        Local l1 = new Local(23,46);
        Local l2 = new Local(25,47);
        TypeArea t1 = new TypeArea("Porto");
        TypeArea t2 = new TypeArea("Braga");
        Geographic_Area ga1 = new Geographic_Area(t1, l1);
        Geographic_Area ga2 = new Geographic_Area(t2, l2);
        double result = ga1.calculateDistanceToGA(ga2);
        double expectedresult = 244;
        assertEquals(expectedresult, result, 1);
    }
}
