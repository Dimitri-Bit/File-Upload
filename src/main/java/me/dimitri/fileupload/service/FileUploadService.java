package me.dimitri.fileupload.service;

import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Singleton
public class FileUploadService {

    private final String BASE_DIR = System.getProperty("user.dir");
    private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    public boolean saveFile(CompletedFileUpload file) {
        String fileName = UUID.randomUUID().toString() + fileExtension(file.getFilename());
        String filePath = "/files/" + fileName;

        File systemFile = new File(BASE_DIR + filePath);
        if (!systemFile.getParentFile().exists()) {
            systemFile.getParentFile().mkdirs();
        }

        if (systemFile.exists()) {
            String newName = renameFile(fileName);
            log.warn("A file with the same random UUID name already exists. What?!");
            systemFile = new File(BASE_DIR + "/files/" + newName);
        }

        return writeFile(systemFile, file);
    }

    private String fileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return "." + filename.substring(dotIndex + 1);
    }

    private String renameFile(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        String name = filename.substring(0, dotIndex);
        String extension = filename.substring(dotIndex + 1);
        int counter = 1;

        String newFilename = filename;
        while (new File(System.getProperty("user.dir") + "/files/" + newFilename).exists()) {
            newFilename = name + "_" + counter + "." + extension;
            counter++;
        }
        return newFilename;
    }

    private boolean writeFile(File systemFile, CompletedFileUpload file) {
        try {
            FileUtils.writeByteArrayToFile(systemFile, file.getBytes());
        } catch (IOException ignored) { return false; }
        return true;
    }
}
