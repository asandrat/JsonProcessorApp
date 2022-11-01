package org.json.filter.model;

import org.json.processor.FilteredBy;
import org.json.processor.FilteredValue;

import java.util.Collection;

public class Element {

    private String value;
    private Collection<Filter> filters;

    public Element(String value, Collection<Filter> filters) {
        this.value = value;
        this.filters = filters;
    }

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
