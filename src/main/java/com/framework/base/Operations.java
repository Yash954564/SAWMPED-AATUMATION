package com.framework.base;


import net.datafaker.Faker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class Operations {
    private static final Faker faker = new Faker(new Locale("en-US"));
    public static String generateRandomString(){
        return  UUID.randomUUID().toString();
    }

    public static int generateRandomNumber(){
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }
    public static  String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BaseTest.getProperty("date.time.format"));
        return LocalDateTime.now().format(formatter);
    }
    public static String getFirstName(){
        return faker.name().firstName();
    }
    public static String getLastName(){
        return faker.name().lastName();
    }
    public static String getEmail(){
        return faker.internet().emailAddress();
    }
    public static String getPhoneNumber(){
        return faker.phoneNumber().phoneNumber();
    }

    public static void setSystemProperty(String key, String value){
        LogManager.info("Setting system property "+key+ " value "+value);
        System.setProperty(key, value);
    }
    public static String getSystemProperty(String key){
        LogManager.info("Getting system property "+key);
        return System.getProperty(key);
    }
}