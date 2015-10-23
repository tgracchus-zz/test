package com.test.backend;


import com.test.backend.app.model.Token;
import com.test.backend.app.model.User;
import com.test.backend.app.services.DefaultLoginService;
import com.test.backend.app.services.LoginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * Created by ulises.olivenza on 23/10/15.
 */
public class ConcurrentLoginServiceTest {

    private LoginService loginService;
    private User user;

    private List<ImpatientUser> impatientUsers;

    @Before
    public void setUp() throws Exception {

        user = new User(10);

        loginService = new DefaultLoginService(100000, Executors.newSingleThreadExecutor());

        impatientUsers = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            impatientUsers.add(new ImpatientUser(loginService, user));
        }

    }


    private static class ImpatientUser implements Runnable {

        private final LoginService loginService;
        private final User user;

        public ImpatientUser(LoginService loginService, User user) {
            this.loginService = loginService;
            this.user = user;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {

                Token token = loginService.login(user);
                Assert.assertNotNull(token);
                Assert.assertEquals(user.getUserId(), token.getUserId());

            }

        }
    }

    @Test
    public void testLoginLogout() throws Exception {

        Token token = loginService.login(user);

        AssertConcurrent.assertConcurrent("Finish Impatient User", impatientUsers, 120);

        Token afterUsersToken = loginService.login(user);
        Assert.assertEquals(token,afterUsersToken);

        User finalUser = loginService.findUserByTokenId(afterUsersToken.getToken());

        Assert.assertEquals(user,finalUser);

    }


}
