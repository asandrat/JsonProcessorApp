package org.jsonprocessor.processor.writer;

import org.jsonprocessor.processor.FilteredBy;
import org.jsonprocessor.processor.FilteredValue;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Map;

public class ParentFilterWriter extends AbstractFilterWriter {

    @Override
    protected String getSpecificFilterName() {
        return PARENT_CLASS_NAME;
    }

    @Override
    protected void writeImportStatements(PrintWriter out) {
        out.println("import java.util.function.Predicate;");
    }

    @Override
    protected void writeClassName(String builderSimpleClassName, PrintWriter out) {
        out.print("public abstract class ");
        out.print(builderSimpleClassName);
        out.println(" {");
        out.println();
    }

    @Override
    protected void writeClassBody(String className, Map<Class<? extends Annotation>, String> annotationToMethodParamsMap, PrintWriter out) {
        out.print("    private final ");
        out.print(className);
        out.println(" nextProcessor;");
        out.println();

        out.print("    public " + className);
        out.print("(");
        out.print(className);
        out.println(" nextProcessor) {");

        out.println("        this.nextProcessor = nextProcessor;");
        out.println("    }");

        String filters = annotationToMethodParamsMap.getOrDefault(FilteredBy.class, "Object");
        String value = annotationToMethodParamsMap.getOrDefault(FilteredValue.class, "Object");

        out.println("    public final String process(");
        out.print("            " + filters);
        out.println(" filters, ");
        out.print("            " + value);
        out.println(" value) {");
        out.println("        String result = value;");
        out.println("        if (shouldProcess(filters)) {");
        out.println("            result = doProcess(filters, value);");
        out.println("        }");
        out.println("");
        out.println("        if (nextProcessor != null) {");
        out.println("            return nextProcessor.process(filters, result);");
        out.println("        }");
        out.println("        return result;");
        out.println("}");
        out.println("");
        out.println("    protected abstract Predicate<Filter> getPredicate();");

        out.print("    private boolean shouldProcess(");
        out.print(filters);
        out.println(" filters) {");
        out.println("        return filters.stream().anyMatch(getPredicate());");
        out.println("    }");
        out.println("");

        out.println("    protected abstract String doProcess(");
        out.println("            " + filters + " filters, ");
        out.println("            " + value + " value);");
        out.println("");
        out.println("}");
    }
}
