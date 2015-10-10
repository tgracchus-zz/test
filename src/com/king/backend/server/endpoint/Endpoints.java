package com.king.backend.server.endpoint;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ulises on 8/10/15.
 */
public class Endpoints {

    private final List<Endpoint> endpointDefinitions;

    public Endpoints(List<Endpoint> endpointDefinitions) {
        this.endpointDefinitions = new ArrayList<>(endpointDefinitions);
    }

    public Optional<Endpoint> findEndpoint(URI requestUri,String method) {
        return endpointDefinitions.parallelStream()
                .filter(definition -> definition.match(requestUri,method)).findFirst(); //
    }
}
