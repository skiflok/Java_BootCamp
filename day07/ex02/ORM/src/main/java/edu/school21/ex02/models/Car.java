package edu.school21.ex02.models;

import edu.school21.ex02.orm.annotation.OrmColumn;
import edu.school21.ex02.orm.annotation.OrmColumnId;
import edu.school21.ex02.orm.annotation.OrmEntity;
import lombok.Data;

@Data
@OrmEntity(table = "car")
public class Car {
    @OrmColumnId
    private Long id;

    @OrmColumn(name = "name")
    private String name;

    @OrmColumn(name = "speed")
    private Double speed;

}
