package com.king.backend.app.services;

import com.king.backend.app.model.Token;
import com.king.backend.app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by ulises on 10/10/15.
 */
public class DefaultLoginService implements LoginService {

    private final ConcurrentMap<Integer, Token> tokensByUser;
    private final ConcurrentMap<UUID, User> userByTokens;
    private final DelayQueue<Token> expirationQueue;

    private ExpireThread expireThread;

    private long tokenDelayInMilliseconds;

    public DefaultLoginService(long tokenDelayInMilliseconds, ExecutorService executor) {
        this.expirationQueue = new DelayQueue<>();
        this.tokensByUser = new ConcurrentHashMap<>();
        this.userByTokens = new ConcurrentHashMap<>();
        this.tokenDelayInMilliseconds = tokenDelayInMilliseconds;

        //Not using ScheduledExecutorService, because when an exception occurs, it ends the scheduling
        this.expireThread = new ExpireThread(tokenDelayInMilliseconds, this.tokensByUser, this.userByTokens, this.expirationQueue);
        executor.execute(this.expireThread);
    }

    @Override
    public Token login(User user) {
        Token token = tokensByUser.get(user.getUserId());
        if (token == null) {
            Token newToken = new Token(UUID.randomUUID(), user.getUserId(), tokenDelayInMilliseconds);
            token = tokensByUser.putIfAbsent(user.getUserId(), newToken);
            if (token == null) {
                //New Token, add it first to tokensByTokens & expiration queue,
                //just in case it expires just before it's enqueue
                token = newToken;
                userByTokens.put(token.getToken(), user);
                expirationQueue.add(token);
            }
        }
        return token;
    }

    @Override
    public User findUserByTokenId(UUID tokenId) {
        return userByTokens.get(tokenId);
    }


    private static class ExpireThread implements Runnable {

        private final ConcurrentMap<Integer, Token> tokensByUser;
        private final ConcurrentMap<UUID, User> userByTokens;
        private final DelayQueue<Token> expirationQueue;

        private long wakeUpTime;

        public ExpireThread(long tokenDelayInMilliseconds, ConcurrentMap<Integer, Token> tokensByUser,//
                            ConcurrentMap<UUID, User> userByTokens, //
                            DelayQueue<Token> expirationQueue) {//
            this.tokensByUser = tokensByUser;
            this.userByTokens = userByTokens;
            this.expirationQueue = expirationQueue;
            this.wakeUpTime = (tokenDelayInMilliseconds * 10000);
        }

        @Override
        public void run() {
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
