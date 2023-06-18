package school21.spring.service.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
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
    UsersRepository usersRepositoryDriverManager () {
        return new UsersRepositoryJdbcImpl(driverManagerDataSource());
    }

    @Bean
    UsersRepository usersRepositoryHikari () {
        return new UsersRepositoryJdbcTemplateImpl(hikariDataSource());
    }

    @Bean
    PasswordGeneratorUtil passwordGeneratorUtil () {
        return new PasswordGeneratorUtil();
    }

    @Bean
    UsersService usersServiceDriverManager () {
        return new UsersServiceImpl(usersRepositoryDriverManager(), passwordGeneratorUtil());
    }

    @Bean
    UsersService usersServiceHikari () {
        return new UsersServiceImpl(usersRepositoryHikari(), passwordGeneratorUtil());
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
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        return hikariConfig;
    }

    @Bean
    public HikariDataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    DataBaseInitializer dataBaseInitializer () {
        return new DataBaseInitializer(driverManagerDataSource());
    }


}
