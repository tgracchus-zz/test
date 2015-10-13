import com.test.backend.app.TestServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ulises on 12/10/15.
 */
public class ConcurrentIntegrationTest {

    private TestServer testServer;
    private TestServerThread testServerThread;
    private Client client;
    private WebTarget target;

    private int numberOfScores;

    private int users;

    @Before public void setUp() throws Exception {
        numberOfScores = 15;
        users = 100;
        testServer = new TestServer(1000 * 60 * 10, numberOfScores);
        testServerThread = new TestServerThread(testServer);
        testServerThread.start();
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8888");
    }

    @After public void tearDown() throws Exception {
        testServer.stopServer();
        testServerThread.interrupt();
    }

    private static class TestServerThread extends Thread {

        private final TestServer testServer;

        public TestServerThread(TestServer testServer) {
            this.testServer = testServer;
        }

        @Override public void run() {
            testServer.runServer();
        }
    }

    @Test public void testHeadyLoad() throws Exception {

        List<ExigentUser> clients = new ArrayList<>(numberOfScores);
        for (int j = 0; j < users; j++) {
            clients.add(new ExigentUser(target, j + 1, 1, numberOfScores));
        }

        //Mean of 100 iterations
        long nanos = System.nanoTime();
        int repetitions = 10;
        String highScores;
        String lastHighScores = null;
        for (int i = 0; i < repetitions; i++) {

            AssertConcurrent.assertConcurrent("Done concurrent testing", clients, 120);

            Response response = target.path(1 + "/highscorelist").request().get();
            if (response.getStatus() != 200) {
                response.close();
                Assert.fail();
            }

            highScores = response.readEntity(String.class);
            if (i == 0) {
                lastHighScores = highScores;
            }
            Assert.assertEquals(lastHighScores, highScores);
            lastHighScores = highScores;
        }

        long totalNanos = (System.nanoTime() - nanos);
        System.out.println(
                "Mean response time " + (totalNanos / 1000000000.0) / repetitions + " of seconds for " + repetitions
                        + " repetitions and " + users + " concurrent users");

    }

    private static class ExigentUser implements Runnable {

        private final WebTarget target;
        private final int userId;
        private final int levelId;
        private final int numberOfScores;

        public ExigentUser(WebTarget target, int userId, int levelId, int numberOfScores) {
            this.target = target;
            this.userId = userId;
            this.levelId = levelId;
            this.numberOfScores = numberOfScores;
        }

        @Override public void run() {

            Response response = target.path(userId + "/login").request().get();
            if (response.getStatus() != 200) {
                response.close();
                Assert.fail();
            }

            String token = response.readEntity(String.class);
            response.close();

            for (int i = 0; i < numberOfScores + 1; i++) {
                response = target.path(levelId + "/score").queryParam("sessionkey", token).request()
                        .post(Entity.entity(i, MediaType.TEXT_PLAIN_TYPE));
                if (response.getStatus() != 200) {
                    response.close();
                    Assert.fail();
                }
                response.close();
            }

            response = target.path(levelId + "/highscorelist").request().get();
            if (response.getStatus() != 200) {
                response.close();
                Assert.fail();
            }

            String highScores = response.readEntity(String.class);
            response.close();

        }
    }

}
