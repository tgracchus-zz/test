package com.test.backend.app.sservices;

import com.test.backend.app.model.Token;
import com.test.backend.app.model.User;
import com.test.backend.app.services.LoginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by ulises on 10/10/15.
 */
public class SDefaultLoginService implements LoginService {

    private final Map<Integer, Token> tokensByUser;
    private final Map<UUID, User> userByTokens;
    private final DelayQueue<Token> expirationQueue;

    private ExpireThread expireThread;

    private long tokenDelayInMilliseconds;

    public SDefaultLoginService(long tokenDelayInMilliseconds, ExecutorService executor) {
        this.expirationQueue = new DelayQueue<>();
        this.tokensByUser = new ConcurrentHashMap<>();
        this.userByTokens = new ConcurrentHashMap<>();
        this.tokenDelayInMilliseconds = tokenDelayInMilliseconds;

        //Not using ScheduledExecutorService, because when an exception occurs, it ends the scheduling
        this.expireThread =
                new ExpireThread(tokenDelayInMilliseconds, this.tokensByUser, this.userByTokens, this.expirationQueue);
        executor.execute(this.expireThread);
    }

    @Override public Token login(User user) {
        Token token = tokensByUser.get(user.getUserId());
        if (token == null) {
            Token newToken = new Token(UUID.randomUUID(), user.getUserId(), tokenDelayInMilliseconds);
            tokensByUser.put(user.getUserId(), newToken);
            token = newToken;
            userByTokens.put(token.getToken(), user);
            expirationQueue.add(token);
        }

        return token;
    }

    @Override public User findUserByTokenId(UUID tokenId) {
        return userByTokens.get(tokenId);
    }

    private static class ExpireThread implements Runnable {

        private final Map<Integer, Token> tokensByUser;
        private final Map<UUID, User> userByTokens;
        private final DelayQueue<Token> expirationQueue;

        private long wakeUpTime;

        public ExpireThread(long tokenDelayInMilliseconds, Map<Integer, Token> tokensByUser,//
                Map<UUID, User> userByTokens, //
                DelayQueue<Token> expirationQueue) {//
            this.tokensByUser = tokensByUser;
            this.userByTokens = userByTokens;
            this.expirationQueue = expirationQueue;
            this.wakeUpTime = (tokenDelayInMilliseconds * 10000);
        }

        @Override public void run() {
            while (1 == 1) {
                expireTokens();
                LockSupport.parkNanos(wakeUpTime);

            }
        }

        private void expireTokens() {
            List<Token> expiredTokens = new ArrayList<>();
            expirationQueue.drainTo(expiredTokens);

            for (Token token : expiredTokens) {
                userByTokens.remove(token.getToken());
                tokensByUser.remove(token.getUserId());
            }

        }

    }

}
