package org.json.processor.writer;

import org.json.processor.FilteredBy;
import org.json.processor.FilteredValue;

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
        out.println("import org.json.converter.CyrillicToLatinConverter;");
        out.println("import org.json.converter.GreekToLatinConverter;");
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

        out.println("    protected Predicate<Filter> getPredicate(){");
        out.println("        return SHOULD_PROCESS;");
        out.println("  }");

        String filters = annotationToMethodParamsMap.getOrDefault(FilteredBy.class, "Object");
        String value = annotationToMethodParamsMap.getOrDefault(FilteredValue.class, "Object");
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

}
