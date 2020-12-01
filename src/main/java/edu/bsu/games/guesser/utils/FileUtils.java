package edu.bsu.games.guesser.utils;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import lombok.SneakyThrows;

/**
 * created by @Kabaye
 * date 29.11.2020
 */

public class FileUtils {

    public static final Gson GSON = new Gson();

    public static <T> T parseJson(FileReader jsonFile, Class<T> clazz) {
        return GSON.fromJson(jsonFile, clazz);
    }

    @SneakyThrows
    @SuppressWarnings("all")
    public static FileReader getFileContent(String path) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("data.json");
        return new FileReader(new File(resource.toURI()));
    }
}
