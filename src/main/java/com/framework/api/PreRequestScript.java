package com.framework.api;

import okhttp3.Request;

public interface PreRequestScript {
    void execute(Request.Builder requestBuilder);
}