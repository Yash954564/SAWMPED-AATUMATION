package com.framework.utils.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.framework.api.ApiVariableManager;
import com.framework.base.LogManager;
import com.framework.utils.validation.AssertionUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
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

    /**
     * Validates a JSON response against a schema.
     * @param response The response object.
     * @param schemaPath The path to the schema file.
     * @throws RuntimeException if the schema validation fails.
     */
    public static void validateJsonResponseSchema(Response response, String schemaPath) {
        JsonNode responseNode = getJsonNodeFromResponse(response); // Get json node from the response
        if (responseNode==null){ // if the node is null, exit from here
            LogManager.warn("Response is null, so skipping the schema validation");
            return;
        }
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();  // Initialize json schema factory
            JsonNode schemaNode = JsonLoader.fromPath(schemaPath); // read schema file from the path.
            JsonSchema schema = factory.getJsonSchema(schemaNode); // get schema object from schema file.
            com.github.fge.jsonschema.core.report.ProcessingReport report = schema.validate(responseNode); // validate the schema against the response node.

            if (!report.isSuccess()) {
                LogManager.error("JSON Schema validation failed: " + report); // Log if schema validation fails.
                throw new RuntimeException("JSON Schema validation failed: " + report);
            } else {
                LogManager.info("JSON Schema validation passed");
            }
        } catch (IOException | ProcessingException e) {
            LogManager.error("Exception occurred during schema validation " + e.getMessage());
            throw new RuntimeException("Exception occurred during schema validation " + e.getMessage(), e);
        }
    }

    public static void validateJsonResponseValue(String responseBody, String jsonPath, Object expectedValue) {
        JsonNode responseNode = getJsonNodeFromString(responseBody);
        if (responseNode == null) {
            LogManager.warn("Response body is null, skipping the validation of " + jsonPath);
            return;
        }
        String actualValue = null;
        try {
            String[] pathParts = jsonPath.split("\\.");
            JsonNode currentNode = responseNode;
            for (String part : pathParts) {
                if (part.contains("[")){ // checking if the part contains array.
                    String[] arrayPart = part.split("\\["); // split by "["
                    String arrayName = arrayPart[0]; // array name before [
                    if (currentNode instanceof ObjectNode){ // if current node is a JSON object then get the object using array name.
                        currentNode = currentNode.get(arrayName);
                    } else {
                        LogManager.error("Invalid json path " + jsonPath);
                        throw new RuntimeException("Invalid json path " + jsonPath);
                    }
                    int index = Integer.parseInt(arrayPart[1].replaceAll("[^0-9]", ""));// get the index by removing non-numeric characters.
                    if (currentNode.isArray()) { // if array node read using the index.
                        currentNode = ((ArrayNode) currentNode).get(index);
                    } else {
                        LogManager.error("Invalid json path " + jsonPath);
                        throw new RuntimeException("Invalid json path " + jsonPath);
                    }

                } else if (currentNode instanceof ObjectNode) { // if the node is a JSON object then read the value based on the key.
                    currentNode = currentNode.get(part);
                } else {
                    LogManager.error("Invalid json path " + jsonPath);
                    throw new RuntimeException("Invalid json path " + jsonPath);
                }
            }
            if (currentNode!=null){
                actualValue = currentNode.asText(); // convert the node value as text.
            }
        } catch (Exception e) {
            LogManager.error("Unable to read the value from the response node " + jsonPath + " " + e.getMessage());
            throw new RuntimeException("Unable to read the value from the response node " + jsonPath + " " + e.getMessage(), e);
        }

        AssertionUtils.assertEquals(actualValue, String.valueOf(expectedValue), "Validating JSON value at path: " + jsonPath);
    }

//    /**
//     * Validates a specific value in the JSON response.
//     *
//     * @param responseBody The response body from an API call.
//     * @param jsonPath   The JSON path to the value to validate.
//     * @param expectedValue The expected value.
//     */
//    public static void validateJsonResponseValue(String responseBody, String jsonPath, Object expectedValue) {
//        JsonNode responseNode = getJsonNodeFromString(responseBody); // Get Json node from response.
//        if (responseNode == null) {
//            LogManager.warn("Response body is null, skipping the validation of "+jsonPath);
//            return;
//        }
//        String actualValue = null;
//        try {
//            String[] pathParts = jsonPath.split("\\."); // split path by . operator
//            JsonNode currentNode = responseNode;
//            for(String part : pathParts){
//                if(currentNode instanceof ObjectNode){
//                    currentNode = currentNode.get(part);
//                } else {
//                    LogManager.error("Invalid json path " + jsonPath);
//                    throw new RuntimeException("Invalid json path "+jsonPath);
//                }
//            }
//            if (currentNode!=null){
//                actualValue = currentNode.asText(); // convert the node value as text.
//            }
//        } catch (Exception e) {
//            LogManager.error("Unable to read the value from the response node " + jsonPath + " " + e.getMessage());
//            throw new RuntimeException("Unable to read the value from the response node " + jsonPath + " " + e.getMessage(), e);
//        }
//
//        AssertionUtils.assertEquals(actualValue, String.valueOf(expectedValue), "Validating JSON value at path: " + jsonPath);
//    }
//

    /**
     * Creates a request body based on content type.
     *
     * @param requestBody The request body string
     * @param contentType The content type (e.g., JSON, XML, TEXT).
     * @return RequestBody instance.
     */
    public static RequestBody createRequestBody(String requestBody, String contentType) {
        RequestBody body;
        MediaType mediaType;

        switch (contentType.toUpperCase()) {
            case "JSON":
                mediaType = MediaType.parse("application/json; charset=utf-8");
                break;
            case "XML":
                mediaType = MediaType.parse("application/xml; charset=utf-8");
                break;
            case "TEXT":
                mediaType = MediaType.parse("text/plain; charset=utf-8");
                break;
            default:
                mediaType = MediaType.parse(contentType);
                break;
        }

        body = RequestBody.create(requestBody, mediaType); // Create request body
        return body;
    }

//    /**
//     * Validates if a JSON response contains a specific key
//     * @param response The response from API.
//     * @param jsonPath The path of json node.
//     * @param message The message for the assertion.
//     */
//    public static void validateJsonResponseContains(Response response, String jsonPath, String message) {
//        JsonNode responseNode = getJsonNodeFromResponse(response);
//        if (responseNode == null) {
//            LogManager.warn("Response body is null, skipping the validation of " + jsonPath);
//            return;
//        }
//        try {
//            String[] pathParts = jsonPath.split("\\.");
//            JsonNode currentNode = responseNode;
//            for (String part : pathParts) {
//                if (currentNode instanceof ObjectNode) {
//                    currentNode = currentNode.get(part);
//                } else {
//                    LogManager.error("Invalid json path " + jsonPath);
//                    throw new RuntimeException("Invalid json path " + jsonPath);
//                }
//            }
//            AssertionUtils.assertNotNull(currentNode, message); // validates if the response node contains the path.
//        } catch (Exception e) {
//            LogManager.error("Unable to read the node from the response node " + jsonPath + " " + e.getMessage());
//            throw new RuntimeException("Unable to read the node from the response node " + jsonPath + " " + e.getMessage(), e);
//        }
//    }

    /**
     * Validates if a JSON response contains a specific key
     * @param responseBody The response body from API.
     * @param jsonPath The path of json node.
     * @param message The message for the assertion.
     */
    public static void validateJsonResponseContains(String responseBody, String jsonPath, String message) {
        JsonNode responseNode = getJsonNodeFromString(responseBody);
        if (responseNode == null) {
            LogManager.warn("Response body is null, skipping the validation of " + jsonPath);
            return;
        }
        try {
            String[] pathParts = jsonPath.split("\\.");
            JsonNode currentNode = responseNode;
            for (String part : pathParts) {
                if (currentNode instanceof ObjectNode) {
                    currentNode = currentNode.get(part);
                } else {
                    LogManager.error("Invalid json path " + jsonPath);
                    throw new RuntimeException("Invalid json path " + jsonPath);
                }
            }
            AssertionUtils.assertNotNull(currentNode, message); // validates if the response node contains the path.
        } catch (Exception e) {
            LogManager.error("Unable to read the node from the response node " + jsonPath + " " + e.getMessage());
            throw new RuntimeException("Unable to read the node from the response node " + jsonPath + " " + e.getMessage(), e);
        }
    }

    /**
     * Converts the JSON string to a JsonNode object.
     *
     * @param responseBody The JSON string of the response body
     * @return The JsonNode representing the JSON string, or null if the response body is empty or null.
     * @throws RuntimeException If the response is null or there is an error during JSON processing.
     */
    public static JsonNode getJsonNodeFromString(String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            LogManager.warn("Response body is empty or null"); // Log the warning
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper(); // Initialize object mapper
        try {
            return objectMapper.readTree(responseBody); // Convert the response body to JsonNode
        } catch (JsonProcessingException e) {
            LogManager.error("Error while processing the json response " + e.getMessage());
            throw new RuntimeException("Error while processing the json response " + e.getMessage(), e);
        }
    }
}