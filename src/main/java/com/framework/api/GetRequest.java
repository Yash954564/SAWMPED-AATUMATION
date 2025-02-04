package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * This class handles the GET API requests by implementing the send()
 * method which calls OkHttp client for actual execution.
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
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get(); // Set the method to GET

        // Add headers and authentication to request builder
        HeaderManager.addHeaders(requestBuilder, headers); // Add headers to the request
        AuthManager.applyAuthentication(requestBuilder, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Request request = requestBuilder.build(); // Build the request

        Response response = null; // Initialize response variable
        try {
            response = apiRequestHandler.client.newCall(request).execute();  // Execute the request using OkHttp client
            if (Objects.nonNull(response)) {
                LogManager.info("Response status code: " + response.code()); // Log the status code
            }
        } catch (IOException e) {
            LogManager.error("Error occurred while sending the GET request: " + e.getMessage());
            throw new ApiException("Error occurred while sending the GET request: " + e.getMessage(), e);
        }
        return response; // Return the response
    }


    /**
     * Sends the GET request Asynchronously.
     *
     * @param callback Callback for handling the response.
     * @throws ApiException If there is an exception while making the api call
     */
    @Step("Send Asynchronous GET Request")
    public void sendAsync(Callback callback) {
        String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint);; // Construct the URL and remove leading slash
        LogManager.info("Sending GET request asynchronously to URL: " + url); // Log the request

        // Build request
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get(); // Set the method to GET

        // Add headers and authentication to request builder
        HeaderManager.addHeaders(requestBuilder, headers); // Add headers to the request
        AuthManager.applyAuthentication(requestBuilder, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Request request = requestBuilder.build(); // Build the request

        Call call = apiRequestHandler.client.newCall(request); // Create a new call object
        call.enqueue(callback); // Execute the request using OkHttp client asynchronously

    }
}