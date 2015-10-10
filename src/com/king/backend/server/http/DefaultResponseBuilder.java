package com.king.backend.server.http;

/**
 * Created by ulises on 10/10/15.
 */
public class DefaultResponseBuilder implements ResponseBuilder {

    @Override
    public Response newResponse(HttpStatus status, Request request, String response) {
        return new Response(status, request, response);
    }

    @Override
    public Response newServerErrorResponse(Request request, String response) {
        return new Response(HttpStatus.INTERNAL_ERROR, request, response);
    }

}

