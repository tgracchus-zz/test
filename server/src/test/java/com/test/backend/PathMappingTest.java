package com.test.backend;

import com.test.backend.app.endpoints.HighScoreEndpoint;
import com.test.backend.app.endpoints.LoginEndpoint;
import com.test.backend.app.endpoints.ScoreEndpoint;
import com.test.backend.app.model.User;
import com.test.backend.app.requestparser.ScoreRequest;
import com.test.backend.app.services.LoginService;
import com.test.backend.app.services.ScoreService;
import com.test.backend.server.endpoint.RequestParser;
import com.test.backend.server.http.HttpMethod;
import com.test.backend.server.http.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PathMappingTest {


    private ResponseBuilder responseBuilder;
    private RequestParser<User> userRequestParser;
    private RequestParser<ScoreRequest> scoreRequestParser;
    private RequestParser<Integer> levelRequestParser;
    private LoginService loginService;
    private ScoreService scoreService;

    private LoginEndpoint loginEndpoint;
    private ScoreEndpoint scoreEndpoint;
    private HighScoreEndpoint highScoreEndpoint;

    @Before
    public void setUp() throws Exception {
        responseBuilder = mock(ResponseBuilder.class);
        userRequestParser = mock(RequestParser.class);
        loginService = mock(LoginService.class);
        scoreRequestParser = mock(RequestParser.class);
        scoreService = mock(ScoreService.class);
        levelRequestParser = mock(RequestParser.class);
        loginEndpoint = new LoginEndpoint(responseBuilder, userRequestParser, loginService);
        scoreEndpoint = new ScoreEndpoint(responseBuilder, scoreRequestParser, scoreService, loginService);
        highScoreEndpoint = new HighScoreEndpoint(responseBuilder, levelRequestParser, scoreService);
    }

    @Test
    public void simpleLoginMatch() throws URISyntaxException {
        boolean loginMatchResults = loginEndpoint.match(new URI("http://localhost/userid/login"), HttpMethod.GET.name());
        assertTrue(loginMatchResults);

    }

    @Test
    public void complexLoginMatch() throws URISyntaxException {
        boolean loginMatchResults = loginEndpoint.match(new URI("http://localhost/userid/login?dads[]&44"), HttpMethod.GET.name());
        assertTrue(loginMatchResults);
    }

    @Test
    public void errorLoginMatch() throws URISyntaxException {
        boolean loginMatchResults = loginEndpoint.match(new URI("http://localhost//userid//logi?8989&&34234"), HttpMethod.GET.name());
        assertFalse(loginMatchResults);
    }




    @Test
    public void simpleScoreMatch() throws URISyntaxException {
        boolean scoreMatchResults = scoreEndpoint.match(
                new URI("http://localhost/levelid/score?sessionkey=7cdc6e3d-629f-43e3-9670-06fef36a90d7"), HttpMethod.POST.name());
        assertTrue(scoreMatchResults);
    }

    @Test
    public void complexScoreMatch() throws URISyntaxException {
        boolean scoreMatchResults = scoreEndpoint.match(
                new URI("http://localhost/levelid/score?sessionkey=7cdc6e3d-629f-43e3-9670-06fef36a90d7?param=noise&pat=ad"), HttpMethod.POST.name());
        assertTrue(scoreMatchResults);
    }

    @Test
    public void errorScoreMatch() throws URISyntaxException {
        boolean scoreMatchResults = scoreEndpoint.match(
                new URI("http://localhost/levelid/scoe?sessionkey==7cdc6e3d-629f-43e3-9670-06fef36a90d7?param=noise&pat=ad"), HttpMethod.POST.name());
        assertFalse(scoreMatchResults);
    }





    @Test
    public void simpleHighscorelistMatch() throws URISyntaxException {
        boolean match = highScoreEndpoint.match(new URI("http://localhost/levelid/highscorelist"), HttpMethod.GET.name());
        assertTrue(match);
    }

    @Test
    public void complexHighscorelistMatch() throws URISyntaxException {
        boolean match = highScoreEndpoint.match(new URI("http://localhost/highscorelist?dasdasdads&31232"), HttpMethod.GET.name());
        assertTrue(match);
    }

    @Test
    public void errorHighscorelistMatch() throws URISyntaxException {
        boolean match = highScoreEndpoint.match(new URI("http://localhost//levelidhighscorelist"), HttpMethod.GET.name());
        assertFalse(match);


    }

}
