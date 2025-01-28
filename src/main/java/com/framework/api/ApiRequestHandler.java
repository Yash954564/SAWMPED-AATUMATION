package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class handles the API requests by using OkHttp client.
 * It also uses the concrete request classes for each type of request
 * (e.g., GetRequest, PostRequest).
 */
public class ApiRequestHandler {

    OkHttpClient client; // OkHttp client for making requests

    /**
     * Constructor which initializes OkHttp client with timeout configuration.
     */
    public ApiRequestHandler() {
        client = new OkHttpClient.Builder()
                .connectTimeout(ApiConfig.getApiRequestTimeout(), TimeUnit.SECONDS) // Set connection timeout
                .readTimeout(ApiConfig.getApiResponseTimeout(), TimeUnit.SECONDS) // Set read timeout
                .writeTimeout(ApiConfig.getApiResponseTimeout(), TimeUnit.SECONDS) // Set write timeout
                .build();
    }

    /**
     * Sends a GET request to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param headers  Optional headers to include in the request.
     * @return The Response object from the API call.
     * @throws RuntimeException If there is an exception while making the api call
     */
    public Response get(String endpoint, Map<String, String> headers) {
        try {
            return new GetRequest(endpoint, headers).send(); // Send request through GetRequest
        } catch (Exception e) {
            LogManager.error("Error while sending Get request " + e.getMessage());
            throw new RuntimeException("Error while sending Get request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a POST request to the specified endpoint.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the POST request.
     * @param contentType The content type of the body.
     * @param headers     Optional headers to include in the request.
     * @return The Response object from the API call.
     * @throws RuntimeException If there is an exception while making the api call
     */
    public Response post(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            return new PostRequest(endpoint, requestBody, contentType, headers).send(); // Send request through PostRequest
        } catch (Exception e) {
            LogManager.error("Error while sending Post request " + e.getMessage());
            throw new RuntimeException("Error while sending Post request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a PUT request to the specified endpoint.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the PUT request.
     * @param contentType The content type of the body.
     * @param headers     Optional headers to include in the request.
     * @return The Response object from the API call.
     * @throws RuntimeException If there is an exception while making the api call
     */
    public Response put(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            return new PutRequest(endpoint, requestBody, contentType, headers).send(); // Send request through PutRequest
        } catch (Exception e) {
            LogManager.error("Error while sending Put request " + e.getMessage());
            throw new RuntimeException("Error while sending Put request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a DELETE request to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param headers  Optional headers to include in the request.
     * @return The Response object from the API call.
     * @throws RuntimeException If there is an exception while making the api call
     */
    public Response delete(String endpoint, Map<String, String> headers) {
        try {
            return new DeleteRequest(endpoint, headers).send(); // Send request through DeleteRequest
        } catch (Exception e) {
            LogManager.error("Error while sending Delete request " + e.getMessage());
            throw new RuntimeException("Error while sending Delete request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a PATCH request to the specified endpoint.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the PATCH request.
     * @param contentType The content type of the body.
     * @param headers     Optional headers to include in the request.
     * @return The Response object from the API call.
     * @throws RuntimeException If there is an exception while making the api call
     */
    public Response patch(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            return new PatchRequest(endpoint, requestBody, contentType, headers).send(); // Send request through PatchRequest
        } catch (Exception e) {
            LogManager.error("Error while sending Patch request " + e.getMessage());
            throw new RuntimeException("Error while sending Patch request " + e.getMessage(), e);
        }
    }
}