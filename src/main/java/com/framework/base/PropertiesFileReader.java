package com.framework.base;

import io.qameta.allure.Step;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class handles reading properties from a .properties file.
 */
public class PropertiesFileReader {
    private Properties properties; // Properties object to hold configuration data

    /**
     * Constructor to initialize the properties
     */
    public PropertiesFileReader() {
        properties = new Properties(); // Initialize properties
    }

    /**
     * Loads properties from a file.
     *
     * @param filePath The path to the properties file.
     * @return The properties object containing the loaded data.
     * @throws RuntimeException If there is an error while loading the properties file.
     */
    @Step("Load properties from file: {0}")
    public Properties loadProperties(String filePath) {
        InputStream input = null; // Input stream for the file
        try {
            LogManager.info("Loading properties from file: " + filePath); // Log file path from where properties are loading
            input = new FileInputStream(filePath); // Read file as an input stream
            if (input == null) {
                LogManager.error("Unable to find property file at: " + filePath); // Log the error
                throw new IOException("Unable to find property file at: " + filePath); // Throw exception if file not found
            }

            properties.load(input); // Load the file to the properties object
            LogManager.info("Properties file loaded successfully."); // Log the successful loading of the file

        } catch (IOException ex) {
            LogManager.error("Error loading properties file: " + filePath, ex); // Log the error
            throw new RuntimeException("Error loading properties file: " + filePath, ex); // Throw exception if error occurred
        } finally {
            if (input != null) { // Close the input stream
                try {
                    input.close(); // Close input stream
                } catch (IOException e) {
                    LogManager.error("Error closing input stream " + e.getMessage()); // Log the error while closing
                    throw new RuntimeException("Error closing input stream " + e.getMessage(), e); // Throw exception if unable to close stream
                }
            }
        }
        return properties; // return the loaded properties object.
    }

    /**
     * Gets a property value based on the property key.
     *
     * @param key The key of the property to get.
     * @return The value of the property if the key is valid
     * @throws RuntimeException If the key is not found in the file.
     */
    @Step("Get property from the file for key: {0}")
    public String getProperty(String key) {
        String value = properties.getProperty(key); // get the property by key from properties object
        if (value == null) {
            LogManager.error("Property not found for the key " + key); // Log the error
            throw new RuntimeException("Property not found for the key " + key); // Throw the exception if property not found.
        }
        return value; // Return the property if found.
    }
}