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

            User user = new User(0L, "John", "Connor" , 500);
            ormManager.save(user);

            System.out.println("\n########################\n");

            Car car = new Car(0L, "myCar", 999.99);
            ormManager.save(car);

        } catch (SQLException | IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
