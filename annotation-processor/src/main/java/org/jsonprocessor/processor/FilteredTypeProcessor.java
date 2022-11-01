package org.jsonprocessor.processor;

import com.google.auto.service.AutoService;
import org.jsonprocessor.processor.writer.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        "org.jsonprocessor.processor.FilteredBy",
        "org.jsonprocessor.processor.FilteredValue"})
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

        Map<Class<? extends Annotation>, String> setterMap = setters.stream()
                .collect(Collectors.toMap(
                        this::getAnnotationFromSetter,
                        setter -> ((ExecutableType) setter.asType()).getParameterTypes().get(0).toString()));

        filterWriters.forEach(writer -> {
            try {
                writer.writeBuilderFile(className, setterMap, processingEnv);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR, "Exception happened while writing to file");
                e.printStackTrace();
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

    private void writeBuilderFile(
            String className, Map<Class<? extends Annotation>, String> setterMap) throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String builderClassName = className + "NonLatinValueFilter";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.println("import java.util.function.Predicate;");
            out.println("import java.util.stream.Collectors;");
            out.println("import org.jsonprocessor.converter.CyrillicToLatinConverter;");
            out.println("import org.jsonprocessor.converter.GreekToLatinConverter;");
            out.println();
            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private static Predicate<Filter> SHOULD_PROCESS = filter -> ");
            out.println("\"NonLatinFilter\".equals(filter.getFilterId())");
            out.println("        && \"Group1\".equals(filter.getFilterGroupId())");
            out.println("        && filter.getParameters() == null;");
            out.println();

            String methodName = "filter";
            String filters = setterMap.getOrDefault(FilteredBy.class, "Object");
            String value = setterMap.getOrDefault(FilteredValue.class, "Object");

            out.print("    public String ");
            out.print(methodName);

            out.print("(");

            out.println("    " + filters);
            out.println(" filters, ");
            out.println("    " + value);
            out.print(" value) {");
            out.println("        if (filters.stream().noneMatch(SHOULD_PROCESS)) {");
            out.println("            return value;");
            out.println("        }");
            out.println("        return value.chars()");
            out.println("              .mapToObj(c -> (char) c)");
            out.println("              .map(CyrillicToLatinConverter::convertCharToLatin)");
            out.println("              .map(GreekToLatinConverter::convertStringToLatin)");
            out.println("              .collect(Collectors.joining());");
            out.println("        }");
            out.println();

            out.println("}");
        }
    }

}
