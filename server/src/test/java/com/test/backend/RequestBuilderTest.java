package com.test.backend;

import com.sun.net.httpserver.HttpExchange;
import com.test.backend.server.http.DefaultRequestBuilder;
import com.test.backend.server.http.HttpMethod;
import com.test.backend.server.http.Request;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestBuilderTest {



    @Test
    public void simpleLoginMatch() throws URISyntaxException {
        String method = HttpMethod.POST.name();

        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn(method);
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/userid/login"));

        InputStream stream = new ByteArrayInputStream("body".getBytes(StandardCharsets.UTF_8));
        when(httpExchange.getRequestBody()).thenReturn(stream);


        DefaultRequestBuilder requestBuilder = new DefaultRequestBuilder();

        Request request = requestBuilder.newRequest(httpExchange);
        Assert.assertEquals(request.getMethod(), method);
        Assert.assertEquals(2, request.getPathParams().getVariables().size());
    }

    @Test
    public void simpleScoreMatch() throws URISyntaxException {
        String method = HttpMethod.POST.name();

        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn(method);
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/levelid/score?sessionkey=7cdc6e3d-629f-43e3-9670-06fef36a90d7"));
        InputStream stream = new ByteArrayInputStream("body".getBytes(StandardCharsets.UTF_8));
        when(httpExchange.getRequestBody()).thenReturn(stream);


        DefaultRequestBuilder requestBuilder = new DefaultRequestBuilder();

        Request request = requestBuilder.newRequest(httpExchange);
        Assert.assertEquals(request.getMethod(), method);
        Assert.assertEquals(2, request.getPathParams().getVariables().size());
        Assert.assertNotNull(request.getQueryParams().getParam("sessionkey"));
    }


    @Test
    public void testHighScoreMatch() throws URISyntaxException {
        String method = HttpMethod.POST.name();

        HttpExchange httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn(method);
        when(httpExchange.getRequestURI()).thenReturn(new URI("http://localhost/levelid/highscorelist"));
        InputStream stream = new ByteArrayInputStream("body".getBytes(StandardCharsets.UTF_8));
        when(httpExchange.getRequestBody()).thenReturn(stream);

        DefaultRequestBuilder requestBuilder = new DefaultRequestBuilder();

        Request request = requestBuilder.newRequest(httpExchange);
        Assert.assertEquals(request.getMethod(), method);
        Assert.assertEquals(2, request.getPathParams().getVariables().size());
    }


}
