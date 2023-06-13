package edu.school21.ex02;

import edu.school21.ex02.models.Car;
import edu.school21.ex02.models.User;
import edu.school21.ex02.orm.OrmManager;

import java.io.IOException;
import java.sql.SQLException;

public class Program {

  public static void main(String[] args) {

    try {
      OrmManager ormManager = new OrmManager("orm");
      System.out.println("#### Program ####");
      System.out.println("#### save ####");
      User user = new User(0L, "John", "Connor", 500);
      ormManager.save(user);
      System.out.println("########################");

      Car car = new Car(0L, "myCar", 999.99);
      ormManager.save(car);

      System.out.println("#### update ####");

      user.setId(3L);
      user.setFirstName("Name");
      user.setLastName("LastName");
      user.setAge(1000);
      ormManager.update(user);

      System.out.println("########################");

      car.setId(3L);
      car.setName("upCar");
      car.setSpeed(0.001);
      ormManager.update(car);

      System.out.println("#### findById ####");

      User findUser = ormManager.findById(5L, User.class);
      System.out.println("findUser = " + findUser);
      Car findCar = ormManager.findById(5L, Car.class);
      System.out.println("findCar = " + findCar);

    } catch (SQLException | IOException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
