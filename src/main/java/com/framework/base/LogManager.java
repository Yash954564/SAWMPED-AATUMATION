package com.framework.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogManager {

    private static Logger logger;


    public static void initialize(Class<?> className) {
        logger = LoggerFactory.getLogger(className);
    }

    public static Logger getLogger(){
        if (logger==null){
            initialize(LogManager.class);
        }
        return logger;
    }

    public static void info(String message) {
        getLogger().info(message);
    }

    public static void debug(String message) {
        getLogger().debug(message);
    }

    public static void error(String message) {
        getLogger().error(message);
    }
    public static void error(String message, Throwable t) {
        getLogger().error(message, t);
    }

    public static void warn(String message) {
        getLogger().warn(message);
    }
}