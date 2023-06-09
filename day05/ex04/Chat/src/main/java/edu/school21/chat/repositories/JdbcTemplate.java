package edu.school21.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import javax.sql.DataSource;

public class JdbcTemplate {

  private final DataSource ds;

  public JdbcTemplate(DataSource ds) {
    this.ds = ds;
  }

  @FunctionalInterface
  public interface SQLFunction<T, R> {

    R apply(T object) throws SQLException;
  }

  @FunctionalInterface
  public interface SQLConsumer<T> {

    void accept(T object) throws SQLException;
  }

  public void connection(SQLConsumer<? super Connection> consumer) throws SQLException {
    Objects.requireNonNull(consumer);
    try (Connection conn = ds.getConnection()) {
      consumer.accept(conn);
    }
  }

  public <R> R connection(SQLFunction<? super Connection, ? extends R> function)
      throws SQLException {
    Objects.requireNonNull(function);
    try (Connection conn = ds.getConnection()) {
      return function.apply(conn);
    }
  }

  public <R> R statement(SQLFunction<? super Statement, ? extends R> function) throws SQLException {
    Objects.requireNonNull(function);
    return connection(conn -> {
      try (Statement stmt = conn.createStatement()) {
        return function.apply(stmt);
      }
    });
  }

  public void statement(SQLConsumer<? super Statement> consumer) throws SQLException {
    Objects.requireNonNull(consumer);
    connection(conn -> {
      try (Statement stmt = conn.createStatement()) {
        consumer.accept(stmt);
      }
    });
  }

  public <R> R preparedStatement(String sql,
      SQLFunction<? super PreparedStatement, ? extends R> function) throws SQLException {
    Objects.requireNonNull(function);
    return connection(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        return function.apply(stmt);
      }
    });
  }

  public void preparedStatement(String sql, SQLConsumer<? super PreparedStatement> consumer)
      throws SQLException {
    Objects.requireNonNull(consumer);
    connection(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        consumer.accept(stmt);
      }
    });
  }
}
