package com.framework.base;

import io.qameta.allure.Step;
import net.datafaker.Faker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * This class provides general utility methods like string generation,
 * number generation and date time. It uses DataFaker to generate random data.
 */
public class Operations {
    private static final Faker faker = new Faker(new Locale("en-US")); // Instance of faker to generate fake data
    /**
     * Generates a random UUID string.
     *
     * @return A random UUID string.
     */
    @Step("Generate Random String")
    public static String generateRandomString() {
        return UUID.randomUUID().toString(); // Generates and returns random string
    }

    /**
     * Generates a random string in the format "User123@".
     *
     * @return A random string in the format "User123@".
     */
    @Step("Generate Random User String")
    public static String generateRandomUserString() {
        String prefix = "User";
        Random random = new Random();
        int number = random.nextInt(900) + 100;  // Generates a 3-digit number
        char specialChar = (char) (random.nextInt(15) + 33);  // Generates a special character between '!' and '/'

        return prefix + number + specialChar;
    }

    /**
     * Generates a random integer value.
     *
     * @return A random integer value.
     */
    @Step("Generate Random Number")
    public static int generateRandomNumber() {
        Random random = new Random(); // Instance of random class
        return random.nextInt(Integer.MAX_VALUE); // Generates and returns a random integer
    }

    /**
     * Returns current date and time string using configured format.
     *
     * @return current date and time string.
     */
    @Step("Get Current Date and Time")
    public static String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BaseTest.getProperty("date.time.format")); // Get the date time pattern
        return LocalDateTime.now().format(formatter); // Gets the current date time in specified format
    }

    /**
     * Generates a random first name.
     *
     * @return A random first name.
     */
    @Step("Get Random First Name")
    public static String getFirstName() {
        return faker.name().firstName(); // generate random first name
    }

    /**
     * Generates a random last name.
     *
     * @return A random last name.
     */
    @Step("Get Random Last Name")
    public static String getLastName() {
        return faker.name().lastName(); // generate random last name
    }

    /**
     * Generates a random email address.
     *
     * @return A random email address.
     */
    @Step("Get Random Email")
    public static String getEmail() {
        return faker.internet().emailAddress(); // generate random email
    }

    /**
     * Generates a random phone number.
     *
     * @return A random phone number.
     */
    @Step("Get Random Phone Number")
    public static String getPhoneNumber() {
        return faker.phoneNumber().phoneNumber(); // generate random phone number
    }

    /**
     * Sets a system property with the given key and value.
     *
     * @param key   The key of the property to set.
     * @param value The value of the property to set.
     */
    @Step("Set System property: {0}, Value: {1}")
    public static void setSystemProperty(String key, String value) {
        LogManager.info("Setting system property " + key + " value " + value); // Log that system property is setting
        System.setProperty(key, value); // Set system property
    }

    /**
     * Gets a system property using the given key.
     *
     * @param key The key of the property to get.
     * @return The value of the system property.
     */
    @Step("Get System property: {0}")
    public static String getSystemProperty(String key) {
        LogManager.info("Getting system property " + key); // Log that system property is getting
        return System.getProperty(key); // Get and return system property
    }
}