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

    private Client client;

    private int numberOfScores;

    private int users;

    private static String HOST = "http://localhost:8888";

    @Before public void setUp() throws Exception {
        numberOfScores = 15;
        users = 100;
        client = ClientBuilder.newClient();
    }

    @After public void tearDown() throws Exception {
        client.close();
    }

    @Test public void testHeadyLoad() throws Exception {

        List<ExigentUser> clients = new ArrayList<>(numberOfScores);
        for (int j = 0; j < users; j++) {
            clients.add(new ExigentUser(client.target(HOST), j + 1, 1, numberOfScores));
        }

        //Mean of 100 iterations
        long nanos = System.nanoTime();
        int repetitions = 10;
        String highScores;
        String lastHighScores = null;
        WebTarget target = client.target(HOST);

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
