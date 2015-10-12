package com.test.backend.server.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpResponseSender {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void sendResponse(Response response) {
        try {
            sendResponse(response.getStatus().getStatus(), response.getExchange(), response.getBody());
        } catch (IOException e) {
            logger.log(Level.ALL, "Can not send response");
        }
    }

    public void sendResourceNotFoundResponse(HttpExchange exchange) {
        try {
            sendResponse(HttpStatus.SERVICE_NOT_AVAILABLE.getStatus(), exchange, exchange.getRequestURI().toString());
        } catch (IOException e) {
            logger.log(Level.ALL, "Can not send response");
        }
    }


    private void sendResponse(int status, HttpExchange exchange, String responseBody) throws IOException {
        OutputStream out = null;
        try {
            byte[] bytes = responseBody.getBytes(Charset.forName("UTF-8"));
            exchange.sendResponseHeaders(status, bytes.length);
            out = exchange.getResponseBody();
            out.write(bytes);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


}
