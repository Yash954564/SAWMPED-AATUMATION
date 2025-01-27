package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.Response;

import java.util.Map;

public class DeleteRequest {
    private String endpoint;
    private Map<String, String> headers;
    private ApiRequestHandler apiRequestHandler;

    public DeleteRequest(String endpoint) {
        this.endpoint = endpoint;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    public DeleteRequest(String endpoint, Map<String, String> headers) {
        this.endpoint = endpoint;
        this.headers = headers;
        this.apiRequestHandler = new ApiRequestHandler();
    }

    public Response send(){
        LogManager.info("Sending Delete request to the endpoint "+endpoint);
        return apiRequestHandler.delete(endpoint, headers);
    }
}