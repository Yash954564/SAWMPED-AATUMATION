package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * This class manages HTTP headers for API requests.
 * It provides methods to add or remove headers from an existing request.
 */
public class HeaderManager {

    /**
     * Adds headers to the request builder.
     *
     * @param request The request builder to add headers to.
     * @param headers        The map of headers to be added.
     */
    @Step("Add headers to request builder: {1}")
    public static void addHeaders(RequestSpecification request, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            LogManager.info("Adding Headers: " + headers); // Log the headers
            request.headers(headers);  // Add headers to the request builder
        }
    }

    /**
     * Removes a header from the request builder.
     * @implNote Not supported in Rest Assured  : need to create a new RequestSpecification by copying all other headers and excluding the provided header
     * @param request The request builder to remove the header from.
     * @param headerKey      The key of the header to be removed.
     */
    @Step("Remove header from request builder: {1}")
    public static void removeHeader(RequestSpecification request, String headerKey) {
        LogManager.warn("Removing Header not supported in Rest Assured: " + headerKey); // Log the header being removed
        // To remove a header, you will need to create a new RequestSpecification instance and manually copy all headers except the one to remove.
    }
}