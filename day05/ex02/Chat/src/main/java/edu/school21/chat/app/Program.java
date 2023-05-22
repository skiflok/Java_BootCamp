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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Program {
    public static void main(String[] args) {
        System.out.println("Hello Chat!");

        MessagesRepository msgRep = new MessagesRepositoryJdbcImpl();

        try {
//            reloadDataBaseToDefault();
            User creator = new User(7L, "user", "user", new ArrayList(), new ArrayList());
            ChatRoom room = new ChatRoom(8L, "room", creator, new ArrayList());
            Message message = new Message(null, creator, room, "Hello!", LocalDateTime.now());
            msgRep.save(message);
            System.out.println(message.getId());

        } catch (NotSavedSubEntityException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void reloadDataBaseToDefault() throws IOException, SQLException {
//        System.out.println(System.getProperty("user.dir"));
        Path schema = Paths.get("day05/ex02/Chat/src/main/resources/schema.sql").normalize().toAbsolutePath();
        Path data = Paths.get("day05/ex02/Chat/src/main/resources/data.sql").normalize().toAbsolutePath();
//        System.out.println(schema);
        String schemaSQL = Files.lines(schema).collect(Collectors.joining("\n"));
        String dataSQL = Files.lines(data).collect(Collectors.joining("\n"));

        JdbcTemplate.statement((stmt) -> {
            stmt.executeUpdate(schemaSQL);
            stmt.executeUpdate(dataSQL);
        });

    }
}
