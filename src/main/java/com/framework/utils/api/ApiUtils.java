package com.framework.utils.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.api.ApiVariableManager;
import com.framework.base.LogManager;
import okhttp3.Response;
import java.io.IOException;
import java.util.Objects;

/**
 * This class provides utility methods for handling API responses and related tasks.
 */
public class ApiUtils {

    /**
     * Extracts the response body as a String.
     *
     * @param response The Response object from an API call.
     * @return The response body as a String.
     * @throws RuntimeException if the response or response body is null or if an IOException occurs.
     */
    public static String getResponseBody(Response response) {
        if (response != null && response.body() != null) {
            try {
                String body = Objects.requireNonNull(response.body()).string();
                LogManager.info("Response Body : " + body); // Log response body
                return body; // Return response body
            } catch (IOException e) {
                LogManager.error("Error while reading the response body " + e.getMessage());
                throw new RuntimeException("Error while reading the response body " + e.getMessage(), e);
            }
        } else {
            LogManager.error("Response or Response body is null"); // Log the error
            throw new RuntimeException("Response or Response body is null");
        }
    }

    /**
     * Extracts the status code from the Response.
     *
     * @param response The Response object from an API call.
     * @return The HTTP status code as an integer.
     * @throws RuntimeException If the response is null.
     */
    public static int getStatusCode(Response response) {
        if (response != null) {
            int code = response.code(); // Get response status code
            LogManager.info("Status Code : " + code); // Log status code
            return code; // Return status code
        } else {
            LogManager.error("Response is null, Unable to get status code"); // Log error message
            throw new RuntimeException("Response is null, Unable to get status code");
        }
    }

    /**
     * Converts the JSON response body to a JsonNode object.
     *
     * @param response The Response object from an API call.
     * @return The JsonNode representing the JSON response, or null if the response body is empty or null.
     * @throws RuntimeException If the response is null or there is an error during JSON processing.
     */
    public static JsonNode getJsonNodeFromResponse(Response response) {
        if (response == null) {
            LogManager.error("Response is null, Unable to get json node"); // Log the error
            throw new RuntimeException("Response is null, Unable to get json node");
        }
        ObjectMapper objectMapper = new ObjectMapper(); // Initialize object mapper
        try {
            String responseBody = getResponseBody(response); // Get response body
            if (responseBody == null || responseBody.isEmpty()) {
                LogManager.warn("Response body is empty or null"); // Log the warning
                return null;
            }
            return objectMapper.readTree(responseBody); // Convert the response body to JsonNode
        } catch (JsonProcessingException e) {
            LogManager.error("Error while processing the json response " + e.getMessage());
            throw new RuntimeException("Error while processing the json response " + e.getMessage(), e);
        }
    }

    /**
     * Sets a response variable in API variable manager.
     *
     * @param response The Response object from an API call.
     * @param key      The key for the variable.
     * @throws RuntimeException If the response is null.
     */
    public static void setResponseVariable(Response response, String key) {
        if (response == null) {
            LogManager.error("Response is null, Unable to set response variable"); // Log the error
            throw new RuntimeException("Response is null, Unable to set response variable");
        }
        String value = getResponseBody(response); // Get response body
        if (value != null) {
            ApiVariableManager.setVariable(key, value); // Set the variable in api variable manager
        }
    }
}