package by.training.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.hamcrest.core.Is.is;

public class FloorTest {
    Passenger passenger = mock(Passenger.class);
    Floor floor;

    @Before
    public void init() {
        final int number = 1;
        floor = new Floor(number);
    }

    @Test
    public void testAddArrivalPassenger() {
        Assert.assertTrue(floor.addArrivalPassenger(passenger));
        Assert.assertThat("Arrival container has one passenger", floor.getArrivalContainer().size(), is(1));
    }

    @Test
    public void testAddDispatchPassenger() {
        Assert.assertTrue(floor.addDispatchPassenger(passenger));
        Assert.assertThat("Dispatch container has one passenger", floor.getDispatchContainer().size(), is(1));
    }

    @Test
    public void testRemoveDispatchPassenger() {
        floor.addDispatchPassenger(passenger);
        Assert.assertThat("Dispatch container has one passenger", floor.getDispatchContainer().size(), is(1));
        Assert.assertTrue(floor.removeDispatchPassenger(passenger));
        Assert.assertThat("Dispatch container is empty", floor.getDispatchContainer().isEmpty(), is(true));
    }
}
