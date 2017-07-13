package by.training.beans;

import by.training.enums.Direction;
import by.training.enums.TransportationState;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class PassengerTest {

    @Test
    public void testPassengersCreationWithUniqueId() {
        final int initialFloor = 2;
        final int destinationFloor = 4;
        Passenger passenger1 = new Passenger(initialFloor, destinationFloor);
        Passenger passenger2 = new Passenger(initialFloor, destinationFloor);
        Passenger passenger3 = new Passenger(initialFloor, destinationFloor);
        assertThat("Each passenger id is unique", passenger1.getId(), not(passenger2.getId()));
        assertThat("Each passenger id is unique", passenger1.getId(), not(passenger3.getId()));
        assertThat("Each passenger id is unique", passenger2.getId(), not(passenger3.getId()));
    }

    @Test
    public void testNewPassengerHasNotStartedTransportationState() {
        final int initialFloor = 1;
        final int destinationFloor = 3;
        Passenger passenger = new Passenger(initialFloor, destinationFloor);
        assertThat("Passenger has NOT_STARTED transportation started", passenger.getTransportationState(),
                is(TransportationState.NOT_STARTED));
    }


    @Test
    public void testPassengerCreationWithMovementDirectionUp() {
        final int initialFloor = 1;
        final int destinationFloor = 3;
        Passenger passenger = new Passenger(initialFloor, destinationFloor);
        assertEquals("Passenger movement direction UP", Direction.UP, passenger.getDirection());
    }

    @Test
    public void testPassengerCreationWithMovementDirectionDown() {
        final int initialStorey = 4;
        final int destinationStorey = 2;
        Passenger passenger = new Passenger(initialStorey, destinationStorey);
        assertEquals("Passenger movement direction is DOWN", Direction.DOWN, passenger.getDirection());
    }
}
