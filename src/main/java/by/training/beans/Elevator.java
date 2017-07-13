package by.training.beans;

import by.training.enums.Direction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elevator {
    private final List<Floor> floors;
    private final int capacity;
    private int currentFloorIndex;
    private Floor currentFloor;
    private Set<Passenger> passengers;
    private Direction direction;

    public Elevator(final List<Floor> floors, final int capacity) {
        this.floors = floors;
        this.capacity = capacity;
        this.currentFloorIndex = 0;
        this.currentFloor = floors.get(currentFloorIndex);
        this.passengers = new HashSet<>(capacity);
        this.direction = Direction.UP;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean addPassenger(final Passenger passenger) {
            return !isFull() && passengers.add(passenger);
    }

    public void removePassenger(final Passenger passenger) {
        passengers.remove(passenger);
    }

    public void move() {
        final int firstFloorIndex = 0;
        final int lastFloorIndex = floors.size() - 1;
        if (direction == Direction.UP) {
            nextFloor();
        }
        if (direction == Direction.DOWN) {
            previousFloor();
        }
        if (currentFloorIndex == firstFloorIndex) {
            direction = Direction.UP;
        }
        if (currentFloorIndex == lastFloorIndex) {
            direction = Direction.DOWN;
        }
    }

    public boolean isFull() {
        return passengers.size() == capacity;
    }

    public void nextFloor() {
        currentFloorIndex++;
        currentFloor = floors.get(currentFloorIndex);
    }

    public void previousFloor() {
        currentFloorIndex--;
        currentFloor = floors.get(currentFloorIndex);
    }

}
