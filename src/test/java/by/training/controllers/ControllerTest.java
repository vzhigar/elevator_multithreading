package by.training.controllers;

import by.training.beans.Building;
import by.training.beans.Elevator;
import by.training.beans.Passenger;
import by.training.enums.Direction;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ControllerTest {
    private Elevator elevator;
    private Building building;
    private Controller controller;
    private Passenger passenger;
    private int storiesNumber = 3;

    @Before
    public void init() {
        final int elevatorCapacity = 2;
        final int initialStory = 1;
        final int destinationStory = 3;
        building = new Building(storiesNumber, elevatorCapacity);
        elevator = building.getElevator();
        controller = new Controller(building);
        passenger = new Passenger(initialStory, destinationStory);

    }

    @Test
    public void testElevatorMovementUp() {
        int currentStory;
        controller.nextFloor();
        controller.nextFloor();
        currentStory = elevator.getCurrentFloor().getNumber();
        assertTrue("Floor number after two movements must be 3", currentStory == 3);
    }

    @Test
    public void testElevatorMovementDown() {
        controller.nextFloor();
        controller.nextFloor();
        controller.nextFloor();
        int currentStorey = elevator.getCurrentFloor().getNumber();
        assertTrue("Floor number after 3 movements must be 2", currentStorey == 2);
        controller.nextFloor();
        currentStorey = elevator.getCurrentFloor().getNumber();
        assertTrue("Floor number after 4 movements must be 1", currentStorey == 1);
    }

    @Test
    public void testElevatorChangingMovementDirection() {
        int currentStorey = 1;
        Direction initialDirection = elevator.getDirection();
        while (currentStorey <= storiesNumber) {
            controller.nextFloor();
            currentStorey++;
        }
        Direction directionAfterMoving = elevator.getDirection();
        assertNotSame("direction changed", initialDirection, directionAfterMoving);
        controller.nextFloor();
        controller.nextFloor();
        directionAfterMoving = elevator.getDirection();
        assertSame("direction changed", directionAfterMoving, initialDirection);
    }

    @Test
    public void testTransportationFinishedCondition() {
        assertThat("Transportation is finished", controller.transportationIsFinished(), is(true));
        building.getFloors().get(0).addDispatchPassenger(passenger);
        assertThat("Transportation not finished, dispatch container on floor 1 is not empty",
                controller.transportationIsFinished(), is(false));
        building.getFloors().get(0).removeDispatchPassenger(passenger);
        assertThat("Transportation is finished", controller.transportationIsFinished(), is(true));
        elevator.addPassenger(passenger);
        assertThat("Transportation not finished, elevator container not empty",
                controller.transportationIsFinished(), is(false));
        elevator.removePassenger(passenger);
    }

    @Test
    public void testPassengerEnterInElevator() {
        building.getFloors().get(passenger.getInitialStory() - 1).addDispatchPassenger(passenger);
        assertTrue(controller.enter(passenger));
        assertTrue(elevator.getPassengers().contains(passenger));
    }

    @Test
    public void testPassengerExitFromElevator() {
        elevator.addPassenger(passenger);
        controller.exit(passenger);
        assertFalse(elevator.getPassengers().contains(passenger));
    }

}
