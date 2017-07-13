package by.training.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import by.training.beans.Building;
import by.training.beans.Elevator;
import by.training.beans.Floor;
import by.training.beans.Passenger;
import by.training.enums.TransportationState;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidatorTest {
    private Elevator elevator;
    private Building building;
    private Validator validator;
    private Passenger passenger;
    private final int storiesNumber = 9;
    private final int elevatorCapacity = 4;

    @Before
    public void init() {
        building = new Building(storiesNumber, elevatorCapacity);
        elevator = building.getElevator();
        validator = new Validator(building, 5);
        passenger = mock(Passenger.class);
    }

    @Test
    public void testElevatorIsEmpty() {
        assertTrue("Elevator container is empty", validator.isElevatorEmpty());
    }

    @Test
    public void testElevatorIsNotEmpty() {
        elevator.addPassenger(passenger);
        assertFalse("Elevator container is not empty", validator.isElevatorEmpty());
    }

    @Test
    public void testCorrectValidationConditions() {
        List<Floor> floors = building.getFloors();
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger(1,4));
        passengers.add(new Passenger(3, 5));
        passengers.add(new Passenger(6, 9));
        passengers.add(new Passenger(4, 7));
        passengers.add(new Passenger(9 ,7));
        for (Passenger passenger : passengers) {
            final int initFloorIndex = passenger.getDestinationStory() - 1;
            floors.get(initFloorIndex).addArrivalPassenger(passenger);
            passenger.setTransportationState(TransportationState.COMPLETED);
        }
        assertTrue("all passengers transportation state is completed", validator.validateTransportation());
        Passenger passenger = new Passenger(5, 2);
        int initIndex = passenger.getInitialStory() - 1;
        int destIndex = passenger.getDestinationStory() - 1;
        floors.get(destIndex).addArrivalPassenger(passenger);
        assertFalse(validator.validateTransportation());
        floors.get(destIndex).getArrivalContainer().remove(passenger);
        assertTrue(validator.validateTransportation());
        floors.get(initIndex).addDispatchPassenger(passenger);
        assertFalse(validator.validateTransportation());
        floors.get(initIndex).removeDispatchPassenger(passenger);
        assertTrue(validator.validateTransportation());
    }
}
