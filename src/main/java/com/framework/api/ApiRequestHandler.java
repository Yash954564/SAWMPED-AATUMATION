package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.Response;

import java.io.IOException;

public class ApiRequestHandler {

    public Response get(String endpoint, java.util.Map<String, String> headers){
        try {
            return new GetRequest(endpoint, headers).send();
        }catch (Exception e){
            LogManager.error("Error while sending Get request "+e.getMessage());
            throw new RuntimeException("Error while sending Get request "+e.getMessage());
        }

    }

    public Response post(String endpoint, String requestBody, String contentType, java.util.Map<String, String> headers){
        try {
            return new PostRequest(endpoint, requestBody, contentType, headers).send();
        }catch (Exception e){
            LogManager.error("Error while sending Post request "+e.getMessage());
            throw new RuntimeException("Error while sending Post request "+e.getMessage());
        }

    }

    public Response put(String endpoint, String requestBody, String contentType, java.util.Map<String, String> headers){
        try {
            return new PutRequest(endpoint, requestBody, contentType, headers).send();
        }catch (Exception e){
            LogManager.error("Error while sending Put request "+e.getMessage());
            throw new RuntimeException("Error while sending Put request "+e.getMessage());
        }
    }

    public Response delete(String endpoint, java.util.Map<String, String> headers){
        try {
            return new DeleteRequest(endpoint, headers).send();
        }catch (Exception e){
            LogManager.error("Error while sending Delete request "+e.getMessage());
            throw new RuntimeException("Error while sending Delete request "+e.getMessage());
        }

    }

    public Response patch(String endpoint, String requestBody, String contentType, java.util.Map<String, String> headers){
        try {
            return new PatchRequest(endpoint, requestBody, contentType, headers).send();
        }catch (Exception e){
            LogManager.error("Error while sending Patch request "+e.getMessage());
            throw new RuntimeException("Error while sending Patch request "+e.getMessage());
        }
    }
}