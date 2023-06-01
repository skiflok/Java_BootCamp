package edu.school21.ex00;

import edu.school21.ex00.utils.ConsoleHelper;
import edu.school21.ex00.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {

    private static final Logger logger = LoggerFactory.getLogger(Program.class);

    public static void main(String[] args) {

        try {

            String classDirectory = "edu.school21.ex00.models";
            List<Class<?>> classes = ReflectionUtil.getClassesInPackage(classDirectory);
            System.out.println("Classes:");
            classes.stream().map(Class::getSimpleName).forEach(System.out::println);
            ConsoleHelper.printSeparatingLine();

            ConsoleHelper.writeMessage("Enter class name:");
            String inputClassName = ConsoleHelper.readString();
            ConsoleHelper.printSeparatingLine();

            Class<?> findClazz = ReflectionUtil.findClass(classes, inputClassName);
            ConsoleHelper.writeMessage("fields:");
            Arrays.stream(findClazz.getDeclaredFields())
                    .map(field -> String.format("\t%s %s",
                            field.getType().getSimpleName(),
                            field.getName()))
                    .forEach(System.out::println);

            ConsoleHelper.writeMessage("methods:");
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
        Arrays.stream(clazz.getDeclaredMethods())
                .map(method -> String.format("\t%s %s(%s)",
                        method.getReturnType().getSimpleName(),
                        method.getName(),
                        Arrays.stream(method.getParameters()).map((parameter ->
                                parameter.getType().getSimpleName()
                        )).collect(Collectors.joining(", "))
                ))
                .forEach(System.out::println);
    }
}
