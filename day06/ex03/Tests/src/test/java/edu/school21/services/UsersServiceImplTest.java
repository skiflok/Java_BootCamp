package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

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
    }

    @Test
    public void authenticateSuccessfully() throws AlreadyAuthenticatedException {
        Mockito.when(usersRepository.findByLogin("USER")).thenReturn(user);
//        Mockito.doNothing().when(usersRepository).update(Mockito.any(User.class));
        assertTrue(usersService.authenticate("USER", "PASSWORD"));
        Mockito.verify(usersRepository).findByLogin("USER");
        Mockito.verify(usersRepository).update(user);
    }

    @Test
    public void authenticateBadLogin() {
        Mockito.when(usersRepository.findByLogin(Mockito.anyString())).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> usersService.authenticate("bad login", "PASSWORD"));
        Mockito.verify(usersRepository).findByLogin(Mockito.anyString());
        Mockito.verify(usersRepository, Mockito.never()).update(user);
    }
//
//    @Test
//    public void authenticateBadPassword() {
//
//    }


}