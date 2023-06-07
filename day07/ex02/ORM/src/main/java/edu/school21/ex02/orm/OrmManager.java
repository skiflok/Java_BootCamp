package edu.school21.ex02.orm;

import edu.school21.ex02.orm.annotation.OrmColumn;
import edu.school21.ex02.orm.annotation.OrmEntity;
import edu.school21.ex02.repositories.JdbcTemplate;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public void save(Object entity) throws IllegalAccessException, SQLException {

        System.out.println(entity.toString());
        String dataBase = "orm";
        Class<?> clazz = entity.getClass();
//        System.out.println("clazz = " + clazz.getSimpleName());

        OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
        String schema = ormEntity.table();

        String paramNames = Arrays.stream(clazz.getDeclaredFields())
                .map(field -> field.getAnnotation(OrmColumn.class))
                .filter(Objects::nonNull)
                .map(OrmColumn::name)
                .collect(Collectors.joining(", "))
                .trim();

        String paramValues = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(OrmColumn.class))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(entity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(", "))
                .trim();

        String sqlBuilder = String.format("insert into %s.%s (%s)\nvalues (%s)",
                dataBase
                , schema
                , paramNames
                , paramValues
        );

        System.out.println(sqlBuilder);


        JdbcTemplate.statement((stmt) -> {
            stmt.executeUpdate(sqlBuilder);
        });

    }

    public void update(Object entity) {
        String sql = "update chat.message set author = ?, room = ?, text = ?, date_time = ? where id = ?";
    }

    public <T> T findById(Long id, Class<T> aClass) {
        String sql = "select * from chat.message where id = ?";
        return null;
    }

// TODO: 06.06.2023
//    OrmManager должен обеспечивать вывод сгенерированного SQL на
//    консоль во время выполнения.
//   При инициализации OrmManager должен удалить созданные таблицы. +++++++++
//   Метод обновления должен заменять значения в столбцах, указанных в
//    объекте, даже если значение поля объекта равно нулю.

    public void init() throws SQLException, IOException {

//        Path schema = Paths.get("day07/ex02/ORM/target/classes/schema.sql").normalize().toAbsolutePath();
//        Path data = Paths.get("day07/ex02/ORM/target/classes/data.sql").normalize().toAbsolutePath();
//        System.out.println(schema);
//        String schemaSQL = Files.lines(schema).collect(Collectors.joining("\n"));
//        String dataSQL = Files.lines(data).collect(Collectors.joining("\n"));
//        System.out.println(schemaSQL);
//        System.out.println(dataSQL);
//        JdbcTemplate.statement((stmt) -> {
//            stmt.executeUpdate(schemaSQL);
//            stmt.executeUpdate(dataSQL);
//        });

    }

}
