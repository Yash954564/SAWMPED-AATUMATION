package com.framework.api;

import com.framework.base.LogManager;
import java.util.HashMap;
import java.util.Map;

public class ApiVariableManager {

    private static Map<String, String> apiVariables = new HashMap<>();

    public static void setVariable(String key, String value) {
        LogManager.info("Setting API variable. Key: " + key + ", Value: " + value);
        apiVariables.put(key, value);
    }

    public static String getVariable(String key) {
        String value = apiVariables.get(key);
        if (value == null){
            LogManager.error("API variable not found for key: " + key);
            throw new RuntimeException("API variable not found for key: " + key);
        }
        LogManager.info("Getting API variable. Key: " + key + ", Value: " + value);
        return value;
    }

    public static void removeVariable(String key){
        LogManager.info("Removing API variable. Key: " + key);
        apiVariables.remove(key);
    }

    public static void clearVariables(){
        LogManager.info("Clearing all API variables.");
        apiVariables.clear();
    }
}