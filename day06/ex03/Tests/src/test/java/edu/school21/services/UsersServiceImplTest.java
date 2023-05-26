package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    private UsersRepository usersRepository;
    UsersServiceImpl usersService;

    private User user;

    @BeforeEach
    public void init () {
        usersRepository = Mockito.mock(UsersRepository.class);
        usersService = new UsersServiceImpl(usersRepository);
        user = new User(1L, "USER", "PASSWORD", false);
        Mockito.when(usersRepository.findByLogin("USER")).thenReturn(user);
        Mockito.doNothing().when(usersRepository).update(user);
    }

    @Test
    public void authenticateSuccessfully() throws AlreadyAuthenticatedException {
        assertTrue(usersService.authenticate("USER", "PASSWORD"));
        Mockito.verify(usersRepository).findByLogin("USER");
        Mockito.verify(usersRepository).update(user);
    }

//    @Test
//    public void authenticateBadLogin() {
//
//    }
//
//    @Test
//    public void authenticateBadPassword() {
//
//    }


}