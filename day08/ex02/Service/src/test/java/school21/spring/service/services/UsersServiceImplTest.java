package school21.spring.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.utils.initializerDB.DataBaseInitializer;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
    UsersServiceImpl usersService = ctx.getBean("usersService", UsersServiceImpl.class);

    @BeforeEach
    public void init(){
        DataBaseInitializer dataBaseInitializer = ctx.getBean("dataBaseInitializer", DataBaseInitializer.class);
        dataBaseInitializer.init();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "312"})
    public void test (String str) {
        System.out.println(usersService.signUp(str));
    }

}