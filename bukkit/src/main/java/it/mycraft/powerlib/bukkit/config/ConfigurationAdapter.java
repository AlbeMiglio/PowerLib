package it.mycraft.powerlib.bukkit.config;

import it.mycraft.powerlib.common.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationAdapter {

    public static Configuration adapt(FileConfiguration fileConfiguration) {
        Configuration config = new Configuration();
        fileConfiguration.getKeys(true).forEach((k) -> {
            config.set(k, fileConfiguration.get(k));
        });
        return config;
    }
}
