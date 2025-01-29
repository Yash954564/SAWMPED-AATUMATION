package com.framework.utils.validation;

import com.framework.base.LogManager;
import org.testng.Assert;

public class AssertionUtils {

    /**
     * Asserts that two objects are equal. Logs the assertion.
     * @param actual The actual value.
     * @param expected The expected value.
     * @param message The message to log for this assertion.
     */
    public static void assertEquals(Object actual, Object expected, String message) {
        LogManager.info("Asserting that " + message + ". Expected: " + expected + ", Actual: " + actual);
        Assert.assertEquals(actual, expected, message);
    }

    /**
     * Asserts that a condition is true.
     * @param condition The condition to assert
     * @param message The message to log for this assertion.
     */
    public static void assertTrue(boolean condition, String message) {
        LogManager.info("Asserting that " + message + ". Condition: " + condition);
        Assert.assertTrue(condition, message);
    }

    /**
     * Asserts that a condition is false.
     * @param condition The condition to assert
     * @param message The message to log for this assertion.
     */
    public static void assertFalse(boolean condition, String message) {
        LogManager.info("Asserting that " + message + ". Condition: " + condition);
        Assert.assertFalse(condition, message);
    }

    /**
     * Asserts that an object is not null.
     * @param actual The object to assert
     * @param message The message to log for this assertion.
     */
    public static void assertNotNull(Object actual, String message) {
        LogManager.info("Asserting that " + message + ". Actual is not null");
        Assert.assertNotNull(actual, message);
    }

}