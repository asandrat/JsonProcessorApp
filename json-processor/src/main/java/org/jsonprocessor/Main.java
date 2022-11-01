package org.jsonprocessor;

import com.google.gson.Gson;
import org.jsonprocessor.filter.ElementsHolderFactory;
import org.jsonprocessor.filter.FilterProcessorFactory;
import org.jsonprocessor.filter.model.*;
import org.jsonprocessor.json.JsonGenerator;
import org.jsonprocessor.json.JsonLoader;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Initialize filter processors
        ElementFilterProcessor filterProcessor = new FilterProcessorFactory().createElementFilterProcessor();

        //Generate json
        String value = String.join(" ", args);
        ElementsHolder elements = new ElementsHolderFactory().createElementsHolder(value);
        JsonGenerator jsonGenerator = new JsonGenerator();
        jsonGenerator.generateJson(elements);

        //process filters
        ElementsHolder originalElementsHolder = new JsonLoader().loadJson();
        List<Element> filteredElements = new ArrayList<>();
        originalElementsHolder.getElements().forEach(
                element -> {
                    String filteredValue = filterProcessor.process(element.getFilters(), element.getValue());
                    Element filteredElement =new Element();
                    filteredElement.setValue(filteredValue);
                    filteredElement.setFilters(element.getFilters());
                    filteredElements.add(filteredElement);
                }
        );
        ElementsHolder filteredElementHolder = new ElementsHolder();
        filteredElementHolder.setElements(filteredElements);

        //Write the original and filtered
        System.out.println(new Gson().toJson(originalElementsHolder));
        System.out.println(new Gson().toJson(filteredElementHolder));
    }
}