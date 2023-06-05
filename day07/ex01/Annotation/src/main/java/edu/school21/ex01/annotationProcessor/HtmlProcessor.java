package edu.school21.ex01.annotationProcessor;

import com.google.auto.service.AutoService;
import edu.school21.ex01.annotations.HtmlForm;
import edu.school21.ex01.annotations.HtmlInput;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes(value = {"edu.school21.ex01.annotations.HtmlForm"
    , "edu.school21.ex01.annotations.HtmlInput"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {

      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        HtmlForm htmlForm = typeElement.getAnnotation(HtmlForm.class);
        String fileName = htmlForm.fileName();
        StringBuilder formCode = new StringBuilder();
        formCode
            .append("<form action = \"")
            .append(htmlForm.action())
            .append("\" method = \"")
            .append(htmlForm.method())
            .append("\">\n");

        for (Element fieldElement : typeElement.getEnclosedElements()) {

          if (fieldElement.getKind().isField() &&
              fieldElement.getAnnotation(HtmlInput.class) != null) {
            HtmlInput htmlInput = fieldElement.getAnnotation(HtmlInput.class);

            formCode
                .append("\t<input type = \"")
                .append(htmlInput.type())
                .append("\" name = \"")
                .append(htmlInput.name())
                .append("\" placeholder = \"")
                .append(htmlInput.placeholder())
                .append("\">\n");
          }
        }

        formCode.append("\t<input type=\"submit\" value=\"Send\">\n");
        formCode.append("</form>");

//        System.out.println(formCode);

        try {
          Path outputPath = Paths.get("target/classes", fileName);
          BufferedWriter writer = Files.newBufferedWriter(outputPath);
          writer.write(formCode.toString());
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }
    return true;
  }
}
