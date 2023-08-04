package me.dimitri.fileupload.utils;

import java.io.*;

public class ConfigStartup {

    public boolean createConfig() {
        try {
            File config = new File("config.yml");
            if (config.createNewFile()) {
                OutputStream outputStream = new FileOutputStream(config);
                outputStream.write(getResource("config.yml").readAllBytes());
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private InputStream getResource(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}
