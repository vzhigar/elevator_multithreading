package by.training.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class ElevatorTest {
    int storeysNumber;
    private Elevator elevator;
    private Passenger passenger;

    @Before
    public void init() {
        storeysNumber = 5;
        final int elevatorCapacity = 3;
        Building building = new Building(storeysNumber, elevatorCapacity);
        elevator = building.getElevator();
        passenger = mock(Passenger.class);
    }

    @Test
    public void testElevatorIsFull() {
        final int initialFloor = 1;
        final int destinationFloor = 3;
        elevator.addPassenger(new Passenger(initialFloor, destinationFloor));
        elevator.addPassenger(new Passenger(initialFloor, destinationFloor));
        Assert.assertThat("elevator is not full", elevator.isFull(), is(false));
        elevator.addPassenger(new Passenger(initialFloor, destinationFloor));
        Assert.assertThat("elevator is full", elevator.isFull(), is(true));
    }

    @Test
    public void testAddPassenger() {
        Assert.assertTrue(elevator.addPassenger(passenger));
        Assert.assertThat(elevator.getPassengers().size(), is(1));
    }

    @Test
    public void testRemovePassenger() {
        Assert.assertTrue(elevator.addPassenger(passenger));
        elevator.removePassenger(passenger);
        Assert.assertThat("passenger removed from elevator", elevator.getPassengers().isEmpty(), is(true));
    }

    @Test
    public void testElevatorMovingUp() {
        int floorNumberBeforeMoving = elevator.getCurrentFloor().getNumber();
        elevator.nextFloor();
        int floorNumberAfterMoving = elevator.getCurrentFloor().getNumber();
        Assert.assertThat("elevator moving up", floorNumberBeforeMoving < floorNumberAfterMoving, is(true));
    }

    @Test
    public void testElevatorMovementDown() {
        elevator.nextFloor();
        elevator.previousFloor();
        Assert.assertThat("elevator is on the 1 floor", elevator.getCurrentFloor().getNumber(), is(1));
    }

    @Test
    public void testElevatorChangingDirectionAfterTheLastFloor() {
        for (int i = 0; i < storeysNumber; i++) {
            elevator.move();
        }
        Assert.assertThat("elevator is on the 4 floor", elevator.getCurrentFloor().getNumber(), is(storeysNumber - 1));
    }

    @Test
    public void testElevatorChangingDirectionAfterTheFirstFloor() {
        int destinationFloor = 3;
        for (int i = 0; i < (storeysNumber * 2); i++) {
            elevator.move();
        }
        Assert.assertThat("elevator is on the 3 floor", elevator.getCurrentFloor().getNumber(), is(destinationFloor));
    }
}
