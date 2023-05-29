package edu.school21.ex00;

import edu.school21.ex00.models.User;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Program {

    public static void main(String[] args) {
        System.out.println("Reflection");

        User user = new User();

        System.out.println(user);

        System.out.println("#########");

        Field[] fields = user.getClass().getDeclaredFields();

        Arrays.stream(fields).forEach(System.out::println);

        Class<?> clazz = user.getClass();

        System.out.println(clazz);

        Method [] methods = clazz.getDeclaredMethods();

        Arrays.stream(methods).forEach(System.out::println);

        Path currentDirectory = Paths.get("day07/ex00/Reflection/src/main/java/edu/school21/ex00/models").toAbsolutePath().normalize();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {

                } else {
                    System.out.printf("%s %d KB\n", file.getFileName(), Files.size(file) / 1024);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

