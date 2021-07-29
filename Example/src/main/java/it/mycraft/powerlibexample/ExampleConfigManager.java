package it.mycraft.powerlibexample;

import it.mycraft.powerlib.bukkit.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class ExampleConfigManager {

    private ConfigManager cm;

    /**
     * It will use the /plugins/MyPlugin/ folder to create, save and load files
     */
    public void init() {
        cm = new ConfigManager(Bukkit.getPluginManager().getPlugin("MyPlugin"));
    }

    public FileConfiguration createConfig() {
        FileConfiguration config = cm.create("config.yml");
        return config;
    }

    public FileConfiguration getConfig() {
        return cm.get("config.yml");
    }

    public void saveConfig(String config) {
        cm.save(config);
    }

    public void reloadConfig() {
        cm.reload("config.yml");
    }

    public void reloadFiles() {
        cm.reloadAll();
    }
}
