package com.king.backend.app.model;

/**
 * Created by ulises on 10/10/15.
 */
public class User {

    private int userId;

    public User(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return userId == user.userId;

    }

    @Override
    public int hashCode() {
        return userId ^ (userId >>> 32);
    }
}
