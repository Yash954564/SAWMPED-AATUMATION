package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.Request;

import java.util.Map;

public class HeaderManager {
    public static void addHeaders(Request.Builder requestBuilder, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            LogManager.info("Adding Headers: " + headers);
            headers.forEach(requestBuilder::addHeader);
        }
    }

    public static void removeHeader(Request.Builder requestBuilder, String headerKey) {
        LogManager.info("Removing Header: " + headerKey);
        requestBuilder.removeHeader(headerKey);
    }
}