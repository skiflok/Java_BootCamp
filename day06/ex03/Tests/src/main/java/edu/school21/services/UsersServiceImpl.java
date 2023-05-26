package edu.school21.services;

import edu.school21.models.User;

public interface UsersServiceImpl {
    User findByLogin(String login);
    void update(User user);
}
