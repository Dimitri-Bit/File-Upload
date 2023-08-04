package me.dimitri.fileupload.service;

import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Singleton
public class FileUploadService {

    private final String BASE_DIR = System.getProperty("user.dir");

    public boolean saveFile(CompletedFileUpload file) {
        String filePath = "/files/" + file.getFilename();
        File systemFile = new File(BASE_DIR + filePath);

        if (!systemFile.getParentFile().exists()) {
            systemFile.getParentFile().mkdirs();
        }

        if (systemFile.exists()) {
            String newName = renameFile(file.getFilename());
            systemFile = new File(BASE_DIR + "/files/" + newName);
        }

        return writeFile(systemFile, file);
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
