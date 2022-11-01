package org.jsonprocessor.filter.model;

import java.util.function.Predicate;
public abstract class ElementFilterProcessor {

    private final ElementFilterProcessor nextProcessor;

    public ElementFilterProcessor(ElementFilterProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }
    public final String process(
            java.util.Collection<org.jsonprocessor.filter.model.Filter> filters, 
            java.lang.String value) {
        String result = value;
        if (shouldProcess(filters)) {
            result = doProcess(filters, value);
        }

        if (nextProcessor != null) {
            return nextProcessor.process(filters, result);
        }
        return result;
}

    protected abstract Predicate<Filter> getPredicate();
    private boolean shouldProcess(java.util.Collection<org.jsonprocessor.filter.model.Filter> filters) {
        return filters.stream().anyMatch(getPredicate());
    }

    protected abstract String doProcess(
            java.util.Collection<org.jsonprocessor.filter.model.Filter> filters, 
            java.lang.String value);

}
