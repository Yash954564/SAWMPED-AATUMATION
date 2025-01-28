package com.framework.api;

import com.framework.base.BaseTest;
import com.framework.base.LogManager;

/**
 * This class is responsible for loading API related configurations
 * from the properties file.
 */
public class ApiConfig {

    // Constants for property keys
    private static final String API_BASE_URL = "api.base.url";
    private static final String API_DEFAULT_CONTENT_TYPE = "api.default.content.type";
    private static final String API_REQUEST_TIMEOUT = "api.request.timeout";
    private static final String API_RESPONSE_TIMEOUT = "api.response.timeout";
    private static final String API_AUTH_TYPE = "api.auth.type";

    // Instance of BaseTest to get properties from property file.
    private static final BaseTest baseTest = new BaseTest();

    /**
     * Retrieves the API base URL from the properties file.
     *
     * @return The API base URL as a String.
     */
    public static String getApiBaseUrl() {
        String url = baseTest.getProperty(API_BASE_URL); // Get value from properties
        LogManager.info("API Base URL: " + url); // Log the value
        return url; // Return the value
    }

    /**
     * Retrieves the default content type for API requests.
     *
     * @return The default content type as a String.
     */
    public static String getApiDefaultContentType() {
        String contentType = baseTest.getProperty(API_DEFAULT_CONTENT_TYPE); // Get value from properties
        LogManager.info("API Default Content Type: " + contentType); // Log the value
        return contentType; // Return the value
    }

    /**
     * Retrieves the request timeout for API calls.
     *
     * @return The request timeout as an integer in seconds.
     */
    public static int getApiRequestTimeout() {
        String timeout = baseTest.getProperty(API_REQUEST_TIMEOUT); // Get value from properties
        LogManager.info("API Request timeout: " + timeout); // Log the value
        try {
            return  Integer.parseInt(timeout); // Return the value after parsing
        }catch (NumberFormatException e){
            LogManager.error("Error while parsing the API request timeout "+timeout+ " "+e.getMessage());
            throw new RuntimeException("Error while parsing the API request timeout "+timeout+ " "+e.getMessage(), e);
        }

    }

    /**
     * Retrieves the response timeout for API calls.
     *
     * @return The response timeout as an integer in seconds.
     */
    public static int getApiResponseTimeout() {
        String timeout = baseTest.getProperty(API_RESPONSE_TIMEOUT); // Get value from properties
        LogManager.info("API Response timeout: " + timeout); // Log the value
        try {
            return  Integer.parseInt(timeout); // Return the value after parsing
        }catch (NumberFormatException e){
            LogManager.error("Error while parsing the API response timeout "+timeout+ " "+e.getMessage());
            throw new RuntimeException("Error while parsing the API response timeout "+timeout+ " "+e.getMessage(), e);
        }
    }

    /**
     * Retrieves the authentication type for API calls.
     *
     * @return The authentication type as a string.
     */
    public static String getApiAuthType() {
        String authType = baseTest.getProperty(API_AUTH_TYPE); // Get value from properties
        LogManager.info("API Authentication Type: " + authType); // Log the value
        return authType; // Return the value
    }
}