package by.training.beans;

import by.training.enums.Direction;
import by.training.enums.TransportationState;

public class Passenger {
    private final int id;
    private final int initialStory;
    private final int destinationStory;
    private TransportationState transportationState;
    private final Direction direction;

    public Passenger(final int initialStory, final int destinationStory) {
        id = GenerateId.getInstance().getNextId();
        this.initialStory = initialStory;
        this.destinationStory = destinationStory;
        this.transportationState = TransportationState.NOT_STARTED;
        if (initialStory < destinationStory) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }
    }

    public int getId() {
        return id;
    }

    public int getInitialStory() {
        return initialStory;
    }

    public int getDestinationStory() {
        return destinationStory;
    }

    public TransportationState getTransportationState() {
        return transportationState;
    }

    public void setTransportationState(final TransportationState transportationState) {
        this.transportationState = transportationState;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) o;
        return id == passenger.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


    private static final class GenerateId {
        private static GenerateId instance;
        private static int nextId;

        private GenerateId() {

        }

        static GenerateId getInstance() {
            if (instance == null) {
                return new GenerateId();
            }
            return instance;
        }

        int getNextId() {
            return nextId++;
        }
    }

}
