package edu.school21.chat.app;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
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

        MessagesRepository msgRep = new MessagesRepositoryJdbcImpl();

        try {
//            reloadDataBaseToDefault();
            Optional<Message> messageOptional = msgRep.findById(11L);
            if (messageOptional.isPresent()) {
                System.out.println(messageOptional);
                Message message = messageOptional.get();
                message.setText("Bye Bye");
                message.setAuthor(new User(1L));
                message.setRoom(new ChatRoom(2));

                message.setDateTime(null);
                msgRep.update(message);
            }
            messageOptional = msgRep.findById(11L);
            if (messageOptional.isPresent()) {
                System.out.println(messageOptional);
            }

        } catch (NotSavedSubEntityException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
//        catch (IOException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
    }

    public static void reloadDataBaseToDefault() throws IOException, SQLException {
//        System.out.println(System.getProperty("user.dir"));
        Path schema = Paths.get("day05/ex04/Chat/src/main/resources/schema.sql").normalize().toAbsolutePath();
        Path data = Paths.get("day05/ex04/Chat/src/main/resources/data.sql").normalize().toAbsolutePath();
//        System.out.println(schema);
        String schemaSQL = Files.lines(schema).collect(Collectors.joining("\n"));
        String dataSQL = Files.lines(data).collect(Collectors.joining("\n"));

        JdbcTemplate.statement((stmt) -> {
            stmt.executeUpdate(schemaSQL);
            stmt.executeUpdate(dataSQL);
        });

    }
}
