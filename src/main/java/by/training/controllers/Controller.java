package by.training.controllers;

import by.training.beans.Building;
import by.training.beans.Elevator;
import by.training.beans.Floor;
import by.training.beans.Passenger;
import by.training.constants.StringConstants;
import by.training.enums.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Controller {
    private final Elevator elevator;
    private final List<Floor> floors;
    private Action action;
    private Floor currentFloor;
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public Controller(final Building building) {
        elevator = building.getElevator();
        floors = building.getFloors();
        currentFloor = elevator.getCurrentFloor();
    }

    public void nextFloor() {
        action = Action.MOVING_ELEVATOR;
        int from = elevator.getCurrentFloor().getNumber();
        elevator.move();
        int to = elevator.getCurrentFloor().getNumber();
        currentFloor = elevator.getCurrentFloor();
        LOGGER.info(action + StringConstants.FROM + from + StringConstants.TO + to);
    }

    public void deboarding() {
        action = Action.DEBOARDING_OF_PASSENGER;
        int count = 0;
        for (Passenger passenger : elevator.getPassengers()) {
            if (currentFloor.getNumber() == passenger.getDestinationStory()) {
                count++;
            }
        }
        if (count > 0) {
            currentFloor.getLock().lock();
            try {
                currentFloor.getExitCondition().signalAll();
                try {
                    currentFloor.getCompleted().await();
                } catch (InterruptedException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }
            } finally {
                currentFloor.getLock().unlock();
            }
        }
    }

    public void boarding() {
        action = Action.BOARDING_OF_PASSENGER;
        int count = 0;
        for (Passenger passenger : currentFloor.getDispatchContainer()) {
            if (currentFloor.getNumber() == passenger.getInitialStory()) {
                count++;
            }
        }
        if (count > 0) {
            currentFloor.getLock().lock();
            try {
                currentFloor.getEnterCondition().signalAll();
                try {
                    currentFloor.getCompleted().await();
                } catch (InterruptedException e) {
                    LOGGER.info(e.getMessage());
                    e.printStackTrace();
                }
            } finally {
                currentFloor.getLock().unlock();
            }
        }
    }

    public boolean enter(final Passenger passenger) {
        final boolean result = elevator.addPassenger(passenger);
        if (result) {
            currentFloor.removeDispatchPassenger(passenger);
            LOGGER.info(action + StringConstants.WHITESPACE + passenger.getId()
                    + StringConstants.ON_STORY + passenger.getInitialStory());
        }
        return result;
    }

    public void exit(final Passenger passenger) {
        elevator.removePassenger(passenger);
        currentFloor.addArrivalPassenger(passenger);
        LOGGER.info(action + StringConstants.WHITESPACE + passenger.getId()
                + StringConstants.ON_STORY + passenger.getDestinationStory());
    }

    public void run() {
        action = Action.STARTING_TRANSPORTATION;
        LOGGER.info(action);
        while (!transportationIsFinished()) {
            deboarding();
            if (transportationIsFinished()) {
                action = Action.COMPLETION_TRANSPORTATION;
                LOGGER.info(action);
                return;
            }
            boarding();
            nextFloor();
        }
    }

    public boolean transportationIsFinished() {
        if (!elevator.getPassengers().isEmpty()) {
            return false;
        }
        for (Floor floor : floors) {
            if (!floor.getDispatchContainer().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
