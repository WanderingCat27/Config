package org.notdev.config;

import net.fabricmc.api.ModInitializer;

import java.io.File;

public class Config {
    public static String FILE_NAME = "CONFIG";

    public static ConfigBuilder load() {
        return ConfigBuilder.loadConfig(getFile());
    }

    public static void save(ConfigBuilder builder) {
        builder.saveConfig(getFile());
    }
    public static File getFile() {
        return  new File("config/" + FILE_NAME);
    }
}
