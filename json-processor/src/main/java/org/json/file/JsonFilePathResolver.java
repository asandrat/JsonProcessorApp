package org.json.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JsonFilePathResolver {

    private static JsonFilePathResolver INSTANCE;
    private String filePath;

    private JsonFilePathResolver() {
        initializeFilePath();
    }

    private void initializeFilePath() {
        Properties properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/application.properties")) {
            properties.load(is);
            filePath = properties.getProperty("json.file.path");
        } catch (IOException e) {
            String defaultFilePath = "src/main/resources/elements.json";
            System.out.println("Unable to find file path, Defaulting file path to " + defaultFilePath);
            filePath = defaultFilePath;
        }
    }

    public static JsonFilePathResolver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JsonFilePathResolver();
        }

        return INSTANCE;
    }

    public String getFilePath() {
        return filePath;
    }
}
