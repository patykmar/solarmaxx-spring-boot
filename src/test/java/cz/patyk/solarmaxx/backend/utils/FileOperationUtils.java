package cz.patyk.solarmaxx.backend.utils;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperationUtils {
    private final String filePrefix;

    public FileOperationUtils(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    @SneakyThrows
    public String loadFileContextAsString(String fileName, PrefixPath prefix) {
        return switch (prefix) {
            case HTML -> Files.readString(Paths.get(String.format(filePrefix, PrefixPath.HTML.getPrefix()) + fileName), StandardCharsets.UTF_8);
            case PEM -> Files.readString(Paths.get(String.format(filePrefix, PrefixPath.PEM.getPrefix()) + fileName), StandardCharsets.UTF_8);
            case JSON -> Files.readString(Paths.get(String.format(filePrefix, PrefixPath.JSON.getPrefix()) + fileName), StandardCharsets.UTF_8);
        };
    }

}
