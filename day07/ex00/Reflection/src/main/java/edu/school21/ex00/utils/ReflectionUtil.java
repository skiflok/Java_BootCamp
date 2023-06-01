package edu.school21.ex00.utils;

import edu.school21.ex00.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    private static final Map<String, Class<?>> classDictionary;

    static {
        classDictionary = new HashMap<>();

        classDictionary.put("int", int.class);
        classDictionary.put("Integer", Integer.class);
        classDictionary.put("long", long.class);
        classDictionary.put("Long", Long.class);
        classDictionary.put("double", double.class);
        classDictionary.put("Double", Double.class);
        classDictionary.put("boolean", boolean.class);
        classDictionary.put("Boolean", Boolean.class);
        classDictionary.put("String", String.class);
    }


    private ReflectionUtil() {
    }

    public static Map<String, Class<?>> getClassDictionary() {
        return classDictionary;
    }

    public static Class<?> findClass(List<Class<?>> classes, String inputClassName) {
        Class<?> findClazz = null;
        for (Class<?> clazz : classes) {
            if (inputClassName.equals(clazz.getSimpleName())) {
                findClazz = clazz;
                logger.debug("find: {}", findClazz.getSimpleName());
                break;
            }
        }

        if (findClazz == null) {
            logger.warn("Class not found");
            throw new RuntimeException();
        }
        return findClazz;
    }

    public static Object createObject(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ConsoleHelper.writeMessage("Let’s create an object.");
        Object obj = clazz.getConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            String value = ConsoleHelper.readString();
            field.setAccessible(true);
            field.set(obj, getNewObjectOfGivenTypeAndValue(field.getType(), value)
                    .orElseThrow(() -> new IllegalAccessException("Bad parameter")));
        }
        ConsoleHelper.writeMessage(String.format("Object created: %s", obj));
        return obj;
    }

    public static Optional<Object> getNewObjectOfGivenTypeAndValue(Class<?> classType, String value) throws IllegalAccessException {

        Optional<Object> optional = Optional.empty();
        String type = classType.getSimpleName().toLowerCase().substring(0, 3);
        switch (type) {
            case "str":
                optional = Optional.ofNullable(value);
                break;
            case "int":
                optional = Optional.of(Integer.parseInt(value));
                break;
            case "dou":
                optional = Optional.of(Double.parseDouble(value));
                break;
            case "lon":
                optional = Optional.of(Long.parseLong(value));
                break;
            case "boo":
                optional = Optional.of(Boolean.parseBoolean(value));
                break;
            default:
                break;
        }
        return optional;
    }

    public static void changeField(Object obj, Class<?> clazz) throws IllegalAccessException {
        List<Field> fieldList = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
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
    }

    public static void callMethod(Object obj, Class<?> clazz) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

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
                .map(ReflectionUtil.getClassDictionary()::get)
                .toArray(Class<?>[]::new);

        logger.debug("methodTypeClasses = {}", Arrays.toString(methodTypeClasses));

        Method method = clazz.getDeclaredMethod(methodType, methodTypeClasses);
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
            methodParam[i] = ReflectionUtil.getNewObjectOfGivenTypeAndValue(methodTypeClasses[i], inputMethodValues.get(i))
                    .orElseThrow(() -> new IllegalAccessException("Bad parameter"));
        }
        Arrays.stream(methodParam).forEach((arg) -> logger.debug("methodParam {} {} ", arg.getClass().getSimpleName() ,arg));

        ConsoleHelper.writeMessage("Method returned:");
        System.out.println(method.invoke(obj, methodParam));
    }

    public static List<Class<?>> getClassesInPackage(String packagePath) throws IOException, ClassNotFoundException, URISyntaxException {
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
