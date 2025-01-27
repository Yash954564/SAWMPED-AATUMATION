package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.Request;

public class AuthManager {

    public static void setBearerToken(Request.Builder requestBuilder, String token) {
        LogManager.info("Setting bearer token: " + token);
        requestBuilder.header("Authorization", "Bearer " + token);
    }

    public static void applyAuthentication(Request.Builder requestBuilder, String authType, String token) {
        LogManager.info("Applying authentication: " + authType + " with token: " + (token != null ? "*****" : "none")); // Mask token
        if(authType == null || authType.trim().isEmpty() || authType.equalsIgnoreCase("none")){
            LogManager.info("No authentication required");
            return;
        }
        switch (authType.toLowerCase()) {
            case "bearer":
                setBearerToken(requestBuilder, token);
                break;
            default:
                LogManager.error("Authentication type not supported: " + authType);
                throw new IllegalArgumentException("Authentication type not supported: " + authType);
        }
    }
}