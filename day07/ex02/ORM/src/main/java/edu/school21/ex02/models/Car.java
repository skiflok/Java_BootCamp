package edu.school21.ex02.models;

import edu.school21.ex02.orm.annotation.OrmColumn;
import edu.school21.ex02.orm.annotation.OrmColumnId;
import edu.school21.ex02.orm.annotation.OrmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@OrmEntity(table = "my_car")
public class Car {
    @OrmColumnId
    private Long id;

    @OrmColumn(name = "car_name")
    private String name;

    @OrmColumn(name = "car_speed")
    private Double speed;

}
