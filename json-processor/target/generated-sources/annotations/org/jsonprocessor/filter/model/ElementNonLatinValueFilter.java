package org.jsonprocessor.filter.model;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.jsonprocessor.converter.CyrillicToLatinConverter;
import org.jsonprocessor.converter.GreekToLatinConverter;

public class ElementNonLatinValueFilter extends ElementFilterProcessor {

    private static Predicate<Filter> SHOULD_PROCESS = filter -> "NonLatinFilter".equals(filter.getFilterId()) 
        && "Group1".equals(filter.getFilterGroupId())
        && CollectionUtils.isEmpty(filter.getParameters());

    public ElementNonLatinValueFilter(ElementFilterProcessor nextProcessor) {
        super(nextProcessor);
}
    protected Predicate<Filter> getPredicate(){
        return SHOULD_PROCESS;
  }
    public String doProcess(
            java.util.Collection<org.jsonprocessor.filter.model.Filter> filters, 
            java.lang.String value) {
        return value.chars()
              .mapToObj(c -> (char) c)
              .map(CyrillicToLatinConverter::convertCharToLatin)
              .map(GreekToLatinConverter::convertStringToLatin)
              .collect(Collectors.joining());
    }

}
