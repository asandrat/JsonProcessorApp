package org.json.filter;

import org.json.filter.model.Element;
import org.json.filter.model.ElementsHolder;
import org.json.filter.model.Filter;

import java.util.Arrays;
import java.util.Collections;

public class ElementsHolderFactory {

    public ElementsHolder createElementsHolder(String value) {
        Filter filter1 = new Filter();
        filter1.setFilterGroupId("Group1");
        filter1.setFilterId("NonLatinFilter");

        Filter filter2 = new Filter();
        filter2.setFilterGroupId("Group1");
        filter2.setFilterId("RemoveRegexValueFilter");
        filter2.setParameters(Collections.singletonList("om"));

        Filter filter3 = new Filter();
        filter3.setFilterGroupId("Group1");
        filter3.setFilterId("ReplaceRegexValueFilter");
        filter3.setParameters(Arrays.asList("yes", "no"));

        Element element = new Element(value, Arrays.asList(filter1, filter2, filter3));

        return new ElementsHolder(Collections.singletonList(element));
    }
}
