package com.framework.base;


import org.testng.annotations.BeforeSuite;
import java.util.Properties;


public class BaseTest {
    protected static Properties properties;
    protected static PropertiesFileReader propertiesFileReader = new PropertiesFileReader();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite(){
        String environment = System.getProperty("env") !=null ? System.getProperty("env"): "qa";
        String filePath = "src/main/resources/configuration/environments/" + environment + "/" + environment + ".properties";
        properties = propertiesFileReader.loadProperties(filePath);
        LogManager.info("Properties loaded from: " + filePath);
        properties.forEach((key, value)->LogManager.info(key + ":" +value));
    }


    public static String getProperty(String key){
        String value = properties.getProperty(key);
        if (value == null){
            LogManager.error("Property Not found for the key "+ key);
            throw new RuntimeException("Property Not found for the key "+ key);
        }
        return value;
    }

}