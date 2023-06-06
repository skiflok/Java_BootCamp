package edu.school21.ex02.orm;

import edu.school21.ex02.repositories.JdbcTemplate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class OrmManager {

    public OrmManager() throws SQLException, IOException {
        init();
    }

    // TODO: 06.06.2023
    //    Разработанный вами класс OrmManager должен генерировать и
    //  выполнять соответствующий код SQL при инициализации всех классов,
    //  помеченных аннотацией @OrmEntity. Этот код будет содержать
    //  команду CREATE TABLE для создания таблицы с именем, указанным в
    //  аннотации. Каждое поле класса, помеченное аннотацией @OrmColumn,
    //  становится столбцом в этой таблице. Поле, помеченное аннотацией
    //  @OrmColumnId, указывает на необходимость создания идентификатора
    //  автоинкремента. OrmManager также должен поддерживать следующие
    //  набор операций (для каждой из них также генерируется
    //  соответствующий код SQL в Runtime)

    public void save(Object entity) {

    }

    public void update(Object entity) {

    }

    public <T> T findById(Long id, Class<T> aClass) {
        return null;
    }

// TODO: 06.06.2023
//    OrmManager должен обеспечивать вывод сгенерированного SQL на
//    консоль во время выполнения.
//   При инициализации OrmManager должен удалить созданные таблицы. +++++++++
//   Метод обновления должен заменять значения в столбцах, указанных в
//    объекте, даже если значение поля объекта равно нулю.

    public void init() throws SQLException, IOException {

//        String filePath = "day07/ex02/ORM/target/classes/schema.sql";
//        String sqlInit = new String(Files.readAllBytes(Paths.get(filePath)));
//        String sqlData = new String(Files.readAllBytes(Paths.get("day07/ex02/ORM/src/main/resources/data.sql")));

        Path schema = Paths.get("day07/ex02/ORM/target/classes/schema.sql").normalize().toAbsolutePath();
        Path data = Paths.get("day07/ex02/ORM/target/classes/data.sql").normalize().toAbsolutePath();
//        System.out.println(schema);
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
