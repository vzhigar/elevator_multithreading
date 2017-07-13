package by.training.beans;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private final int storiesNumber;
    private final List<Floor> floors;
    private final Elevator elevator;

    public Building(final int storiesNumber, final int elevatorCapacity) {
        this.storiesNumber = storiesNumber;
        floors = new ArrayList<>(storiesNumber);
        initFloors();
        this.elevator = new Elevator(floors, elevatorCapacity);
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public Elevator getElevator() {
        return elevator;
    }

    private void initFloors() {
        for (int i = 1; i <= storiesNumber; i++) {
            Floor floor = new Floor(i);
            floors.add(floor);
        }
    }

}
