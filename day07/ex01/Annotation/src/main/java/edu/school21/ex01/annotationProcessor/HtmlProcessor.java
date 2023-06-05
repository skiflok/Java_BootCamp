package edu.school21.ex01.annotationProcessor;

import edu.school21.ex01.annotations.HtmlForm;
import edu.school21.ex01.annotations.HtmlInput;
import java.io.FilterOutputStream;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
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
//@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {

      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        HtmlForm htmlForm = typeElement.getAnnotation(HtmlForm.class);
        String filName = htmlForm.fileName();
        String action = htmlForm.action();
        String method = htmlForm.method();
        String formCode = String.format("<form action = \"%s\" method = \"%s\">",
                              action,
                              method);
        System.out.println(formCode);

      }
    }
    return true;
  }
}
//        System.out.println("annotations = " + annotations.size());

//        annotations.forEach(System.out::println);

//          roundEnv.getElementsAnnotatedWith(HtmlForm.class).stream()
//                  .map(element -> {
//                      HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
//                      String filName = htmlForm.fileName();
//                      String action = htmlForm.action();
//                      String method = htmlForm.method();
//
//
//                      return String.format("<form action = \"%s\" method = \"%s\">",
//                              action,
//                              method);
//                  })
//                  .forEach(System.out::println);