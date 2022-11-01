package org.json.processor;

import com.google.auto.service.AutoService;
import org.json.processor.writer.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        "org.json.processor.FilteredBy",
        "org.json.processor.FilteredValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class FilteredTypeProcessor extends AbstractProcessor {

    private final Collection<AbstractFilterWriter> filterWriters;

    public FilteredTypeProcessor() {
        filterWriters = Collections.unmodifiableList(Arrays.asList(
                new ParentFilterWriter(),
                new NonLatinValueFilterWriter(),
                new RemoveRegexFilterWriter(),
                new ReplaceRegexFilterWriter()));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream()
                .collect(Collectors.partitioningBy(element -> ((ExecutableType) element.asType())
                        .getParameterTypes().size() == 1 && element.getSimpleName().toString().startsWith("set")));

        List<Element> setters = annotatedMethods.get(true);
        List<Element> otherMethods = annotatedMethods.get(false);

        otherMethods.forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                "@FilteredBy and @FilteredValue must be applied to a setter method", element));

        if (setters.isEmpty()) {
            return true;
        }

        String className = ((TypeElement) setters.get(0).getEnclosingElement()).getQualifiedName().toString();

        Map<Class<? extends Annotation>, String> annotationToMethodParamMap = setters.stream()
                .collect(Collectors.toMap(
                        this::getAnnotationFromSetter,
                        setter -> ((ExecutableType) setter.asType()).getParameterTypes().get(0).toString()));

        filterWriters.forEach(writer -> {
            try {
                writer.writeFile(className, annotationToMethodParamMap, processingEnv);
            } catch (IOException e) {
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Exception happened while writing to file");
            }
        });

        return true;
    }

    private Class<? extends Annotation> getAnnotationFromSetter(Element element) {
        Annotation filteredValue = element.getAnnotation(FilteredValue.class);
        return filteredValue == null
                ? element.getAnnotation(FilteredBy.class).annotationType()
                : filteredValue.annotationType();
    }

}
