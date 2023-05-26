package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class UsersServiceImpl {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException {

        Optional<User> userOptional = Optional.ofNullable(usersRepository.findByLogin(login));
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User not found");
        }
        if (userOptional.get().isAuthenticated()) {
            throw new AlreadyAuthenticatedException("User already authenticated");
        }

        if (password.equals(userOptional.get().getPassword())) {
            userOptional.get().setAuthenticated(true);
            usersRepository.update(userOptional.get());
        }

        return userOptional.get().isAuthenticated();
    }
}
