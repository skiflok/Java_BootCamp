package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class Program {

  public static void main(String[] args) {
    System.out.println("Hello Chat!");

    JdbcTemplate jdbcTemplate = new JdbcTemplate(HikariCPDataSource.getDs());

    try {
      reloadDataBaseToDefault(jdbcTemplate);

      System.out.println("##########################");
      int page = 4;
      int size = 4;
      System.out.printf("page = %d, size = %d\n", page, size);
      UsersRepository usersRepository = new UsersRepositoryJdbcImpl(jdbcTemplate);
      List<User> users = usersRepository.findAll(page, size);
      users.forEach(System.out::println);

      System.out.println("##########################");
      page = 1;
      size = 2;
      System.out.printf("page = %d, size = %d\n", page, size);
      users = usersRepository.findAll(page, size);
      users.forEach(System.out::println);

      System.out.println("##########################");
      page = 5;
      size = 5;
      System.out.printf("page = %d, size = %d\n", page, size);
      users = usersRepository.findAll(page, size);
      users.forEach(System.out::println);

      System.out.println("##########################");
      page = 3;
      size = 3;
      System.out.printf("page = %d, size = %d\n", page, size);
      users = usersRepository.findAll(page, size);
      users.forEach(System.out::println);

    } catch (NotSavedSubEntityException | SQLException | IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static void reloadDataBaseToDefault(JdbcTemplate jdbcTemplate)
      throws IOException, SQLException {
//        System.out.println(System.getProperty("user.dir"));
//    Path schema = Paths.get("src/ex04/Chat/src/main/resources/schema.sql").normalize()
//        .toAbsolutePath();
//    Path data = Paths.get("src/ex04/Chat/src/main/resources/data.sql").normalize()
//        .toAbsolutePath();

    Path schema = Paths.get("src/main/resources/schema.sql").normalize()
        .toAbsolutePath();
    Path data = Paths.get("src/main/resources/data.sql").normalize()
        .toAbsolutePath();
//        System.out.println(schema);
    String schemaSQL = Files.lines(schema).collect(Collectors.joining("\n"));
    String dataSQL = Files.lines(data).collect(Collectors.joining("\n"));

    jdbcTemplate.statement((stmt) -> {
      stmt.executeUpdate(schemaSQL);
      stmt.executeUpdate(dataSQL);
    });
  }
}
