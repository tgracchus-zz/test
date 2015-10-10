package com.king.backend.app.services;

import com.king.backend.app.model.Token;
import com.king.backend.app.model.User;

import java.util.UUID;

/**
 * Created by ulises on 10/10/15.
 */
public interface LoginService {

    Token login(User user);

    User findUserByTokenId(UUID tokenId);
}
