package ru.otus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public final class FileSystemHelper {

    private static final Logger log = LoggerFactory.getLogger(FileSystemHelper.class);

    private FileSystemHelper() {
    }

    public static String localFileNameOrResourceNameToFullPath(String fileOrResourceName) {
        String path = null;
        File file = new File(String.format("./%s", fileOrResourceName));
        if (file.exists()) {
            path = URLDecoder.decode(file.toURI().getPath(), StandardCharsets.UTF_8);
        }

        if (path == null) {
            log.info("Local file not found, looking into resources");
            path = Optional.ofNullable(FileSystemHelper.class.getClassLoader().getResource(fileOrResourceName))
                           .orElseThrow(() -> new RuntimeException(String.format("File \"%s\" not found", fileOrResourceName))).toExternalForm();

        }
        return path;
    }
}
