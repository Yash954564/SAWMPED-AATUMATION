package com.framework.api;

import com.framework.base.LogManager;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, String> session = new HashMap<>();
    public static void setSessionValue(String key, String value){
        LogManager.info("Setting the session value "+key+" value "+value);
        session.put(key, value);
    }

    public static String getSessionValue(String key){
        String value = session.get(key);
        if (value ==null){
            LogManager.error("Session value not found for the key " +key);
            throw new RuntimeException("Session value not found for the key "+ key);
        }
        LogManager.info("Getting the session value "+key +" value "+value);
        return value;
    }

    public static void removeSessionValue(String key){
        LogManager.info("Removing the session value "+key);
        session.remove(key);
    }
    public static void clearSession(){
        LogManager.info("Clearing all the session values.");
        session.clear();
    }
}