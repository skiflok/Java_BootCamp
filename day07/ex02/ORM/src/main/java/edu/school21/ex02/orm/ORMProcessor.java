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
import java.util.Set;
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

  private final Map<String, String> sqlTypes = new HashMap<>();
  private final String schemaName;

  String fileName;

  {
    sqlTypes.put("String", "varchar");
    sqlTypes.put("Integer", "int");
    sqlTypes.put("Double", "numeric");
    sqlTypes.put("Boolean", "bool");
    sqlTypes.put("Long", "bigint");
    schemaName = "orm";
    fileName = "schema.sql";
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
        int count = 0;
        for (Element fieldElement : fieldElements) {
          count++;
          if (fieldElement.getKind().isField()) {
            if (fieldElement.getAnnotation(OrmColumnId.class) != null) {
              schema.append(fieldElement)
                  .append(" ")
                  .append(getAutoIncrementType(elements, fieldElement));
            }
            if (fieldElement.getAnnotation(OrmColumn.class) != null) {
              schema.append(fieldElement)
                  .append(" ")
                  .append(appendType(elements, fieldElement));
            }
            if (count == fieldElements.size()) {
              schema.append(");\n");
            } else {
              schema.append(",\n");
            }
          }
        }
      }
    }

    try {
      System.out.println("\n" + schema);
      Path outputPath = Paths.get("target/classes", fileName);
      BufferedWriter writer = Files.newBufferedWriter(outputPath);
      writer.write(schema.toString());
      writer.close();
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
      return sqlTypes.get(getType(elements, element)) + "(" + element.getAnnotation(OrmColumn.class).length() + ")";
    } else {
      return sqlTypes.get(getType(elements, element));
    }
  }


}
