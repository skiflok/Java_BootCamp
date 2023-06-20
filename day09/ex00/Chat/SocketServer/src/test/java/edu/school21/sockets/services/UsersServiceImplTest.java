package edu.school21.sockets.services;

import edu.school21.sockets.config.TestApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.utils.DataBaseInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
    UsersServiceImpl usersService = ctx.getBean("usersService", UsersServiceImpl.class);

    @BeforeEach
    public void init(){
        ctx.getBean("dataBaseInitializer", DataBaseInitializer.class).init();
    }


    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2"})
    public void usersServiceTest (String email) {
        UsersRepository usersRepository = ctx.getBean("hikariDataSource", UsersRepository.class);
        Assertions.assertNotNull(usersService.signUp(email));
        User user = usersRepository.findByEmail(email).orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getEmail(), email);
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    public void usersServiceEmptyEmail (String email) {
        assertThrows(IllegalArgumentException.class, () -> usersService.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test1"})
    public void usersServiceEmailAlreadyExist (String email) {
        UsersRepository usersRepository = ctx.getBean("hikariDataSource", UsersRepository.class);
        Assertions.assertNotNull(usersService.signUp(email));
        User user = usersRepository.findByEmail(email).orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getEmail(), email);
        assertThrows(IllegalArgumentException.class, () -> usersService.signUp(email));
    }


}