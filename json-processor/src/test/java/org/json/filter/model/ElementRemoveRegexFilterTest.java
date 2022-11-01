package org.json.filter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ElementRemoveRegexFilterTest extends AbstractFilterTest {

    @BeforeEach
    void setUp() {
        filterProcessor = new ElementRemoveRegexFilter(null);
    }

    @Test
    void testDoNotProcessDifferentFilter() {
        Filter filter = createFilter("SomeRandomFilter", "Group1", Collections.singletonList("da"));
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testDoNotProcessCorrectFilterWrongParamsSize() {
        Filter filter = createFilter("RemoveRegexValueFilter", "Group1", null);
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testDoNotProcessCorrectFilterAndParamsSizeWrongGroup() {
        Filter filter = createFilter("RemoveRegexValueFilter", "Group9", Collections.singletonList("day"));
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testProcessCorrectFilter() {
        Filter filter = createFilter("RemoveRegexValueFilter", "Group1", Collections.singletonList("day"));
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, "May");
    }
}