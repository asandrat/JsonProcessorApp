package org.jsonprocessor.processor.writer;

import org.jsonprocessor.processor.FilteredBy;
import org.jsonprocessor.processor.FilteredValue;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Map;

public class NonLatinValueFilterWriter extends AbstractFilterWriter {

    private final static String FILTER_NAME = "NonLatinValueFilter";
    private final static String FILTER_ID = "NonLatinFilter";
    private final static String FILTER_GROUP_ID = "Group1";

    @Override
    protected String getSpecificFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected void writeImportStatements(PrintWriter out) {
        out.println("import java.util.function.Predicate;");
        out.println("import java.util.stream.Collectors;");
        out.println("import org.apache.commons.collections4.CollectionUtils;");
        out.println("import org.jsonprocessor.converter.CyrillicToLatinConverter;");
        out.println("import org.jsonprocessor.converter.GreekToLatinConverter;");
        out.println();
    }

    protected void writeClassBody(
            String className,
            Map<Class<? extends Annotation>, String> annotationToMethodParamsMap,
            PrintWriter out) {
        out.print("    private static Predicate<Filter> SHOULD_PROCESS = filter -> ");
        out.println("\"" + FILTER_ID + "\".equals(filter.getFilterId()) ");
        out.println("        && \"" + FILTER_GROUP_ID + "\".equals(filter.getFilterGroupId())");
        out.println("        && CollectionUtils.isEmpty(filter.getParameters());");
        out.println();

        addConstructorForDerivedClass(className, out);

        String filters = annotationToMethodParamsMap.getOrDefault(FilteredBy.class, "Object");
        String value = annotationToMethodParamsMap.getOrDefault(FilteredValue.class, "Object");

        out.println("    protected Predicate<Filter> getPredicate(){");
        out.println("        return SHOULD_PROCESS;");
        out.println("  }");

        out.println("    public String doProcess(");
        out.print("            " + filters);
        out.println(" filters, ");
        out.print("            " + value);
        out.println(" value) {");
        out.println("        return value.chars()");
        out.println("              .mapToObj(c -> (char) c)");
        out.println("              .map(CyrillicToLatinConverter::convertCharToLatin)");
        out.println("              .map(GreekToLatinConverter::convertStringToLatin)");
        out.println("              .collect(Collectors.joining());");
        out.println("    }");
        out.println();
        out.println("}");
    }

    @Override
    protected void writeClassName(String className, PrintWriter out) {
        out.print("public class ");
        out.print(className);
        out.print(" extends ");
        out.print(className.replaceAll(FILTER_NAME, "") + PARENT_CLASS_NAME);
        out.println(" {");
        out.println();
    }

}
