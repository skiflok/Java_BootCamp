package edu.school21.ex00;

import edu.school21.ex00.utils.ConsoleHelper;
import edu.school21.ex00.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.*;
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
            List<Class<?>> classes = ReflectionUtil.getClassesInPackage(classDirectory);

            printClassesInPackage(classes);
            ConsoleHelper.printSeparatingLine();

            ConsoleHelper.writeMessage("Enter class name:");
            String inputClassName = ConsoleHelper.readString();
            ConsoleHelper.printSeparatingLine();

            Class<?> findClazz = ReflectionUtil.findClass(classes, inputClassName);

            printClassDeclaredField(findClazz);

            printDeclaredMethods(findClazz);
            ConsoleHelper.printSeparatingLine();

            Object obj = ReflectionUtil.createObject(findClazz);
            ConsoleHelper.printSeparatingLine();

            ReflectionUtil.changeField(obj, findClazz);
            ConsoleHelper.printSeparatingLine();

            ReflectionUtil.callMethod(obj, findClazz);


        } catch (NumberFormatException | IOException | ClassNotFoundException | URISyntaxException |
                 InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            logger.warn("Ошибка выполнения " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printDeclaredMethods(Class<?> clazz) {
        ConsoleHelper.writeMessage("methods:");
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
        ConsoleHelper.writeMessage("fields:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ConsoleHelper.writeMessage("\t" + field.getType().getSimpleName() + " " + field.getName());
        }
    }

    private static void printClassesInPackage(List<Class<?>> classes) {
        System.out.println("Classes:");
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getSimpleName());
        }
    }

}
