package org.jsonprocessor.filter.model;

import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

public class ElementRemoveRegexFilter extends ElementFilterProcessor {

    private static Predicate<Filter> SHOULD_PROCESS = filter -> "RemoveRegexValueFilter".equals(filter.getFilterId()) 
        && "Group1".equals(filter.getFilterGroupId())
        && !CollectionUtils.isEmpty(filter.getParameters()) 
        && filter.getParameters().size() == 1;

    public ElementRemoveRegexFilter(ElementFilterProcessor nextProcessor) {
        super(nextProcessor);
}
    protected Predicate<Filter> getPredicate(){
        return SHOULD_PROCESS;
  }
    public String doProcess(
            java.util.Collection<org.jsonprocessor.filter.model.Filter> filters, 
            java.lang.String value) {
        String result = value;
        List<Filter> applicableFilters = filters.stream()
              .filter(SHOULD_PROCESS)
              .collect(Collectors.toList());
        for (Filter filter : applicableFilters) {
              String regex = filter.getParameters().get(0);
              result =  result.replaceAll(regex, "");
         }
         return result;
    }

}
