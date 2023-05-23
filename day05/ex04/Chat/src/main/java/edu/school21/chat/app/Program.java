package edu.school21.chat.app;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Program {
    public static void main(String[] args) {
        System.out.println("Hello Chat!");

        MessagesRepository msgRep = new MessagesRepositoryJdbcImpl();

        try {
//            reloadDataBaseToDefault();

            UsersRepository usersRepository = new UsersRepositoryJdbcImpl();
            List<User> users = usersRepository.findAll(4, 4);

            for (User user : users) {
                System.out.println(user);
            }

        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
