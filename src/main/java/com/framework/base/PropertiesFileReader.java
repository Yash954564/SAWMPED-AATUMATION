package com.framework.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileReader {
    private Properties properties;

    public PropertiesFileReader() {
        properties = new Properties();
    }

    public Properties loadProperties(String filePath) {
        InputStream input = null;
        try {
            LogManager.info("Loading properties from file: " + filePath);
            input = new FileInputStream(filePath);
            if (input == null) {
                LogManager.error("Unable to find property file at: " + filePath);
                throw new IOException("Unable to find property file at: " + filePath);
            }

            properties.load(input);
            LogManager.info("Properties file loaded successfully.");

        } catch (IOException ex) {
            LogManager.error("Error loading properties file: " + filePath, ex);
            throw new RuntimeException("Error loading properties file: " + filePath, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LogManager.error("Error closing input stream "+e.getMessage());
                    throw new RuntimeException("Error closing input stream "+e.getMessage());
                }
            }
        }
        return properties;
    }

    public String getProperty(String key){
        String value = properties.getProperty(key);
        if (value == null){
            LogManager.error("Property not found for the key "+ key);
            throw new RuntimeException("Property not found for the key "+ key);
        }
        return value;
    }

}