package edu.school21.ex02.orm;

import edu.school21.ex02.repositories.JdbcTemplate;
import java.sql.SQLException;

public class OrmManager {

    public OrmManager() throws SQLException {
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

    public void init() throws SQLException {

        String SQL = "drop schema if exists orm cascade;\n"
            + "create schema if not exists orm;\n";
        JdbcTemplate.statement((stmt) -> {
            stmt.execute(SQL);
            System.out.println(SQL);
        });
    }

}
