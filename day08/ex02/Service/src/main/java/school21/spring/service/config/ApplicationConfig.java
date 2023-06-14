package school21.spring.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = "school21.spring.service.services")
@PropertySource("classpath:db.properties")

public class ApplicationConfig {

  @Value("${db.url}")
  private String url;

  @Value("${db.user}")
  private String username;

  @Value("${db.password}")
  private String password;

  @Value("${db.driver.name}")
  private String driverClassName;

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


  public DriverManagerDataSource driverManagerDataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setUrl(url);
    driverManagerDataSource.setUsername(username);
    driverManagerDataSource.setPassword(password);
    driverManagerDataSource.setDriverClassName(driverClassName);

    return driverManagerDataSource;
  }

}
