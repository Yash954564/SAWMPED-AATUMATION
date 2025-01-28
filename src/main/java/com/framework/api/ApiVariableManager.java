package com.framework.api;

import com.framework.base.LogManager;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages API variables by storing and retrieving them.
 * These variables can be used in subsequent requests to perform data transfer
 * during API calls.
 */
public class ApiVariableManager {

    // Map to store API variables
    private static final Map<String, String> apiVariables = new HashMap<>();

    /**
     * Sets a variable in the API variables map.
     *
     * @param key   The name of the variable.
     * @param value The value of the variable.
     */
    public static void setVariable(String key, String value) {
        LogManager.info("Setting API variable. Key: " + key + ", Value: " + value); // Log the action
        apiVariables.put(key, value); // Store variable in the map
    }

    /**
     * Retrieves the value of a variable from the API variables map.
     *
     * @param key The name of the variable.
     * @return The value of the variable as a String.
     * @throws RuntimeException If the variable is not found.
     */
    public static String getVariable(String key) {
        String value = apiVariables.get(key); // Get the value from the map
        if (value == null) {
            LogManager.error("API variable not found for key: " + key); // Log the error
            throw new RuntimeException("API variable not found for key: " + key); // Throw exception if variable is not found
        }
        LogManager.info("Getting API variable. Key: " + key + ", Value: " + value); // Log the action
        return value; // Return the variable value
    }

    /**
     * Removes a variable from the API variables map.
     *
     * @param key The name of the variable to remove.
     */
    public static void removeVariable(String key) {
        LogManager.info("Removing API variable. Key: " + key); // Log the action
        apiVariables.remove(key); // Remove the variable from map
    }

    /**
     * Clears all variables from the API variables map.
     */
    public static void clearVariables() {
        LogManager.info("Clearing all API variables."); // Log the action
        apiVariables.clear(); // Clear all the variables
    }
}