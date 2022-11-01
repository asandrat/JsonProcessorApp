package org.json;

import com.google.gson.Gson;
import org.json.filter.ElementsHolderFactory;
import org.json.filter.FilterProcessorFactory;
import org.json.filter.model.Element;
import org.json.filter.model.ElementsHolder;
import org.json.file.JsonGenerator;
import org.json.file.JsonLoader;
import org.json.filter.model.ElementFilterProcessor;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Initialize filter processors
        ElementFilterProcessor filterProcessor = new FilterProcessorFactory().createElementFilterProcessor();
        //Generate json
        generateJsonFile(args);
        //Load Json file
        ElementsHolder originalElementsHolder = new JsonLoader().loadJson();
        //Process filters
        ElementsHolder filteredElementHolder = getFilteredElementsHolder(filterProcessor, originalElementsHolder);
        //Write the original and filtered elements
        System.out.println(new Gson().toJson(originalElementsHolder));
        System.out.println(new Gson().toJson(filteredElementHolder));
    }

    private static void generateJsonFile(String[] args) {
        String value = String.join(" ", args);
        ElementsHolder elements = new ElementsHolderFactory().createElementsHolder(value);
        JsonGenerator jsonGenerator = new JsonGenerator();
        jsonGenerator.generateJson(elements);
    }

    private static ElementsHolder getFilteredElementsHolder(
            ElementFilterProcessor filterProcessor,
            ElementsHolder originalElementsHolder) {
        List<Element> filteredElements = new ArrayList<>();
        originalElementsHolder.getElements().forEach(
                element -> {
                    String filteredValue = filterProcessor.process(element.getFilters(), element.getValue());
                    Element filteredElement = new Element(filteredValue, element.getFilters());
                    filteredElements.add(filteredElement);
                }
        );

        ElementsHolder filteredElementHolder = new ElementsHolder(filteredElements);
        filteredElementHolder.setElements(filteredElements);
        return filteredElementHolder;
    }
}