package edu.school21.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.util.TableInitializer;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan("edu.school21")
public class TanksServerConfig {

  @Value("${db.url}")
  String url;

  @Value("${db.user}")
  String user;

  @Value("${db.password}")
  String password;

  @Value("${db.driver.name}")
  String driverName;

  @Value("${db.path.schema}")
  String schema;

  @Value("${db.path.data}")
  String data;

  @Bean
  public HikariConfig hikariConfig() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(user);
    config.setPassword(password);
    config.setDriverClassName(driverName);
    return config;
  }

  @Bean
  public DataSource dataSource() {
    return new HikariDataSource(hikariConfig());
  }

//  public JdbcTemplate jdbcTemplate() {
//    return  new JdbcTemplate(dataSource());
//  }
  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    return new NamedParameterJdbcTemplate(dataSource());
  }

  @Bean
  public TableInitializer tableInitializer() {
    TableInitializer tableInitializer = new TableInitializer(namedParameterJdbcTemplate());
    tableInitializer.setPathSchema(schema);
    tableInitializer.setPathData(data);
    return tableInitializer;
  }

}
