package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.Response;
import java.util.Map;

public class PutRequest {
    private String endpoint;
    private String requestBody;
    private String contentType;
    private Map<String, String> headers;
    private ApiRequestHandler apiRequestHandler;


    public PutRequest(String endpoint, String requestBody) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = ApiConfig.getApiDefaultContentType();
        this.apiRequestHandler = new ApiRequestHandler();
    }
    public PutRequest(String endpoint, String requestBody, String contentType) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.apiRequestHandler = new ApiRequestHandler();
    }
    public PutRequest(String endpoint, String requestBody, String contentType,  Map<String, String> headers) {
        this.endpoint = endpoint;
        this.requestBody = requestBody;
        this.contentType = contentType;
        this.headers = headers;
        this.apiRequestHandler = new ApiRequestHandler();
    }
    public Response send(){
        LogManager.info("Sending Put request to the endpoint "+endpoint);
        return apiRequestHandler.put(endpoint, requestBody, contentType, headers);
    }
}