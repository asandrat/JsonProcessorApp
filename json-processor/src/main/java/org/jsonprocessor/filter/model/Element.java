package org.jsonprocessor.filter.model;

import org.jsonprocessor.processor.FilteredBy;
import org.jsonprocessor.processor.FilteredValue;

import java.util.Collection;

public class Element {

    private String value;
    private Collection<Filter> filters;

    public String getValue() {
        return value;
    }

    @FilteredValue
    public void setValue(String value) {
        this.value = value;
    }

    public Collection<Filter> getFilters() {
        return filters;
    }

    @FilteredBy
    public void setFilters(Collection<Filter> filters) {
        this.filters = filters;
    }
}
