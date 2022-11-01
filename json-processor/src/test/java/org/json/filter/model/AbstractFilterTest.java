package org.json.filter.model;

import java.util.List;

public class AbstractFilterTest {

    protected ElementFilterProcessor filterProcessor;

    protected static Filter createFilter(String filterId, String filterGroup, List<String> params) {
        Filter filter = new Filter();
        filter.setFilterId(filterId);
        filter.setFilterGroupId(filterGroup);
        filter.setParameters(params);
        return filter;
    }
}
