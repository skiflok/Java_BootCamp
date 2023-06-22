package edu.school21.chat.app;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.HikariCPDataSource;
import edu.school21.chat.repositories.JdbcTemplate;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;


public class Program {

  public static void main(String[] args) {
    System.out.println("Hello Chat!");

    JdbcTemplate jdbcTemplate = new JdbcTemplate(HikariCPDataSource.getDs());
    MessagesRepository msgRep = new MessagesRepositoryJdbcImpl(jdbcTemplate);

    try {
      reloadDataBaseToDefault(jdbcTemplate);
      Optional<Message> messageOptional = msgRep.findById(1L);
      if (messageOptional.isPresent()) {
        System.out.println(messageOptional);
        Message message = messageOptional.get();
        message.setText("Bye Bye");
        message.setAuthor(new User(1L));
        message.setRoom(new ChatRoom(5));

        message.setDateTime(null);
        msgRep.update(message);
      }
      messageOptional = msgRep.findById(1L);
      messageOptional.ifPresent(System.out::println);

    } catch (NotSavedSubEntityException | SQLException | IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public static void reloadDataBaseToDefault(JdbcTemplate jdbcTemplate)
      throws SQLException, IOException {
//        System.out.println(System.getProperty("user.dir"));
    Path schema = Paths.get("src/ex03/Chat/src/main/resources/schema.sql").normalize()
        .toAbsolutePath();
    Path data = Paths.get("src/ex03/Chat/src/main/resources/data.sql").normalize()
        .toAbsolutePath();
//    Path schema = Paths.get("src/main/resources/schema.sql").normalize().toAbsolutePath();
//    Path data = Paths.get("src/main/resources/data.sql").normalize().toAbsolutePath();
    String schemaSQL = Files.lines(schema).collect(Collectors.joining("\n"));
    String dataSQL = Files.lines(data).collect(Collectors.joining("\n"));

    jdbcTemplate.statement((stmt) -> {
      stmt.executeUpdate(schemaSQL);
      stmt.executeUpdate(dataSQL);
    });

  }
}