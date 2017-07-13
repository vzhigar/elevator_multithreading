package by.training.validators;

import by.training.beans.Building;
import by.training.beans.Floor;
import by.training.beans.Passenger;
import by.training.enums.TransportationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;


public class Validator {
    private final Building building;
    private final List<Floor> floors;
    private final int passengersNumber;
    private static final Logger LOGGER = LogManager.getLogger(Validator.class);

    public Validator(final Building building, final int passengersNumber) {
        this.building = building;
        this.floors = building.getFloors();
        this.passengersNumber = passengersNumber;
    }

    public boolean validateTransportation() {
        final boolean valid = isElevatorEmpty() && checkConditions();
        if (valid) {
            LOGGER.info("Transportation of passengers is VALID");
        } else {
            LOGGER.info("Transportation of passengers is INVALID");
        }
        return valid;
    }

    public boolean checkConditions() {
        int passengersCounter = 0;
        for (Floor floor : floors) {
            Set<Passenger> arrival = floor.getArrivalContainer();
            Set<Passenger> dispatch = floor.getDispatchContainer();
            if (!dispatch.isEmpty()) {
                LOGGER.info("Dispatch container on the floor " + floor.getNumber() + " not empty");
                return false;
            }
            for (Passenger passenger : arrival) {
                passengersCounter++;
                if (!(passenger.getTransportationState() == TransportationState.COMPLETED)) {
                    LOGGER.info("Passenger with id = " + passenger.getId() + " transportation state is "
                    + passenger.getTransportationState() + " instead of COMPLETED");
                    return false;
                }
                    if (passenger.getDestinationStory() != floor.getNumber()) {
                    LOGGER.info("Passenger with id = " + passenger.getId() + " destination floor is "
                    + passenger.getDestinationStory() + " instead of " + floor.getNumber());
                    return false;
                }
            }
        }
        return passengersCounter == passengersNumber;
    }

    public boolean isElevatorEmpty() {
        return building.getElevator().getPassengers().isEmpty();
    }
}
