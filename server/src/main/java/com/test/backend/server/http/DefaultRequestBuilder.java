package com.test.backend.server.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ulises on 8/10/15.
 */
public class DefaultRequestBuilder implements RequestBuilder {

    @Override
    public Request newRequest(HttpExchange exchange) {
        URI requestUri = exchange.getRequestURI();
        QueryParams queryQueryParams = new QueryParams(parseQueryParams(requestUri.getQuery()));
        PathParams pathParams = new PathParams(parsePathParams(requestUri.getPath()));
        String body = parseBody(exchange.getRequestBody());
        return new Request(exchange.getRequestMethod(), body, exchange, pathParams, queryQueryParams);
    }

    static String parseBody(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private List<String> parsePathParams(String path) {
        Path parsedPath = Paths.get(path);
        List<String> paths = new ArrayList<>();
        for (Path pathPart : parsedPath) {
            paths.add(pathPart.toString());

        }

        return paths;

    }

    private Map<String, List<String>> parseQueryParams(String query) {
        try {
            Map<String, List<String>> params = new HashMap<>();
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }

                    List<String> values = params.get(key);
                    if (values == null) {
                        values = new ArrayList<>();
                        params.put(key, values);
                    }
                    values.add(value);
                }

            }
            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }
}
