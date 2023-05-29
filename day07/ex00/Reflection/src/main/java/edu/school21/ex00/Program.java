package edu.school21.ex00;

import edu.school21.ex00.models.User;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        try {
            System.out.println("Reflection");

            Path directory = Paths.get("day07/ex00/Reflection/src/main/java/edu/school21/ex00/models").toAbsolutePath().normalize();

            List<Path> filePath = getFilesFromPath(directory);

            Arrays.stream(filePath.toArray()).forEach(System.out::println);

            System.out.println("Classes:");

            Arrays.stream(filePath.toArray()).forEachOrdered((file) -> {
                System.out.println(file.getClass().getSimpleName());
            });

            String classDirectory = "edu.school21.ex00.models";
//            List<Class<?>> classes = getClassesInPackage(classDirectory);
//
//            for (Class<?> clazz : classes) {
//                // Получение информации о классе
//                System.out.println("Class Name: " + clazz.getName());
//                System.out.println("Package Name: " + clazz.getPackage().getName());
//
//                System.out.println("Methods:");
//                for (Method method : clazz.getDeclaredMethods()) {
//                    System.out.println(method.getName());
//                }
//
//                System.out.println();
//            }

        } catch (NotDirectoryException e) {
            e.printStackTrace();
        }

    }

    private static List<Path> getFilesFromPath (Path directory) throws NotDirectoryException {
        if (!Files.isDirectory(directory)) {
            throw new NotDirectoryException("is not directory");
        }
        List<Path> pathList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    pathList.add(path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathList;
    }

}

