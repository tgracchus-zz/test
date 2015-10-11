package com.test.backend.app.model;


import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Token implements Delayed {

    private final int userId;
    private final UUID token;
    private final long delayInMilliseconds;

    public Token(UUID token,int userId,long delayInMilliseconds) {
        super();
        this.token = token;
        this.delayInMilliseconds = System.currentTimeMillis() + delayInMilliseconds;
        this.userId = userId;
    }

    public UUID getToken() {
        return token;
    }


    public int getUserId() {
        return userId;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = delayInMilliseconds - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (delayInMilliseconds - o.getDelay(TimeUnit.MINUTES));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        if (userId != token1.userId) return false;
        return !(token != null ? !token.equals(token1.token) : token1.token != null);

    }

    @Override
    public int hashCode() {
        int result = (userId ^ (userId >>> 32));
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
