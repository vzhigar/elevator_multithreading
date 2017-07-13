package by.training.tasks;

import by.training.beans.Building;
import by.training.beans.Elevator;
import by.training.beans.Passenger;
import by.training.beans.Floor;
import by.training.controllers.Controller;
import by.training.enums.TransportationState;

public class TransportationTask implements Runnable {
    private final Passenger passenger;
    private final Controller controller;
    private final Elevator elevator;
    private final Floor initialFloor;
    private final Floor destinationFloor;

    public TransportationTask(final Passenger passenger, final Building building, final Controller controller) {
        this.passenger = passenger;
        this.initialFloor = building.getFloors().get(passenger.getInitialStory() - 1);
        this.destinationFloor = building.getFloors().get(passenger.getDestinationStory() - 1);
        this.elevator = building.getElevator();
        this.controller = controller;
    }

    @Override
    public void run() {

        try {
            boolean completed = false;
            while (!completed) {
                initialFloor.getLock().lock();
                try {
                    initialFloor.getEnterCondition().await();
                    if (passenger.getDirection() == elevator.getDirection()) {
                        completed = controller.enter(passenger);
                        passenger.setTransportationState(TransportationState.IN_PROGRESS);
                    }
                    initialFloor.getCompleted().signal();
                } finally {
                    initialFloor.getLock().unlock();
                }
            }
            destinationFloor.getLock().lock();
            try {
                destinationFloor.getExitCondition().await();
                controller.exit(passenger);
                passenger.setTransportationState(TransportationState.COMPLETED);
                destinationFloor.getCompleted().signal();
            } finally {
                destinationFloor.getLock().unlock();
            }

        } catch (InterruptedException e) {
            passenger.setTransportationState(TransportationState.ABORTED);
        }
    }

}
