package org.json.filter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ElementReplaceRegexFilterTest extends AbstractFilterTest {

    @BeforeEach
    void setUp() {
        filterProcessor = new ElementReplaceRegexFilter(null);
    }

    @Test
    void testDoNotProcessDifferentFilter() {
        Filter filter = createFilter("SomeRandomFilter", "Group1", Arrays.asList("ay", "ey"));
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testDoNotProcessCorrectFilterWrongParamsSize() {
        Filter filter = createFilter("ReplaceRegexValueFilter", "Group1", null);
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testDoNotProcessCorrectFilterAndParamsSizeWrongGroup() {
        Filter filter = createFilter("ReplaceRegexValueFilter", "Grou17", Arrays.asList("ay", "ey"));
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testProcessCorrectFilter() {
        Filter filter = createFilter("ReplaceRegexValueFilter", "Group1", Arrays.asList("ay", "ey"));
        String originalValue = "Mayday";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, "Meydey");
    }
}