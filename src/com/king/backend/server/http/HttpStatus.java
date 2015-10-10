package com.king.backend.server.http;

public enum HttpStatus {

    OK(200), SERVICE_NOT_AVAILABLE(503), NOT_AUTORIZED(401), BAD_REQUEST(400), INTERNAL_ERROR(500);

    private final int status;

    HttpStatus(int status) {
        this.status = status;
    }


}
