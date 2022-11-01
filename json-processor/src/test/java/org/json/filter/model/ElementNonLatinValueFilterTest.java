package org.json.filter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ElementNonLatinValueFilterTest extends AbstractFilterTest {

    @BeforeEach
    void setUp() {
        filterProcessor = new ElementNonLatinValueFilter(null);
    }

    @Test
    void testDoNotProcessDifferentFilter() {
        Filter filter = createFilter("someFilter", "Group1", null);
        String originalValue = "TeΨ Ώ";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testDoNotProcessCorrectFilterButWrongNoOfParams() {
        Filter filter = createFilter("NonLatinFilter", "Group1", Collections.singletonList("param"));
        String originalValue = "TeΨ Ώ";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, originalValue);
    }

    @Test
    void testProcessCorrectFilterAndNoParams() {
        Filter filter = createFilter("NonLatinFilter", "Group1", null);
        String originalValue = "TeΨ Ώ";
        String result = filterProcessor.process(Collections.singletonList(filter), originalValue);
        assertEquals(result, "TePs O");
    }
}