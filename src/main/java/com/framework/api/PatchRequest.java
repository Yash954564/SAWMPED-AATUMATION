package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * This class handles the PATCH API requests by implementing the send()
 * method which calls OkHttp client for actual execution.
 */
public class PatchRequest {
    private String endpoint; // API endpoint
    private String requestBody; // Request body
    private String contentType; // Content type of the request body
    private Map<String, String> headers; // Headers for the request
    private final ApiRequestHandler apiRequestHandler; // Instance of ApiRequestHandler

    // Constants for media types
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
    private static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");

    /**
     * Constructor for creating a PatchRequest with an endpoint, request body and content type.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the request.
     */
    public PatchRequest(String endpoint, String requestBody) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = ApiConfig.getApiDefaultContentType(); // Default content type if not specified
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Constructor for creating a PatchRequest with an endpoint, request body and content type.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the request.
     * @param contentType The content type of the body.
     */
    public PatchRequest(String endpoint, String requestBody, String contentType) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Constructor for creating a PatchRequest with an endpoint, request body, content type and headers.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the request.
     * @param contentType The content type of the body.
     * @param headers     Headers to be sent with the request.
     */
    public PatchRequest(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.headers = headers;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Sends the PATCH request.
     *
     * @return The Response object from the API call.
     * @throws RuntimeException If there is an exception while making the api call
     */
    public Response send() {
        String url = ApiConfig.getApiBaseUrl() + endpoint; // Construct the URL
        LogManager.info("Sending PATCH request to URL: " + url); // Log the request
        RequestBody body;

        // Create the request body
        switch (contentType.toUpperCase()) {
            case "JSON":
                body = RequestBody.create(requestBody, JSON);
                break;
            case "XML":
                body = RequestBody.create(requestBody, XML);
                break;
            case "TEXT":
                body = RequestBody.create(requestBody, TEXT);
                break;
            default:
                body = RequestBody.create(requestBody, MediaType.parse(contentType)); // Default to provided content type
                break;
        }

        // Build request
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .patch(body); // Set the method to PATCH

        // Add headers and authentication to request builder
        HeaderManager.addHeaders(requestBuilder, headers); // Add headers to the request
        AuthManager.applyAuthentication(requestBuilder, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Request request = requestBuilder.build(); // Build the request
        Response response = null; // Initialize the response
        try {
            response = apiRequestHandler.client.newCall(request).execute(); // Execute the request using OkHttp client
            if (Objects.nonNull(response)) {
                LogManager.info("Response status code: " + response.code()); // Log the status code
            }
        } catch (IOException e) {
            LogManager.error("Error occurred while sending the PATCH request: " + e.getMessage());
            throw new RuntimeException("Error occurred while sending the PATCH request: " + e.getMessage(), e);
        }
        return response; // Return the response
    }
}