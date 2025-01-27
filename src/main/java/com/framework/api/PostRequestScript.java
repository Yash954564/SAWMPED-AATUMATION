package com.framework.api;

import com.framework.base.LogManager;
import okhttp3.Response;
import java.io.IOException;
import java.util.Objects;

public interface PostRequestScript {
    void execute(Response response);
    default void setVariables(Response response){
        if(Objects.nonNull(response)){
            try {
                String responseBody = Objects.requireNonNull(response.body()).string();
                LogManager.info("Setting variable from the response body : "+responseBody);
                ApiVariableManager.setVariable("responseBody",responseBody);
                ApiVariableManager.setVariable("statusCode", String.valueOf(response.code()));
            } catch (IOException e){
                LogManager.error("Error while reading the response body "+e.getMessage());
                throw new RuntimeException("Error while reading the response body "+e.getMessage());
            }
        }
    }
    default String getResponse(Response response){
        try {
            return Objects.requireNonNull(response.body()).string();
        } catch (Exception e){
            LogManager.error("Error while reading the response body "+e.getMessage());
            throw new RuntimeException("Error while reading the response body "+e.getMessage());
        }
    }
}
