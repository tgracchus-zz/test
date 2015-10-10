package com.king.backend.server.http;

/**
 * Created by ulises on 7/10/15.
 */
public interface ResponseBuilder {

    Response newResponse(HttpStatus status, Request request, String response);

    Response newServerErrorResponse(Request request, String response);

}


