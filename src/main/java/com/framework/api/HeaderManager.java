package com.framework.api;

import com.framework.base.LogManager;
import io.qameta.allure.Step;
import okhttp3.Request;

import java.util.Map;

/**
 * This class manages HTTP headers for API requests.
 * It provides methods to add or remove headers from an existing request.
 */
public class HeaderManager {

    /**
     * Adds headers to the request builder.
     *
     * @param requestBuilder The request builder to add headers to.
     * @param headers        The map of headers to be added.
     */
    @Step("Add headers to request builder: {1}")
    public static void addHeaders(Request.Builder requestBuilder, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            LogManager.info("Adding Headers: " + headers); // Log the headers
            headers.forEach(requestBuilder::addHeader); // Add headers to the request builder
        }
    }

    /**
     * Removes a header from the request builder.
     *
     * @param requestBuilder The request builder to remove the header from.
     * @param headerKey      The key of the header to be removed.
     */
    @Step("Remove header from request builder: {1}")
    public static void removeHeader(Request.Builder requestBuilder, String headerKey) {
        LogManager.info("Removing Header: " + headerKey); // Log the header being removed
        requestBuilder.removeHeader(headerKey); // Remove header from the request builder
    }
}