package com.framework.api;

import okhttp3.Request;

/**
 * This interface defines the contract for handling the request before it is being sent to the server.
 */
public interface PreRequestScript {

    /**
     * This method will be called before the request is sent to the server.
     *
     * @param requestBuilder The OkHttp request builder.
     */
    void execute(Request.Builder requestBuilder);
}