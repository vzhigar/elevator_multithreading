package by.training.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class BuildingTest {
    private Building building;
    private int storeysNumber;

    @Before
    public void init() {
        storeysNumber = 3;
        final int elevatorCapacity = 4;
        building = new Building(storeysNumber, elevatorCapacity);
    }

    @Test
    public void testBuildingCreatedWithCorrectStoreyNumber() {
        Assert.assertTrue("first floor number is 1", building.getFloors().get(0).getNumber() == 1);
        Assert.assertTrue("second floor number is 2", building.getFloors().get(1).getNumber() == 2);
        Assert.assertTrue("third floor number is 3", building.getFloors().get(2).getNumber() == 3);
    }

    @Test
    public void testFloorsCountIsCorrect() {
        Assert.assertThat("floors quantity = floorsNumber created after creation of building", building.getFloors().size(), is(storeysNumber));
    }
}
