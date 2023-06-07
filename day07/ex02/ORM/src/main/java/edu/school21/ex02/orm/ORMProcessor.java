package edu.school21.ex02.orm;

import edu.school21.ex02.orm.annotation.OrmColumn;
import edu.school21.ex02.orm.annotation.OrmColumnId;
import edu.school21.ex02.orm.annotation.OrmEntity;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@SupportedAnnotationTypes(value = {
    "edu.school21.ex02.orm.annotation.OrmEntity"
    , "edu.school21.ex02.orm.annotation.OrmColumn"
    , "edu.school21.ex02.orm.annotation.OrmColumnId"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ORMProcessor extends AbstractProcessor {

  private final static Map<String, String> sqlTypes = new HashMap<>();
  private final String schemaName;
  Path outputPath;
  String fileName;

  {
    sqlTypes.put("String", "varchar");
    sqlTypes.put("Integer", "int");
    sqlTypes.put("Double", "double precision");
    sqlTypes.put("Boolean", "bool");
    sqlTypes.put("Long", "bigint");
    schemaName = "orm";
    fileName = "schema.sql";
    outputPath = Paths.get("target/classes", fileName);
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    if (annotations.size() == 0) {
      return false;
    }

    Elements elements = processingEnv.getElementUtils();
    StringBuilder schema = new StringBuilder();
    schema.append("drop schema if exists ")
        .append(schemaName)
        .append(" cascade;\n")
        .append("create schema if not exists ")
        .append(schemaName)
        .append(";\n");

    for (Element element : roundEnv.getElementsAnnotatedWith(OrmEntity.class)) {

      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        OrmEntity ormEntity = typeElement.getAnnotation(OrmEntity.class);
        schema.append("create table if not exists ")
            .append(schemaName).append(".").append(ormEntity.table()).append(" (\n");

        List<? extends Element> fieldElements = typeElement.getEnclosedElements();
        String columns = fieldElements.stream()
            .map(fieldElement -> {
              if (fieldElement.getAnnotation(OrmColumnId.class) != null) {
                return fieldElement.getAnnotation(OrmColumnId.class).id() + " " + getAutoIncrementType(elements, fieldElement);
              }
              if (fieldElement.getAnnotation(OrmColumn.class) != null) {
                return fieldElement.getAnnotation(OrmColumn.class).name() + " " + appendType(elements, fieldElement);
              }
              return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.joining(",\n"));

        schema.append(columns)
            .append(");\n");
      }
    }

    System.out.println(schema);
    try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
      writer.write(schema.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  private String getAutoIncrementType(Elements elements, Element element) {
    if ("Integer".equals(getType(elements, element))) {
      return "serial";
    } else {
      return "bigserial";
    }
  }
  private String getType(Elements elements, Element element) {
    return elements.getTypeElement(element.asType().toString()).getSimpleName().toString();
  }

  private String appendType(Elements elements, Element element) {
    if ("String".equals(getType(elements, element))) {
      return sqlTypes.get(getType(elements, element)) + "(" + element.getAnnotation(OrmColumn.class)
          .length() + ")";
    } else {
      return sqlTypes.get(getType(elements, element));
    }
  }
}
