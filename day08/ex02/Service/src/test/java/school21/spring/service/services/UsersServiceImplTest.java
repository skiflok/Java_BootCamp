package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.utils.initializerDB.DataBaseInitializer;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
    UsersServiceImpl usersService = ctx.getBean("usersServiceDriverManager", UsersServiceImpl.class);
    UsersServiceImpl usersServiceHikari = ctx.getBean("usersServiceHikari", UsersServiceImpl.class);

    @BeforeEach
    public void init(){
        DataBaseInitializer dataBaseInitializer = ctx.getBean("dataBaseInitializer", DataBaseInitializer.class);
        dataBaseInitializer.init();
    }


    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2"})
    public void usersServiceTest (String email) {
        UsersRepository usersRepository = ctx.getBean("usersRepositoryDriverManager", UsersRepository.class);
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
        UsersRepository usersRepository = ctx.getBean("usersRepositoryDriverManager", UsersRepository.class);
        Assertions.assertNotNull(usersService.signUp(email));
        User user = usersRepository.findByEmail(email).orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getEmail(), email);
        assertThrows(IllegalArgumentException.class, () -> usersService.signUp(email));
    }


}