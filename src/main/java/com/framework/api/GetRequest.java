package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * This class handles the GET API requests by implementing the send()
 * method which calls RestAssured client for actual execution.
 */
public class GetRequest {
    private String endpoint; // API endpoint
    private Map<String, String> headers; // Headers for the request
    private final ApiRequestHandler apiRequestHandler; // Instance of ApiRequestHandler


    /**
     * Constructor for creating a GetRequest with an endpoint.
     *
     * @param endpoint The API endpoint.
     */
    public GetRequest(String endpoint) {
        this.endpoint = endpoint;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Constructor for creating a GetRequest with an endpoint and headers.
     *
     * @param endpoint The API endpoint.
     * @param headers  Headers to be sent with the request.
     */
    public GetRequest(String endpoint, Map<String, String> headers) {
        this.endpoint = endpoint;
        this.headers = headers;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Sends the GET request.
     *
     * @return The Response object from the API call.
     * @throws ApiException If there is an exception while making the api call
     */
    @Step("Send GET Request")
    public Response send() {
        String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // Construct the URL and remove leading slash
        LogManager.info("Sending GET request to URL: " + url); // Log the request

        // Build request
        RequestSpecification request = RestAssured.given(); // Initialize RestAssured request

        // Add headers
        if (headers != null) {
            request.headers(headers);
        }
        AuthManager.applyAuthentication(request, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Response response = null; // Initialize response variable
        try {
            response = request.get(url); // Execute the request using RestAssured
            if (response != null) {
                LogManager.info("Response status code: " + response.getStatusCode()); // Log the status code
            }
        } catch (Exception e) {
            LogManager.error("Error occurred while sending the GET request: " + e.getMessage());
            throw new ApiException("Error occurred while sending the GET request: " + e.getMessage(), e);
        }
        return response; // Return the response
    }


    /**
     * Sends the GET request Asynchronously.
     *   @implNote Rest Assured does not have direct async support. Running synchronously for migration simplicity. Consider CompletableFuture for true async.
     */
    @Step("Send Asynchronous GET Request")
    public Response sendAsync() {
        String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // Construct the URL and remove leading slash
        LogManager.info("Sending GET request to URL: " + url); // Log the request

        // Build request
        RequestSpecification request = RestAssured.given(); // Initialize RestAssured request

        // Add headers
        if (headers != null) {
            request.headers(headers);
        }
        AuthManager.applyAuthentication(request, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Response response = null; // Initialize response variable
        try {
            response = request.get(url); // Execute the request using RestAssured
            if (response != null) {
                LogManager.info("Response status code: " + response.getStatusCode()); // Log the status code
            }
        } catch (Exception e) {
            LogManager.error("Error occurred while sending the GET request: " + e.getMessage());
            throw new ApiException("Error occurred while sending the GET request: " + e.getMessage(), e);
        }
        return response; // Return the response
    }
}