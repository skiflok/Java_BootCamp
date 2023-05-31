package edu.school21.ex00;

import edu.school21.ex00.utils.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Program {

    private static final Logger logger = LoggerFactory.getLogger(Program.class);

    public static void main(String[] args) {

        try {

            String classDirectory = "edu.school21.ex00.models";
            List<Class<?>> classes = getClassesInPackage(classDirectory);

            System.out.println("Classes:");

            printClassesInPackage(classes);

            ConsoleHelper.printSeparatingLine();

            ConsoleHelper.writeMessage("Enter class name:");
// TODO: 5/31/23
//            String inputClass = ConsoleHelper.readString();
            String inputClass = "User"; // заглушка

            ConsoleHelper.printSeparatingLine();

            Class<?> findClazz = findClass(classes, inputClass);

            ConsoleHelper.printSeparatingLine();
            ConsoleHelper.writeMessage("fields:");

            printClassDeclaredField(findClazz);

            ConsoleHelper.writeMessage("methods:");

            printDeclaredMethods(findClazz);

            ConsoleHelper.printSeparatingLine();

            ConsoleHelper.writeMessage("Let’s create an object.");


            // TODO: 5/31/23 create object





            Constructor<?>[] constructors = findClazz.getDeclaredConstructors();

            for (Constructor<?> constructor : constructors) {
                System.out.println(Arrays.stream(constructor.getParameters()).map(p ->
                        p.getType().getSimpleName()
                ).collect(Collectors.joining(", ")));
            }

            Object object = constructors[0].newInstance();

            ConsoleHelper.writeMessage(object.toString());
            ConsoleHelper.printSeparatingLine();

            // TODO: 5/31/23 Enter name of the field for changing

            ConsoleHelper.writeMessage("Enter name of the field for changing:");

            ConsoleHelper.writeMessage("Object updated:");
            ConsoleHelper.printSeparatingLine();

            // TODO: 5/31/23 Enter name of the method for call:
            ConsoleHelper.writeMessage("Enter name of the method for call:");

            ConsoleHelper.writeMessage("Enter int value:");

            ConsoleHelper.writeMessage("Method returned:");



        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printDeclaredMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String printMethod = String.format("\t%s %s (%s)",
                    method.getReturnType().getSimpleName(),
                    method.getName(),
                    Arrays.stream(method.getParameters()).map((parameter ->
                            parameter.getType().getSimpleName()
                    )).collect(Collectors.joining(", "))
            );
            ConsoleHelper.writeMessage(printMethod);
        }
    }
    private static void printClassDeclaredField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ConsoleHelper.writeMessage("\t" + field.getType().getSimpleName());
        }
    }

    private static Class<?> findClass(List<Class<?>> classes, String inputClass) {
        Class<?> findClazz = null;
        for (Class<?> clazz : classes) {
            if (inputClass.equals(clazz.getSimpleName())) {
                findClazz = clazz;
                // TODO: 5/31/23 delete print
                System.out.println("find " + findClazz.getSimpleName());
                break;
            }
        }

        if (findClazz == null) {
            logger.warn("Class not found");
            throw new RuntimeException();
        }
        return findClazz;
    }

    private static void printClassesInPackage(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getSimpleName());
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
