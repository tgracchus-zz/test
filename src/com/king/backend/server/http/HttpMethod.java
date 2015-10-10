package com.king.backend.server.http;

public enum HttpMethod {

    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE");


    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

}


