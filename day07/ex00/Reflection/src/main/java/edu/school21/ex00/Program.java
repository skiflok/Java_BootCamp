package edu.school21.ex00;

import edu.school21.ex00.utils.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    private static final Logger logger = LoggerFactory.getLogger(Program.class);

    public static void main(String[] args) {

        Map<String, Class<?>> classDictionary = new HashMap<>();
        classDictionary.put("int", int.class);
        classDictionary.put("Integer", Integer.class);
        classDictionary.put("long", long.class);
        classDictionary.put("Long", Long.class);
        classDictionary.put("double", double.class);
        classDictionary.put("Double", Double.class);
        classDictionary.put("boolean", boolean.class);
        classDictionary.put("Boolean", Boolean.class);
        classDictionary.put("String", String.class);

        try {

            String classDirectory = "edu.school21.ex00.models";
            List<Class<?>> classes = getClassesInPackage(classDirectory);

            System.out.println("Classes:");
            printClassesInPackage(classes);
            ConsoleHelper.printSeparatingLine();
            ConsoleHelper.writeMessage("Enter class name:");
// TODO: 5/31/23
            String inputClass = ConsoleHelper.readString();
//            String inputClass = "User"; // заглушка
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
            ConsoleHelper.writeMessage("Enter " + changeField.getType().getSimpleName() + " value:");
            String inputChangeValue = ConsoleHelper.readString();
            changeField.setAccessible(true);
            changeField.set(obj, getNewObjectOfGivenTypeAndValue(changeField.getType(), inputChangeValue)
                    .orElseThrow(() -> new IllegalAccessException("Bad parameter")));
            ConsoleHelper.writeMessage(String.format("Object updated: %s", obj));
            ConsoleHelper.printSeparatingLine();

            // TODO: 5/31/23 Enter name of the method for call:
            ConsoleHelper.writeMessage("Enter name of the method for call:");


            String inputMethod = ConsoleHelper.readString();
            String methodType = inputMethod.substring(0, inputMethod.lastIndexOf("("));
            logger.debug("methodType = {}", methodType);
            String methodArgument = inputMethod.substring(inputMethod.indexOf("(") + 1, inputMethod.lastIndexOf(")"));
            logger.debug("methodArgument = {}", methodArgument);
            String [] methodArgs =  Arrays.stream(methodArgument.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

            Arrays.stream(methodArgs).forEach((arg) -> logger.debug("methodArg {} ", arg));

            Class<?>[] methodTypeClasses = Arrays.stream(methodArgs)
                    .map(classDictionary::get)
                    .toArray(Class<?>[]::new);

            logger.debug("methodTypeClasses = {}", Arrays.toString(methodTypeClasses));

            Method method = findClazz.getDeclaredMethod(methodType, methodTypeClasses);
            logger.debug("method = {}", method.getName());

            List<String> inputMethodValues = new ArrayList<>();
            for (Class<?> cl : methodTypeClasses) {
                ConsoleHelper.writeMessage(String.format("Enter %s value:", cl.toString()));
                inputMethodValues.add(ConsoleHelper.readString());
            }

            Arrays.stream(inputMethodValues.toArray()).forEach((arg) -> logger.debug("inputMethodValues {} ", arg));

            method.setAccessible(true);

            Object [] methodParam = new Object[methodTypeClasses.length];

            for (int i = 0; i < methodParam.length; i++) {
                methodParam[i] = getNewObjectOfGivenTypeAndValue(methodTypeClasses[i], inputMethodValues.get(i))
                        .orElseThrow(() -> new IllegalAccessException("Bad parameter"));
            }

            Arrays.stream(methodParam).forEach((arg) -> logger.debug("methodParam {} {} ", arg.getClass().getSimpleName() ,arg));

            ConsoleHelper.writeMessage("Method returned:");
            System.out.println(method.invoke(obj, methodParam));


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
            field.setAccessible(true);
            field.set(obj, getNewObjectOfGivenTypeAndValue(field.getType(), value)
                    .orElseThrow(() -> new IllegalAccessException("Bad parameter")));
        }
        return obj;
    }

    private static Optional<Object> getNewObjectOfGivenTypeAndValue(Class<?> classType, String value) throws IllegalAccessException {

        Optional<Object> resObj = Optional.empty();
        String type = classType.getSimpleName().toLowerCase().substring(0, 3);
        switch (type) {
            case "str":
                resObj = Optional.ofNullable(value);
                break;
            case "int":
                resObj = Optional.of(Integer.parseInt(value));
                break;
            case "dou":
                resObj = Optional.of(Double.parseDouble(value));
                break;
            case "lon":
                resObj = Optional.of(Long.parseLong(value));
                break;
            case "boo":
                resObj = Optional.of(Boolean.parseBoolean(value));
                break;
            default:
                break;
        }
        return resObj;
    }

    private static void printDeclaredMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String printMethod = String.format("\t%s %s(%s)",
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
