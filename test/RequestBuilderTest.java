package com.king.backend.test;

import com.king.backend.server.http.HttpMethod;
import com.king.backend.server.http.Request;
import com.king.backend.server.http.DefaultRequestBuilder;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestBuilderTest {


    @Test
    public void simpleLoginMatch() throws URISyntaxException {
        String method = HttpMethod.POST.name();

        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn(method);
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/levelid/score?sessionkey=7cdc6e3d-629f-43e3-9670-06fef36a90d7"));

        DefaultRequestBuilder requestBuilder = new DefaultRequestBuilder();


        Request request = requestBuilder.newRequest(httpExchange);
        Assert.assertEquals(request.getMethod(), method);
        Assert.assertEquals(2,request.getPathParams().getVariables().size());
        Assert.assertEquals(1, request.getQueryParams().getParams().size());
    }


}
