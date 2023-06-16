package school21.spring.service.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;
import school21.spring.service.utils.initializerDB.DataBaseInitializer;
import school21.spring.service.utils.passwordGenerator.PasswordGeneratorUtil;

@Configuration
@PropertySource("classpath:db.properties")
public class TestApplicationConfig {

    @Value("${db.test.url}")
    private String url;

    @Value("${db.test.user}")
    private String username;

    @Value("${db.test.password}")
    private String password;

    @Value("${db.test.driver.name}")
    private String driverClassName;

    @Bean
    UsersRepository usersRepository () {
        return new UsersRepositoryJdbcImpl(driverManagerDataSource());
    }

    @Bean
    PasswordGeneratorUtil passwordGeneratorUtil () {
        return new PasswordGeneratorUtil();
    }

    @Bean
    UsersService usersService () {
        return new UsersServiceImpl(usersRepository(), passwordGeneratorUtil());
    }

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        driverManagerDataSource.setDriverClassName(driverClassName);
        return driverManagerDataSource;
    }

    @Bean
    DataBaseInitializer dataBaseInitializer () {
        return new DataBaseInitializer(driverManagerDataSource());
    }


}
