package edu.school21.ex01.annotationProcessor;

import edu.school21.ex01.annotations.HtmlForm;
import edu.school21.ex01.annotations.HtmlInput;
import java.util.Arrays;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;


@SupportedAnnotationTypes(value = {"edu.school21.ex01.annotations.HtmlForm",
    "edu.school21.ex01.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    System.out.println("annotations = " + annotations.size());
    System.out.println( roundEnv.getElementsAnnotatedWith(HtmlInput.class).size());
    System.out.println( roundEnv.getElementsAnnotatedWith(HtmlForm.class).size());
    annotations.forEach(annotation -> {
      Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
      elements.stream()
          .flatMap(element ->
              Arrays.stream(element.getAnnotationsByType(HtmlInput.class))
          )
          .forEach(ann -> {
            System.out.println(
                " аннотация = " + ann.type() + " " + ann.name() + " " + ann.placeholder());
          });
//          .filter(TypeElement.class::isInstance)
//          .map(TypeElement.class::cast)
//          .map(TypeElement::getQualifiedName)
//          .map(name -> "Class " + name + " is annotated with " + annotation.getQualifiedName())
//          .forEach(System.out::println);
    });
    return true;
  }
}
