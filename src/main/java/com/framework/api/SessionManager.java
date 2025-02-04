package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages session values during API testing.
 * It provides methods to store, retrieve, and clear session data.
 */
public class SessionManager {

    // Map to store session values
    private static final Map<String, String> session = new HashMap<>();

    /**
     * Sets a session value.
     *
     * @param key   The key of the session value.
     * @param value The value to be set.
     */
    @Step("Set session value for key: {0}")
    public static void setSessionValue(String key, String value) {
        LogManager.info("Setting the session value " + key + " value " + value); // Log the action
        session.put(key, value); // Store the session value
    }

    /**
     * Retrieves a session value.
     *
     * @param key The key of the session value.
     * @return The value of the session variable.
     * @throws RuntimeException If the session value is not found.
     */
    @Step("Get session value for key: {0}")
    public static String getSessionValue(String key) {
        String value = session.get(key); // Retrieve session value
        if (value == null) {
            LogManager.error("Session value not found for the key " + key); // Log the error
            throw new RuntimeException("Session value not found for the key " + key); // Throw exception if session value is not found
        }
        LogManager.info("Getting the session value " + key + " value " + value); // Log the action
        return value; // Return session value
    }

    /**
     * Removes a session value.
     *
     * @param key The key of the session value to be removed.
     */
    @Step("Remove session value for key: {0}")
    public static void removeSessionValue(String key) {
        LogManager.info("Removing the session value " + key); // Log the action
        session.remove(key); // Remove the session value
    }

    /**
     * Clears all session values.
     */
    @Step("Clear all session values")
    public static void clearSession() {
        LogManager.info("Clearing all the session values."); // Log the action
        session.clear(); // Clear all the session values
    }
}