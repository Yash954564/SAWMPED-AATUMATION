package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;
import okhttp3.Response;
import java.io.IOException;
import java.util.Objects;

/**
 * This interface defines a contract for handling the response after an API call using a script.
 * It provides default implementations to set variables from the response and to read the response body.
 */
public interface PostRequestScript {

    /**
     * Method to handle the response.
     *
     * @param response The API response.
     */
    void execute(Response response);

    /**
     * Default method to set the response body and response code as API variables.
     *
     * @param response The API response.
     */
    @Step("Set Response variables")
    default void setVariables(Response response) {
        if (Objects.nonNull(response)) {
            try {
                String responseBody = Objects.requireNonNull(response.body()).string(); // Get the response body as string
                LogManager.info("Setting variable from the response body : " + responseBody); // Log the action
                ApiVariableManager.setVariable("responseBody", responseBody);  // Set the response body
                ApiVariableManager.setVariable("statusCode", String.valueOf(response.code())); // Set the status code
            } catch (IOException e) {
                LogManager.error("Error while reading the response body " + e.getMessage());
                throw new RuntimeException("Error while reading the response body " + e.getMessage(), e);
            }
        }
    }

    /**
     * Default method to get the response body as a string.
     *
     * @param response The API response.
     * @return The response body as a string.
     * @throws RuntimeException If there's an error reading the response body.
     */
    @Step("Get Response")
    default String getResponse(Response response) {
        try {
            return Objects.requireNonNull(response.body()).string(); // Get the response body as string
        } catch (Exception e) {
            LogManager.error("Error while reading the response body " + e.getMessage());
            throw new RuntimeException("Error while reading the response body " + e.getMessage(), e);
        }
    }
}