package org.notdev.config;

import net.fabricmc.api.ModInitializer;

import java.io.File;
import java.nio.file.Files;

public class Config {
    public static String FILE_NAME = "CONFIG";
    public static String path = "config/";

    public static ConfigBuilder load() {
        return ConfigBuilder.loadConfig(getFile());
    }

    public static void save(ConfigBuilder builder) {
        builder.saveConfig(getFile());
    }
    public static File getFile() {
        return  new File(path + FILE_NAME);
    }

    public static configExists() {
        return Files.exists(getFile());
    }
}
