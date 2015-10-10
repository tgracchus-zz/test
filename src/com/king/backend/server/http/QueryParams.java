package com.king.backend.server.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ulises on 8/10/15.
 */
public class QueryParams {

    private final Map<String, List<String>> params;

    public QueryParams(Map<String, List<String>> params) {
        this.params = Collections.unmodifiableMap(new HashMap<>(params));
    }

    public List<String> getParam(Object key) {
        return params.get(key);
    }


}
