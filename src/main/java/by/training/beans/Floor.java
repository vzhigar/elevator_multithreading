package by.training.beans;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Floor {
    private final int number;
    private Set<Passenger> arrivalContainer;
    private Set<Passenger> dispatchContainer;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition exitCondition = lock.newCondition();
    private final Condition enterCondition = lock.newCondition();
    private final Condition completed = lock.newCondition();

    public Floor(final int number) {
        this.number = number;
        arrivalContainer = new HashSet<>();
        dispatchContainer = new HashSet<>();
    }

    public int getNumber() {
        return number;
    }

    public Set<Passenger> getArrivalContainer() {
        return arrivalContainer;
    }

    public Set<Passenger> getDispatchContainer() {
        return dispatchContainer;
    }

    public boolean addArrivalPassenger(final Passenger passenger) {
        return arrivalContainer.add(passenger);
    }

    public boolean addDispatchPassenger(final Passenger passenger) {
        return dispatchContainer.add(passenger);
    }

    public boolean removeDispatchPassenger(final Passenger passenger) {
        return dispatchContainer.remove(passenger);
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public Condition getEnterCondition() {
        return enterCondition;
    }

    public Condition getExitCondition() {
        return exitCondition;
    }

    public Condition getCompleted() {
        return completed;
    }

}
