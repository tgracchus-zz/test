package com.king.backend.server.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ulises on 10/10/15.
 */
public class PathParams {

    private final List<String> variables;

    public PathParams(List<String> variables) {
        this.variables = Collections.unmodifiableList(new ArrayList<>(variables));
    }

    public List<String> getVariables() {
        return variables;
    }

}
