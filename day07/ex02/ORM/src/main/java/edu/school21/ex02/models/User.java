package edu.school21.ex02.models;

import edu.school21.ex02.orm.annotation.OrmColumn;
import edu.school21.ex02.orm.annotation.OrmColumnId;
import edu.school21.ex02.orm.annotation.OrmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@OrmEntity(table = "simple_user")
public class User {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "first_name", length = 10)
    private String firstName;
    @OrmColumn(name = "last_name", length = 10)
    private String lastName;
    @OrmColumn(name = "age")
    private Integer age;

    public User() {}
}