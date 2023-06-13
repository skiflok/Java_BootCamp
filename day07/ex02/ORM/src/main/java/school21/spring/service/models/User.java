package school21.spring.service.models;

import school21.spring.service.orm.annotation.OrmColumn;
import school21.spring.service.orm.annotation.OrmColumnId;
import school21.spring.service.orm.annotation.OrmEntity;
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