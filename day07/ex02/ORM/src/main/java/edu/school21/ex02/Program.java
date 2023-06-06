package edu.school21.ex02;

import edu.school21.ex02.orm.OrmManager;
import java.io.IOException;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {

        try {
            OrmManager ormManager = new OrmManager();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }
}
