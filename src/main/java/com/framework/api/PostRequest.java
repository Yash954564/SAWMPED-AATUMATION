package com.framework.api;

import com.framework.base.LogManager;
import com.framework.utils.api.ApiUtils;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * This class handles the POST API requests by implementing the send()
 * method which calls OkHttp client for actual execution.
 */
public class PostRequest {
    private String endpoint; // API endpoint
    private String requestBody; // Request body
    private String contentType; // Content type of the request body
    private Map<String, String> headers; // Headers for the request
    private final ApiRequestHandler apiRequestHandler; // Instance of ApiRequestHandler

    /**
     * Constructor for creating a PostRequest with an endpoint, request body and content type.
     *
     * @param endpoint The API endpoint.
     * @param requestBody  The body of the request.
     */
    public PostRequest(String endpoint, String requestBody) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = ApiConfig.getApiDefaultContentType();
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Constructor for creating a PostRequest with an endpoint, request body and content type.
     *
     * @param endpoint The API endpoint.
     * @param requestBody The body of the request.
     * @param contentType The content type of the body.
     */
    public PostRequest(String endpoint, String requestBody, String contentType) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Constructor for creating a PostRequest with an endpoint, request body, content type and headers.
     *
     * @param endpoint The API endpoint.
     * @param requestBody The body of the request.
     * @param contentType The content type of the body.
     * @param headers  Headers to be sent with the request.
     */
    public PostRequest(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.headers = headers;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    /**
     * Sends the POST request.
     *
     * @return The Response object from the API call.
     * @throws ApiException If there is an exception while making the api call
     */
    public Response send() {
        String url = ApiConfig.getApiBaseUrl() +  (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // Construct the URL and remove leading slash
        LogManager.info("Sending POST request to URL: " + url); // Log the request
        // Create the request body
        RequestBody body= ApiUtils.createRequestBody(requestBody,contentType);


        // Build request
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body); // Set the method to POST

        // Add headers
        HeaderManager.addHeaders(requestBuilder, headers);  // Add headers to the request


        Request request = requestBuilder.build(); // Build the request
        Response response=null;  // Initialize response variable
        try{
            response=  apiRequestHandler.client.newCall(request).execute();  // Execute the request using OkHttp client
            if (Objects.nonNull(response)){
                LogManager.info("Response status code: "+response.code()); // Log the status code
            }
        }catch (IOException e){
            LogManager.error("Error occurred while sending the POST request: " + e.getMessage());
            throw new ApiException("Error occurred while sending the POST request: " + e.getMessage(), e);
        }
        return response;  // Return the response
    }

    /**
     * Sends the POST request Asynchronously.
     *
     * @param callback Callback for handling the response.
     * @throws ApiException If there is an exception while making the api call
     */
    public void sendAsync(Callback callback) {
        String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint);; // Construct the URL and remove leading slash
        LogManager.info("Sending POST request asynchronously to URL: " + url); // Log the request

        // Create the request body
        RequestBody body = ApiUtils.createRequestBody(requestBody,contentType);


        // Build request
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body); // Set the method to POST

        // Add headers and authentication to request builder
        HeaderManager.addHeaders(requestBuilder, headers); // Add headers to the request
        AuthManager.applyAuthentication(requestBuilder, ApiConfig.getApiAuthType(), SessionManager.getSessionValue("token"));

        Request request = requestBuilder.build(); // Build the request

        Call call = apiRequestHandler.client.newCall(request); // Create a new call object
        call.enqueue(callback); // Execute the request using OkHttp client asynchronously
    }
}