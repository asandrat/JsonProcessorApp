package org.jsonprocessor.json;

import com.google.gson.Gson;
import org.jsonprocessor.filter.model.ElementsHolder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {

    public ElementsHolder loadJson() {
        try (Reader reader = Files.newBufferedReader(
                Paths.get(JsonFilePathResolver.getInstance().getFilePath()))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ElementsHolder.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
