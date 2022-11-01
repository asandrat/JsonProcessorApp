package org.json.processor.writer;


import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Map;

public abstract class AbstractFilterWriter {

    protected static final String PARENT_CLASS_NAME = "FilterProcessor";

    public final void writeFile(
            String className,
            Map<Class<? extends Annotation>, String> annotationToMethodParamsMap,
            ProcessingEnvironment processingEnv) throws IOException {

        String packageName = getPackageName(className);
        String generatedClassName = className + getSpecificFilterName();
        String generatedSimpleClassName = generatedClassName.substring(className.lastIndexOf('.') + 1);

        JavaFileObject file = processingEnv.getFiler().createSourceFile(generatedClassName);
        try (PrintWriter out = new PrintWriter(file.openWriter())) {
            writePackageName(packageName, out);
            writeImportStatements(out);
            writeClassName(generatedSimpleClassName, out);
            writeClassBody(generatedSimpleClassName, annotationToMethodParamsMap, out);
        }
    }

    protected abstract String getSpecificFilterName();

    protected abstract void writeImportStatements(PrintWriter out);

    protected abstract void writeClassBody(
            String className,
            Map<Class<? extends Annotation>, String> annotationToMethodParamsMap,
            PrintWriter out);

    protected void writeClassName(String className, PrintWriter out) {
        out.print("public class ");
        out.print(className);
        out.print(" extends ");
        out.print(className.replaceAll(getSpecificFilterName(), "") + PARENT_CLASS_NAME);
        out.println(" {");
        out.println();
    }

    protected void addConstructorForDerivedClass(String className, PrintWriter out) {
        out.print("    public " + className);
        out.print("(");
        out.print(className.replaceAll(getSpecificFilterName(), "") + PARENT_CLASS_NAME);
        out.println(" nextProcessor) {");
        out.println("        super(nextProcessor);");
        out.println("}");
    }

    private void writePackageName(String packageName, PrintWriter out) {
        if (packageName != null) {
            out.print("package ");
            out.print(packageName);
            out.println(";");
            out.println();
        }
    }

    private String getPackageName(String className) {
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            return className.substring(0, lastDot);
        }

        return null;
    }
}
