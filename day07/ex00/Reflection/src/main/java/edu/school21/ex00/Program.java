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
import java.util.*;
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
            Object obj = createObject(findClazz);
            ConsoleHelper.writeMessage(String.format("Object created: %s", obj));
            ConsoleHelper.printSeparatingLine();

            List<Field> fieldList = Arrays.stream(findClazz.getDeclaredFields()).collect(Collectors.toList());
            ConsoleHelper.writeMessage("Enter name of the field for changing:");
            String changeFieldName = ConsoleHelper.readString();
            System.out.println(changeFieldName);
            Optional<Field> optionalField = fieldList.stream()
                    .filter(field -> changeFieldName.equals(field.getName()))
                            .findFirst();
            if (!optionalField.isPresent()) {
                throw new IllegalArgumentException("Field not found");
            }
            Field changeField = optionalField.get();
            ConsoleHelper.writeMessage("Enter "+ changeField.getType().getSimpleName() + " value:");
            String inputChangeValue = ConsoleHelper.readString();
            setFieldValue(obj, changeField, inputChangeValue);
            ConsoleHelper.writeMessage(String.format("Object updated: %s", obj));
            ConsoleHelper.printSeparatingLine();

            // TODO: 5/31/23 Enter name of the method for call:
            ConsoleHelper.writeMessage("Enter name of the method for call:");

            ConsoleHelper.writeMessage("Enter int value:");

            ConsoleHelper.writeMessage("Method returned:");

        } catch (NumberFormatException | IOException | ClassNotFoundException | URISyntaxException |
                 InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            logger.warn("Ошибка выполнения " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Object createObject(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object obj = clazz.getConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("\t" + field.getName());
            String value = ConsoleHelper.readString();
            setFieldValue(obj, field, value);
        }
        return obj;
    }

    private static void setFieldValue (Object obj, Field field, String value) throws IllegalAccessException {

        String type = field.getType().getSimpleName().toLowerCase().substring(0, 3);
        field.setAccessible(true);
        switch (type) {
            case "str":
                field.set(obj, value);
                break;
            case "int":
                field.setInt(obj, Integer.parseInt(value));
                break;
            case "dou":
                field.setDouble(obj, Double.parseDouble(value));
                break;
            case "lon":
                field.setLong(obj, Long.parseLong(value));
                break;
            case "boo":
                field.setBoolean(obj, Boolean.parseBoolean(value));
                break;
            default:
                break;
        }
    }

    private static int getMaxConstructorParameters(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        return Arrays.stream(constructors)
                .map(Constructor::getParameterCount)
                .max(Integer::compare)
                .orElse(0);
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
            ConsoleHelper.writeMessage("\t" + field.getType().getSimpleName() + " " + field.getName());
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
