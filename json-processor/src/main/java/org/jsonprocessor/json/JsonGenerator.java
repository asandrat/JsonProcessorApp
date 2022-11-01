package org.jsonprocessor.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsonprocessor.filter.model.ElementsHolder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JsonGenerator {

    public void generateJson(ElementsHolder elements) {
        try (Writer writer = new FileWriter(JsonFilePathResolver.getInstance().getFilePath())) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(elements, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
