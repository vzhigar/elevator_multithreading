package by.training;

import by.training.beans.Building;
import by.training.beans.Passenger;
import by.training.constants.StringConstants;
import by.training.controllers.Controller;
import by.training.readers.ConfigReader;
import by.training.tasks.TransportationTask;
import by.training.utils.UniquePairNumbersGenerator;
import by.training.validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Runner {

    public static void main(String[] args) throws Exception {
        final String fileName = StringConstants.CONFIG_FILE;
        ConfigReader reader = new ConfigReader(fileName);
        final Building building = new Building(reader.getStoriesNumber(), reader.getElevatorCapacity());
        final Controller controller = new Controller(building);
        List<TransportationTask> tasks = new ArrayList<>();
        UniquePairNumbersGenerator generator = new UniquePairNumbersGenerator(reader.getStoriesNumber());

        for (int i = 0; i < reader.getPassengersNumber(); i++) {
            int[] pair = generator.getPair();
            int initialFloor = pair[0];
            int destinationFloor = pair[1];
            Passenger passenger = new Passenger(initialFloor, destinationFloor);
            TransportationTask transportationTask = new TransportationTask(passenger, building, controller);
            tasks.add(transportationTask);
            int floorIndex = initialFloor - 1;
            building.getFloors().get(floorIndex).addDispatchPassenger(passenger);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(reader.getPassengersNumber());
        for (TransportationTask transportationTask : tasks) {
            executorService.execute(transportationTask);
        }
        controller.run();

        Validator validator = new Validator(building, reader.getPassengersNumber());
        validator.validateTransportation();
        executorService.shutdown();

    }
}