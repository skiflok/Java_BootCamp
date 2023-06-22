package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariCPDataSource {

  private static final DataSource ds;

  private HikariCPDataSource() {
  }

  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/chatDataBase");
    config.setUsername("postgres");
    config.setPassword("admin");
    config.setMaximumPoolSize(4);
    ds = new HikariDataSource(config);
  }

  public static DataSource getDs() {
    return ds;
  }
}
