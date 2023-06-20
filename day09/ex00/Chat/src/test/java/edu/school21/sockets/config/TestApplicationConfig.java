package edu.school21.sockets.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.application_utils.PasswordGeneratorUtil;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.sockets.repositories.utils.DataBaseInitializer;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = "edu.school21.sockets")
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
    UsersRepository usersRepositoryHikari () {
        return new UsersRepositoryJdbcTemplateImpl(hikariDataSource());
    }

    @Bean
    PasswordGeneratorUtil passwordGeneratorUtil () {
        return new PasswordGeneratorUtil();
    }


    @Bean
    UsersService usersServiceHikari () {
        return new UsersServiceImpl(usersRepositoryHikari(), passwordGeneratorUtil());
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


}
