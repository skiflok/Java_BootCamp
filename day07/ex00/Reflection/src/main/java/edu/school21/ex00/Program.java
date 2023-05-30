package edu.school21.ex00;

import edu.school21.ex00.utils.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Program {

    private static final Logger logger = LoggerFactory.getLogger(Program.class);

    public static void main(String[] args) {

        try {

//            Path directory = Paths.get("day07/ex00/Reflection/src/main/java/edu/school21/ex00/models").toAbsolutePath().normalize();
//
//            List<Path> filePath = getFilesFromPath(directory);
//
//            Arrays.stream(filePath.toArray()).forEach(System.out::println);
//
//            System.out.println("Classes:");
//
//            Arrays.stream(filePath.toArray()).forEachOrdered((file) -> {
//                System.out.println(file.getClass().getSimpleName());
//            });

            String classDirectory = "edu.school21.ex00.models";
            List<Class<?>> classes = getClassesInPackage(classDirectory);

            System.out.println("Classes:");
            for (Class<?> clazz : classes) {
                System.out.println(clazz.getSimpleName());
            }
            ConsoleHelper.printSeparatingLine();
            ConsoleHelper.writeMessage("Enter class name:");
            String inputClass = ConsoleHelper.readString();
            ConsoleHelper.printSeparatingLine();

            Class<?> findClazz = null;
            for (Class<?> clazz : classes) {
                if (inputClass.equals(clazz.getSimpleName())) {
                    findClazz = clazz;
                    System.out.println("find " + findClazz);
                }
                // Получение информации о классе
                System.out.println("Class Name: " +
//                        clazz.getName()
                                clazz.getSimpleName()
                );
            }

            if (findClazz == null) {
                logger.warn("Class not found");
                throw new RuntimeException();
            }

            ConsoleHelper.printSeparatingLine();
            ConsoleHelper.writeMessage("fields:");

            Field [] fields = findClazz.getDeclaredFields();
            for (Field field : fields) {
                ConsoleHelper.writeMessage("\t" + field.getType().getSimpleName());

            }



//            for (Class<?> clazz : classes) {
//                // Получение информации о классе
//                System.out.println("Class Name: " +
////                        clazz.getName()
//                                clazz.getSimpleName()
//                );
//                System.out.println("Package Name: " + clazz.getPackage().getName());
//
//                System.out.println("Methods:");
//                for (Method method : clazz.getDeclaredMethods()) {
//                    System.out.println(method.getName());
//                }
//
//                System.out.println();
//            }

        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<Class<?>> getClassesInPackage(String packagePath) throws IOException, ClassNotFoundException, URISyntaxException {
        String packageDirectory = packagePath.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageUrl = classLoader.getResource(packageDirectory);

        if (packageUrl == null) {
            throw new IllegalArgumentException("Package not found: " + packagePath);
        }

        if (!Files.isDirectory(Paths.get(packageUrl.toURI()))) {
            throw new NotDirectoryException("is not directory");
        }

        List<Class<?>> classes = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(packageUrl.toURI()))) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    String className = String.format(
                            "%s.%s",
                            packagePath,
                            path.getFileName().toString().substring(0, path.getFileName().toString().lastIndexOf('.'))
                    );
                    classes.add(Class.forName(className));
                }
            }
        }

        return classes;
    }
}

