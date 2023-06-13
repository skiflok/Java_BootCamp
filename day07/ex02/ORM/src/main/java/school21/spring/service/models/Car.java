package school21.spring.service.models;

import school21.spring.service.orm.annotation.OrmColumn;
import school21.spring.service.orm.annotation.OrmColumnId;
import school21.spring.service.orm.annotation.OrmEntity;
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

  public Car() {
  }

}
