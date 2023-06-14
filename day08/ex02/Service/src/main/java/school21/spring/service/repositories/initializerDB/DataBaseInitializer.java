package school21.spring.service.repositories.initializerDB;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class DataBaseInitializer {

    private final DataSource ds;

    @Autowired
    public DataBaseInitializer( @Qualifier("hikariDataSource") DataSource ds) {
        this.ds = ds;
    }

    public void init() {
        try (Statement statement = ds.getConnection().createStatement()) {

            String sql = Files.lines(Paths.get("day08/ex01/Service/src/main/resources/schema.sql")).collect(Collectors.joining("\n"));
            String data = Files.lines(Paths.get("day08/ex01/Service/src/main/resources/data.sql")).collect(Collectors.joining("\n"));
            System.out.println(sql);
            System.out.println(data);
            statement.executeUpdate(sql);
            statement.executeUpdate(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
