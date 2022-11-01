package org.json.filter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class ElementsHolder {

    private Collection<Element> elements;
}
