package edu.school21.ex02.models;

import edu.school21.ex02.annotation.OrmColumn;
import edu.school21.ex02.annotation.OrmColumnId;
import edu.school21.ex02.annotation.OrmEntity;
import lombok.Data;

@Data
@OrmEntity(table = "car")
public class Car {
    @OrmColumnId
    private Long id;

    @OrmColumn(name = "name")
    private String name;

    @OrmColumn(name = "length")
    private Integer length;

    @OrmColumn(name = "width")
    private Integer width;

    @OrmColumn(name = "speed")
    private Double speed;

}
