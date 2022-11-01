package org.json.processor.writer;

import org.json.processor.FilteredBy;
import org.json.processor.FilteredValue;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Map;

public class ReplaceRegexFilterWriter extends AbstractFilterWriter {

    private static final String FILTER_NAME = "ReplaceRegexFilter";
    private static final String FILTER_ID = "ReplaceRegexValueFilter";
    private final static String FILTER_GROUP_ID = "Group1";

    @Override
    protected String getSpecificFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected void writeImportStatements(PrintWriter out) {
        out.println("import java.util.function.Predicate;");
        out.println("import java.util.List;");
        out.println("import java.util.stream.Collectors;");
        out.println("import org.apache.commons.collections4.CollectionUtils;");
        out.println();
    }

    @Override
    protected void writeClassBody(
            String className,
            Map<Class<? extends Annotation>, String> annotationToMethodParamsMap,
            PrintWriter out) {
        out.print("    private static Predicate<Filter> SHOULD_PROCESS = filter -> ");
        out.println("\"" + FILTER_ID + "\".equals(filter.getFilterId()) ");
        out.println("        && \"" + FILTER_GROUP_ID + "\".equals(filter.getFilterGroupId())");
        out.println("        && !CollectionUtils.isEmpty(filter.getParameters()) ");
        out.println("        && filter.getParameters().size() == 2;");
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
        out.println("        String result = value;");
        out.println("        List<Filter> applicableFilters = filters.stream()");
        out.println("              .filter(SHOULD_PROCESS)");
        out.println("              .collect(Collectors.toList());");
        out.println("        for (Filter filter : applicableFilters) {");
        out.println("              String regex = filter.getParameters().get(0);");
        out.println("              String replacement = filter.getParameters().get(1);");
        out.println("              result =  result.replaceAll(regex, replacement);");
        out.println("         }");
        out.println("         return result;");
        out.println("    }");
        out.println();
        out.println("}");
    }
}
