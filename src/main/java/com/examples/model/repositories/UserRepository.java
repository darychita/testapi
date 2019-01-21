package com.examples.model.repositories;

import com.examples.exceptions.SuchUserExistsException;
import com.examples.model.entity.Token;
import com.examples.model.entity.User;

public interface UserRepository {

    void addUser(User user) throws SuchUserExistsException;
    User findUserByLogin(String login);
    void updateWithToken(User user, Token token);

}
