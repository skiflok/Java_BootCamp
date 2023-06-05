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
        StringBuilder formCode = new StringBuilder();
        formCode
            .append("<form action = \"")
            .append(action)
            .append("\" method = \"")
            .append(method)
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
        System.out.println(formCode);
      }
    }
    return true;
  }
}


/*
*
  <form action = "/users" method = "post">
	<input type = "text" name = "first_name" placeholder = "Enter First Name">
	<input type = "text" name = "last_name" placeholder = "Enter Last Name">
	<input type = "password" name = "password" placeholder = "Enter Password">
	<input type = "submit" value = "Send">
</form>
* */
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