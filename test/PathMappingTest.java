package com.king.backend.test;

import com.king.backend.app.endpoints.HighScoreEndpoint;
import com.king.backend.app.endpoints.LoginEndpoint;
import com.king.backend.app.endpoints.ScoreEndpoint;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PathMappingTest {


    @Test
    public void simpleLoginMatch() throws URISyntaxException {
        LoginEndpoint loginEndpoint = new LoginEndpoint();
        boolean loginMatchResults = loginEndpoint.match(new URI("http://localhost/userid/login"));
        assertTrue(loginMatchResults);

    }


    @Test
    public void simpleScoreMatch() throws URISyntaxException {
        ScoreEndpoint scoreEndpoint = new ScoreEndpoint();
        boolean scoreMatchResults = scoreEndpoint.match(new URI("http://localhost/levelid/score?sessionkey=7cdc6e3d-629f-43e3-9670-06fef36a90d7"));
        assertTrue(scoreMatchResults);
    }


    @Test
    public void simpleHighscorelistMatch() throws URISyntaxException {
        HighScoreEndpoint highScoreEndpoint = new HighScoreEndpoint();
        boolean match = highScoreEndpoint.match(new URI("http://localhost/levelid/highscorelist"));
        assertTrue(match);

        URI uri = new URI("http://localhost/levelid/highscorelist");

        Path path =Paths.get(uri.getPath());

        for(Path ipath : path){
            System.out.print(ipath);
        }


    }
/*
    @Test
    public void complexLoginMatch() throws URISyntaxException {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn(HttpMethod.POST);
        when(exchange.getRequestURI()).thenReturn(new URI("/userid/login/?dads[]&44"));

        URIMatcher loginMatch = new LoginURIMatcher();
        MatchResult loginMatchResults = loginMatch.match(exchange);
        assertTrue(loginMatchResults.isAccepted());
        assertTrue(loginMatchResults.getAttributes() != null);
        assertTrue(loginMatchResults.getAttributes().size() == 1);
        assertTrue(loginMatchResults.getAttributes().get("param1").equals("userid"));
    }


    @Test
    public void complexScoreMatch() throws URISyntaxException {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn(HttpMethod.POST);
        when(exchange.getRequestURI()).thenReturn(new URI("/levelid/score?sessionkey=7cdc6e3d-629f-43e3-9670-06fef36a90d7?param=noise&pat=ad"));

        URIMatcher scoreMatch = new ScoreURIMatcher();
        MatchResult scoreMatchResults = scoreMatch.match(exchange);
        assertTrue(scoreMatchResults.isAccepted());
        assertTrue(scoreMatchResults.getAttributes() != null);
        assertTrue(scoreMatchResults.getAttributes().size() == 2);
        assertTrue(scoreMatchResults.getAttributes().get("param1").equals("levelid"));
        assertTrue(scoreMatchResults.getAttributes().get("sessionkey").equals("7cdc6e3d-629f-43e3-9670-06fef36a90d7"));
    }


    @Test
    public void complexHighscorelistMatch() throws URISyntaxException {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn(HttpMethod.GET);
        when(exchange.getRequestURI()).thenReturn(new URI("/levelid/highscorelist?dasdasdads&312323"));

        URIMatcher highscorelistMatch = new HighScoreURIMatcher();
        MatchResult highscorelistMatchResults = highscorelistMatch.match(exchange);
        assertTrue(highscorelistMatchResults.isAccepted());
        assertTrue(highscorelistMatchResults.getAttributes() != null);
        assertTrue(highscorelistMatchResults.getAttributes().size() == 1);
        assertTrue(highscorelistMatchResults.getAttributes().get("param1").equals("levelid"));
    }

    @Test
    public void errorLoginMatch() throws URISyntaxException {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn(HttpMethod.POST);
        when(exchange.getRequestURI()).thenReturn(new URI("/userid//login?8989&&34234"));

        URIMatcher loginMatch = new LoginURIMatcher();
        MatchResult loginMatchResults = loginMatch.match(exchange);
        assertFalse(loginMatchResults.isAccepted());
        assertTrue(loginMatchResults.getAttributes() == null);
    }


    @Test
    public void errorScoreMatch() throws URISyntaxException {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn(HttpMethod.POST);
        when(exchange.getRequestURI()).thenReturn(new URI("/levelid/score?sessionkey==7cdc6e3d-629f-43e3-9670-06fef36a90d7?param=noise&pat=ad"));

        URIMatcher scoreMatch = new ScoreURIMatcher();
        MatchResult scoreMatchResults = scoreMatch.match(exchange);
        assertFalse(scoreMatchResults.isAccepted());
        assertTrue(scoreMatchResults.getAttributes() == null);
    }


    @Test
    public void errorHighscorelistMatch() throws URISyntaxException {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn(HttpMethod.GET);
        when(exchange.getRequestURI()).thenReturn(new URI("//levelid/highscorelist"));

        URIMatcher highscorelistMatch = new HighScoreURIMatcher();
        MatchResult highscorelistMatchResults = highscorelistMatch.match(exchange);
        assertFalse(highscorelistMatchResults.isAccepted());
        assertTrue(highscorelistMatchResults.getAttributes() == null);
    }
*/
}
