package com.framework.api;

import com.framework.base.BaseTest;
import com.framework.base.LogManager;

public class ApiConfig {

    private static final String API_BASE_URL = "api.base.url";
    private static final String API_DEFAULT_CONTENT_TYPE = "api.default.content.type";
    private static final String API_REQUEST_TIMEOUT = "api.request.timeout";
    private static final String API_RESPONSE_TIMEOUT = "api.response.timeout";
    private static final String API_AUTH_TYPE = "api.auth.type";
    private static BaseTest baseTest = new BaseTest();
    public static String getApiBaseUrl() {
        String url = baseTest.getProperty(API_BASE_URL);
        LogManager.info("API Base URL: " + url);
        return url;
    }

    public static String getApiDefaultContentType() {
        String contentType = baseTest.getProperty(API_DEFAULT_CONTENT_TYPE);
        LogManager.info("API Default Content Type: " + contentType);
        return contentType;
    }
    public static int getApiRequestTimeout() {
        String timeout = baseTest.getProperty(API_REQUEST_TIMEOUT);
        LogManager.info("API Request timeout: " + timeout);
        return Integer.parseInt(timeout);
    }
    public static int getApiResponseTimeout() {
        String timeout = baseTest.getProperty(API_RESPONSE_TIMEOUT);
        LogManager.info("API Response timeout: " + timeout);
        return Integer.parseInt(timeout);
    }
    public static String getApiAuthType() {
        String authType = baseTest.getProperty(API_AUTH_TYPE);
        LogManager.info("API Authentication Type: " + authType);
        return authType;
    }
}