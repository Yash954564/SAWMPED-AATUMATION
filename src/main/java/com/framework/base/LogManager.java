package com.framework.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  This class manages logging for the entire framework by using SLF4J.
 */
public class LogManager {

    private static Logger logger; // Logger instance

    /**
     * Initialize the logger by using given class name.
     * @param className class name to initialize the logger.
     */
    public static void initialize(Class<?> className) {
        logger = LoggerFactory.getLogger(className);  // Initialize the logger for the given class name
    }

    /**
     * Method to get logger instance, also checks if logger is null or not,
     * if null it will initialize the logger.
     * @return Logger instance
     */
    public static Logger getLogger(){
        if (logger==null){ // Check if logger is null
            initialize(LogManager.class); // Initialize logger if null
        }
        return logger; // Return the logger instance
    }

    /**
     * Logs an informational message.
     *
     * @param message The message to log.
     */
    public static void info(String message) {
        getLogger().info(message); // Log info message
    }

    /**
     * Logs a debug message.
     *
     * @param message The message to log.
     */
    public static void debug(String message) {
        getLogger().debug(message); // Log debug message
    }

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     */
    public static void error(String message) {
        getLogger().error(message); // Log error message
    }

    /**
     * Logs an error message along with a throwable.
     *
     * @param message The message to log.
     * @param t       The throwable to log.
     */
    public static void error(String message, Throwable t) {
        getLogger().error(message, t); // Log error message with throwable
    }

    /**
     * Logs a warning message.
     *
     * @param message The message to log.
     */
    public static void warn(String message) {
        getLogger().warn(message); // Log warn message
    }
}