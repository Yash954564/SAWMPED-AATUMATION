package com.framework.base;

import org.testng.annotations.BeforeSuite;
import java.util.Properties;

/**
 * Base test class for setting up the test environment and loading properties.
 */
public class BaseTest {
    protected static Properties properties; // Properties object to hold configuration data
    protected static PropertiesFileReader propertiesFileReader = new PropertiesFileReader(); // Properties file reader instance

    public BaseTest(){
        // Determine the environment (default to "qa" if not provided)
        String environment = System.getProperty("env") != null ? System.getProperty("env") : "qa";
        // Construct file path to load properties
        String filePath = "src/main/resources/configuration/environments/" + environment + "/" + environment + ".properties";
        // Load properties from the file
        properties = propertiesFileReader.loadProperties(filePath);
        // Log that properties are loaded from this file
        LogManager.info("Properties loaded from: " + filePath);
        // Log all the loaded properties
        properties.forEach((key, value) -> LogManager.info(key + ":" + value));
    }

    /**
     * Gets a property from the loaded properties.
     *
     * @param key The key of the property to get.
     * @return The value of the property.
     * @throws RuntimeException if the property is not found.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key); // Get the property by its key
        if (value == null) {
            LogManager.error("Property Not found for the key " + key); // Log error if the property doesn't exist
            throw new RuntimeException("Property Not found for the key " + key); // Throw exception if property is not found
        }
        return value; // Return the value of the property
    }
}