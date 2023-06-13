package edu.school21.ex02.orm;

import edu.school21.ex02.orm.annotation.OrmColumn;
import edu.school21.ex02.repositories.JdbcTemplate;
import edu.school21.ex02.orm.annotation.OrmColumnId;
import edu.school21.ex02.orm.annotation.OrmEntity;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrmManager {

  private final String dataBaseName;

  public OrmManager(String dataBaseName) throws SQLException, IOException {
    this.dataBaseName = dataBaseName;
    init();
  }

  public void save(Object entity) throws IllegalAccessException, SQLException {

    Class<?> clazz = entity.getClass();

    OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
    String schema = ormEntity.table();

    StringBuilder paramNamesBuilder = new StringBuilder();
    StringBuilder paramPlaceholdersBuilder = new StringBuilder();
    List<Object> paramValues = new ArrayList<>();
    List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());

    for (Field field : fields) {
      if (field.isAnnotationPresent(OrmColumn.class)) {
        field.setAccessible(true);
        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
        paramNamesBuilder.append(ormColumn.name()).append(", ");
        paramPlaceholdersBuilder.append("?, ");
        paramValues.add(field.get(entity));
      }
    }

    String paramNames = paramNamesBuilder.substring(0, paramNamesBuilder.length() - 2);
    String paramPlaceholders = paramPlaceholdersBuilder
        .substring(0, paramPlaceholdersBuilder.length() - 2);

    String sql = String.format("insert into %s.%s (%s)\nvalues (%s)",
        dataBaseName
        , schema
        , paramNames
        , paramPlaceholders
    );

    JdbcTemplate.preparedStatement(sql, (stmt) -> {
      for (int i = 0; i < paramValues.size(); i++) {
        stmt.setObject(i + 1, paramValues.get(i));
      }
      stmt.executeUpdate();
      System.out.println(stmt.unwrap(PreparedStatement.class)
          .toString().replace("RETURNING *", ""));
    });
  }

  public void update(Object entity) throws IllegalAccessException, SQLException {

    Class<?> clazz = entity.getClass();
    OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
    String schema = ormEntity.table();
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("update ").append(dataBaseName).append(".").append(schema).append(" set ");
    List<Object> paramValues = new ArrayList<>();
    List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());

    Object id = null;
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(OrmColumnId.class)) {
        id = field.get(entity);
      }
      if (field.isAnnotationPresent(OrmColumn.class)) {
        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
        sqlBuilder.append(ormColumn.name()).append(" = ?, ");
        paramValues.add(field.get(entity));
      }
    }
    sqlBuilder.setLength(sqlBuilder.length() - 2);
    sqlBuilder.append(" where id = ?");

    Object finalId = id;
    JdbcTemplate.preparedStatement(sqlBuilder.toString(), (stmt) -> {
      for (int i = 0; i < paramValues.size(); i++) {
        stmt.setObject(i + 1, paramValues.get(i));
      }
      stmt.setObject(paramValues.size() + 1, finalId);
      stmt.executeUpdate();
      System.out.println(stmt.unwrap(PreparedStatement.class)
          .toString().replace("RETURNING *", ""));
    });

  }

  public <T> T findById(Long id, Class<T> aClass) throws SQLException {

    OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
    String schema = ormEntity.table();

    String sql = String.format("select * from %s.%s where id = ?"
        , dataBaseName
        , schema
    );

    return JdbcTemplate.preparedStatement(sql, (stmt) -> {
      stmt.setLong(1, id);
      System.out.println(stmt.unwrap(PreparedStatement.class)
          .toString().replace("RETURNING *", ""));
      ResultSet resultSet = stmt.executeQuery();

      if (!resultSet.next()) {
        return null;
      }

      T object = null;
      try {
        object = aClass.getDeclaredConstructor().newInstance();

        for (Field field : aClass.getDeclaredFields()) {
          field.setAccessible(true);
          Object value = null;
          if (field.isAnnotationPresent(OrmColumnId.class)) {
            value = resultSet.getObject(field.getAnnotation(OrmColumnId.class).id());
          }
          if (field.isAnnotationPresent(OrmColumn.class)) {
            value = resultSet.getObject(field.getAnnotation(OrmColumn.class).name());
          }

          field.set(object, value);
        }
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
               NoSuchMethodException e) {
        e.printStackTrace();
      }
      return object;
    });

  }

  public void init() throws SQLException, IOException {

    Path schema = Paths.get("day07/ex02/ORM/target/classes/schema.sql").normalize()
        .toAbsolutePath();
    Path data = Paths.get("day07/ex02/ORM/target/classes/data.sql").normalize().toAbsolutePath();
    String schemaSQL = Files.lines(schema).collect(Collectors.joining("\n"));
    String dataSQL = Files.lines(data).collect(Collectors.joining("\n"));
    System.out.println(schemaSQL);
    System.out.println(dataSQL);
    JdbcTemplate.statement((stmt) -> {
      stmt.executeUpdate(schemaSQL);
      stmt.executeUpdate(dataSQL);
    });
  }
}
