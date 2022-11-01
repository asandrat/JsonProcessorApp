package org.json.filter;

import org.json.filter.model.ElementFilterProcessor;
import org.json.filter.model.ElementNonLatinValueFilter;
import org.json.filter.model.ElementRemoveRegexFilter;
import org.json.filter.model.ElementReplaceRegexFilter;

public class FilterProcessorFactory {

    public ElementFilterProcessor createElementFilterProcessor() {
        ElementReplaceRegexFilter replaceRegexFilter = new ElementReplaceRegexFilter(null);
        ElementRemoveRegexFilter removeRegexFilter = new ElementRemoveRegexFilter(replaceRegexFilter);
        return new ElementNonLatinValueFilter(removeRegexFilter);
    }
}
