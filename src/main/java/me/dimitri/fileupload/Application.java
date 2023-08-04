package me.dimitri.fileupload;

import io.micronaut.runtime.Micronaut;
import me.dimitri.fileupload.utils.ConfigStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ConfigStartup configStartup = new ConfigStartup();
        if (configStartup.createConfig()) {
            log.info("Created config.yml");
        }

        System.setProperty("micronaut.config.files", "config.yml");
        Micronaut.run(Application.class, args);
    }
}