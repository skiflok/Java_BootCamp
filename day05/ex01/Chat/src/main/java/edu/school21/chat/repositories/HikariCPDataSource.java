package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {


    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/chatDataBase");
        config.setUsername("postgres");
        config.setPassword("admin");
        System.out.println("config.getMaximumPoolSize() " + config.getMaximumPoolSize());
        ds = new HikariDataSource(config);
    }


    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
