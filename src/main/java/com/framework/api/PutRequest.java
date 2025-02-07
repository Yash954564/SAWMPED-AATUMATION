package com.framework.api;

import com.framework.base.LogManager;
import com.framework.utils.api.ApiUtils;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * This class handles the PUT API requests by implementing the send()
 * method which calls RestAssured client for actual execution.
 */
public class PutRequest {
    private String endpoint; // API endpoint
    private String requestBody; // Request body
    private String contentType; // Content type of the request body
    private Map<String, String> headers; // Headers for the request
    private final ApiRequestHandler apiRequestHandler; // Instance of ApiRequestHandler


    /**
     * Constructor for creating a PutRequest with an endpoint, request body and content type.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the request.
     */
    public PutRequest(String endpoint, String requestBody) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = ApiConfig.getApiDefaultContentType(); // Default content type if not specified
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Constructor for creating a PutRequest with an endpoint, request body and content type.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the request.
     * @param contentType The content type of the body.
     */
    public PutRequest(String endpoint, String requestBody, String contentType) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Constructor for creating a PutRequest with an endpoint, request body, content type and headers.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the request.
     * @param contentType The content type of the body.
     * @param headers     Headers to be sent with the request.
     */
    public PutRequest(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.headers = headers;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Sends the PUT request.
     *
     * @return The Response object from the API call.
     * @throws ApiException If there is an exception while making the API call
     */
    @Step("Send PUT Request")
    public Response send() {
        String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // Construct the URL and remove leading slash
        LogManager.info("Sending PUT request to URL: " + url); // Log the request

        // Build request
        RequestSpecification request = RestAssured.given()
                .contentType(contentType) // set content type
                .body(requestBody);  // set request body.

        // Add headers and authentication to request builder
        if (headers != null) {
            request.headers(headers);
        }
        AuthManager.applyAuthentication(request, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Response response = null; // Initialize response variable
        try {
            response = request.put(url); // Execute the request using RestAssured
            if (response != null) {
                LogManager.info("Response status code: " + response.getStatusCode()); // Log the status code
            }
        } catch (Exception e) {
            LogManager.error("Error occurred while sending the PUT request: " + e.getMessage());
            throw new ApiException("Error occurred while sending the PUT request: " + e.getMessage(), e);
        }
        return response; // Return the response
    }

    /**
     * Sends the PUT request Asynchronously.
     *  @implNote Rest Assured does not have direct async support. Running synchronously for migration simplicity. Consider CompletableFuture for true async.
     */
    @Step("Send Asynchronous PUT Request")
    public Response sendAsync() {
        String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // Construct the URL and remove leading slash
        LogManager.info("Sending PUT request asynchronously to URL: " + url); // Log the request

        // Build request
        RequestSpecification request = RestAssured.given()
                .contentType(contentType) // set content type
                .body(requestBody);  // set request body.

        // Add headers and authentication to request builder
        if (headers != null) {
            request.headers(headers);
        }
        AuthManager.applyAuthentication(request, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Response response = null; // Initialize response variable
        try {
            response = request.put(url); // Execute the request using RestAssured
            if (response != null) {
                LogManager.info("Response status code: " + response.getStatusCode()); // Log the status code
            }
        } catch (Exception e) {
            LogManager.error("Error occurred while sending the PUT request: " + e.getMessage());
            throw new ApiException("Error occurred while sending the PUT request: " + e.getMessage(), e);
        }
        return response; // Return the response
    }
}