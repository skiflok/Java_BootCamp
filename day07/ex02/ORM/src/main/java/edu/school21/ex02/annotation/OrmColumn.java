package edu.school21.ex02.annotation;

public @interface OrmColumn {
    String name();
    int length() default 20;
}
