package by.training.readers;


import by.training.constants.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private int storiesNumber;
    private int elevatorCapacity;
    private int passengersNumber;
    private String configFileName;
    private static final Logger LOGGER = LogManager.getLogger(ConfigReader.class);

    public ConfigReader(final String configFileName) {
        this.configFileName = configFileName;
        readConfigFile();
    }

    public int getStoriesNumber() {
        return storiesNumber;
    }

    public int getElevatorCapacity() {
        return elevatorCapacity;
    }

    public int getPassengersNumber() {
        return passengersNumber;
    }

    private void readConfigFile() {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configFileName);
            properties.load(inputStream);
            storiesNumber = Integer.parseInt(properties.getProperty(StringConstants.STORIES_NUMBER));
            elevatorCapacity = Integer.parseInt(properties.getProperty(StringConstants.ELEVATOR_CAPACITY));
            passengersNumber = Integer.parseInt(properties.getProperty(StringConstants.PASSENGERS_NUMBER));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage() + " parameter value must be a number");
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("File " + configFileName + " not found");
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
