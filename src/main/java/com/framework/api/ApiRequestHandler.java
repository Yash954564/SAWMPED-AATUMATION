package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

/**
 * This class handles the API requests by using RestAssured client.
 * It also uses the concrete request classes for each type of request
 * (e.g., GetRequest, PostRequest).
 */
public class ApiRequestHandler {

    /**
     * Sends a GET request to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param headers  Optional headers to include in the request.
     * @return The Response object from the API call.
     * @throws ApiException If there is an exception while making the api call
     */
    @Step("Send GET Request to {0}")
    public Response get(String endpoint, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given(); // initialize the RestAssured request object
            if (headers != null) {
                request.headers(headers); // set headers in the request.
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending GET request to URL: " + url); // log the url
            return request.get(url);  // make get request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Get request " + e.getMessage());
            throw new ApiException("Error while sending Get request " + e.getMessage(), e);
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
     * @throws ApiException If there is an exception while making the api call
     */
    @Step("Send POST Request to {0}")
    public Response post(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given()  // initialize the RestAssured request object
                    .contentType(contentType) // set content type
                    .body(requestBody);  // set request body.
            if (headers != null) {
                request.headers(headers); // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending POST request to URL: " + url); // log the url
            return request.post(url); // make post request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Post request " + e.getMessage());
            throw new ApiException("Error while sending Post request " + e.getMessage(), e);
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
     * @throws ApiException If there is an exception while making the api call
     */
    @Step("Send PUT Request to {0}")
    public Response put(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given()  // initialize the RestAssured request object
                    .contentType(contentType) // set content type
                    .body(requestBody);  // set request body.
            if (headers != null) {
                request.headers(headers); // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending PUT request to URL: " + url); // log the url
            return request.put(url); // make put request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Put request " + e.getMessage());
            throw new ApiException("Error while sending Put request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a DELETE request to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param headers  Optional headers to include in the request.
     * @return The Response object from the API call.
     * @throws ApiException If there is an exception while making the api call
     */
    @Step("Send DELETE Request to {0}")
    public Response delete(String endpoint, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given();  // initialize the RestAssured request object
            if (headers != null) {
                request.headers(headers);  // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending DELETE request to URL: " + url); // log the url
            return request.delete(url);  // make delete request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Delete request " + e.getMessage());
            throw new ApiException("Error while sending Delete request " + e.getMessage(), e);
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
     * @throws ApiException If there is an exception while making the api call
     */
    @Step("Send PATCH Request to {0}")
    public Response patch(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given() // initialize the RestAssured request object
                    .contentType(contentType) // set content type
                    .body(requestBody);  // set request body.
            if (headers != null) {
                request.headers(headers); // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending PATCH request to URL: " + url); // log the url
            return request.patch(url); // make patch request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Patch request " + e.getMessage());
            throw new ApiException("Error while sending Patch request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a GET request asynchronously to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param headers  Optional headers to include in the request.
     * @throws ApiException If there is an exception while making the api call
     * @implNote Rest Assured does not have direct async support. Running synchronously for migration simplicity. Consider CompletableFuture for true async.
     */
    @Step("Send Asynchronous GET Request to {0}")
    public Response getAsync(String endpoint, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given(); // initialize the RestAssured request object
            if (headers != null) {
                request.headers(headers); // set headers in the request.
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending GET request to URL: " + url); // log the url
            return request.get(url);  // make get request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Get request " + e.getMessage());
            throw new ApiException("Error while sending Get request " + e.getMessage(), e);
        }
    }


    /**
     * Sends a POST request asynchronously to the specified endpoint.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the POST request.
     * @param contentType The content type of the body.
     * @param headers     Optional headers to include in the request.
     * @throws ApiException If there is an exception while making the api call
     * @implNote Rest Assured does not have direct async support. Running synchronously for migration simplicity. Consider CompletableFuture for true async.
     */
    @Step("Send Asynchronous POST Request to {0}")
    public Response postAsync(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given()  // initialize the RestAssured request object
                    .contentType(contentType) // set content type
                    .body(requestBody);  // set request body.
            if (headers != null) {
                request.headers(headers); // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending POST request to URL: " + url); // log the url
            return request.post(url); // make post request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Post request " + e.getMessage());
            throw new ApiException("Error while sending Post request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a PUT request asynchronously to the specified endpoint.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the PUT request.
     * @param contentType The content type of the body.
     * @param headers     Optional headers to include in the request.
     * @throws ApiException If there is an exception while making the api call
     * @implNote Rest Assured does not have direct async support. Running synchronously for migration simplicity. Consider CompletableFuture for true async.
     */
    @Step("Send Asynchronous PUT Request to {0}")
    public Response putAsync(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given()  // initialize the RestAssured request object
                    .contentType(contentType) // set content type
                    .body(requestBody);  // set request body.
            if (headers != null) {
                request.headers(headers); // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending PUT request to URL: " + url); // log the url
            return request.put(url); // make put request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Put request " + e.getMessage());
            throw new ApiException("Error while sending Put request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a DELETE request asynchronously to the specified endpoint.
     *
     * @param endpoint The API endpoint.
     * @param headers  Optional headers to include in the request.
     * @throws ApiException If there is an exception while making the api call
     * @implNote Rest Assured does not have direct async support. Running synchronously for migration simplicity. Consider CompletableFuture for true async.
     */
    @Step("Send Asynchronous DELETE Request to {0}")
    public Response deleteAsync(String endpoint, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given();  // initialize the RestAssured request object
            if (headers != null) {
                request.headers(headers);  // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending DELETE request to URL: " + url); // log the url
            return request.delete(url);  // make delete request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Delete request " + e.getMessage());
            throw new ApiException("Error while sending Delete request " + e.getMessage(), e);
        }
    }

    /**
     * Sends a PATCH request asynchronously to the specified endpoint.
     *
     * @param endpoint    The API endpoint.
     * @param requestBody The body of the PATCH request.
     * @param contentType The content type of the body.
     * @param headers     Optional headers to include in the request.
     * @throws ApiException If there is an exception while making the api call
     * @implNote Rest Assured does not have direct async support. Running synchronously for migration simplicity. Consider CompletableFuture for true async.
     */
    @Step("Send Asynchronous PATCH Request to {0}")
    public Response patchAsync(String endpoint, String requestBody, String contentType, Map<String, String> headers) {
        try {
            RequestSpecification request = RestAssured.given() // initialize the RestAssured request object
                    .contentType(contentType) // set content type
                    .body(requestBody);  // set request body.
            if (headers != null) {
                request.headers(headers); // set headers in the request
            }
            String url = ApiConfig.getApiBaseUrl() + (endpoint.startsWith("/") ? endpoint.substring(1) : endpoint); // construct complete url
            LogManager.info("Sending PATCH request to URL: " + url); // log the url
            return request.patch(url); // make patch request and return response
        } catch (Exception e) {
            LogManager.error("Error while sending Patch request " + e.getMessage());
            throw new ApiException("Error while sending Patch request " + e.getMessage(), e);
        }
    }
}