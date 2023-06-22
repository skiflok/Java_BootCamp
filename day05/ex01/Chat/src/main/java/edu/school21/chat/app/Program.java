package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.HikariCPDataSource;
import edu.school21.chat.repositories.JdbcTemplate;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        try (BufferedReader bis = new BufferedReader(new InputStreamReader(System.in))) {

            reloadDataBaseToDefault(jdbcTemplate);
            System.out.println("Enter a message ID");
            Long inputId = Long.parseLong(bis.readLine());

            Optional<Message> message = msgRep.findById(inputId);

            if (message.isPresent()) {
                System.out.println(message.get());
            } else {
                System.out.println("Message not found");
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void reloadDataBaseToDefault(JdbcTemplate jdbcTemplate) throws IOException, SQLException {
//        System.out.println(System.getProperty("user.dir"));
//        Path schema = Paths.get("src/ex01/Chat/src/main/resources/schema.sql").normalize().toAbsolutePath();
//        Path data = Paths.get("src/ex01/Chat/src/main/resources/data.sql").normalize().toAbsolutePath();
        Path schema = Paths.get("src/main/resources/schema.sql").normalize().toAbsolutePath();
        Path data = Paths.get("src/main/resources/data.sql").normalize().toAbsolutePath();
//        System.out.println(schema);
        String schemaSQL = Files.lines(schema).collect(Collectors.joining("\n"));
        String dataSQL = Files.lines(data).collect(Collectors.joining("\n"));

        jdbcTemplate.statement((stmt) -> {
            stmt.executeUpdate(schemaSQL);
            stmt.executeUpdate(dataSQL);
        });

    }

}
