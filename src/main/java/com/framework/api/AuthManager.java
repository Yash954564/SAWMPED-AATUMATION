package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

/**
 * This class manages the authentication for API requests.
 * It handles different types of authentication such as Bearer token.
 */
public class AuthManager {

    /**
     * Sets the Bearer token in the request specification.
     *
     * @param request The request specification to add the header to.
     * @param token          The Bearer token value.
     */
    @Step("Set Bearer token in the request header")
    public static void setBearerToken(RequestSpecification request, String token) {
        LogManager.info("Setting bearer token: " + (token != null ? "*****" : "none"));  // Log the action with masked token
        request.header("Authorization", "Bearer " + token); // Add Bearer token header
    }

    /**
     * Applies the appropriate authentication based on the provided type.
     *
     * @param request The request specification to add the authentication to.
     * @param authType       The authentication type (e.g., "bearer", or "none").
     * @param token          The token value for authentication.
     * @throws IllegalArgumentException If the authentication type is not supported.
     */
    @Step("Apply Authentication, Type: {0}")
    public static void applyAuthentication(RequestSpecification request, String authType, String token) {
        LogManager.info("Applying authentication: " + authType + " with token: " + (token != null ? "*****" : "none")); // Log auth type and masked token

        // If authentication is not configured, return from here
        if (authType == null || authType.trim().isEmpty() || authType.equalsIgnoreCase("none")) {
            LogManager.info("No authentication required"); // Log the action
            return;
        }

        // Apply authentication based on auth type
        switch (authType.toLowerCase()) {
            case "bearer":
                setBearerToken(request, token); // Set Bearer token
                break;
            default:
                LogManager.error("Authentication type not supported: " + authType); // Log unsupported type
                throw new IllegalArgumentException("Authentication type not supported: " + authType); // Throw exception
        }
    }
}