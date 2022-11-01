package org.jsonprocessor.filter;

import org.jsonprocessor.filter.model.ElementFilterProcessor;
import org.jsonprocessor.filter.model.ElementNonLatinValueFilter;
import org.jsonprocessor.filter.model.ElementRemoveRegexFilter;
import org.jsonprocessor.filter.model.ElementReplaceRegexFilter;

public class FilterProcessorFactory {

    public ElementFilterProcessor createElementFilterProcessor() {
        ElementReplaceRegexFilter replaceRegexFilter = new ElementReplaceRegexFilter(null);
        ElementRemoveRegexFilter removeRegexFilter = new ElementRemoveRegexFilter(replaceRegexFilter);
        return new ElementNonLatinValueFilter(removeRegexFilter);
    }
}
