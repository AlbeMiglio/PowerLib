package it.mycraft.powerlib.config;

import com.google.common.base.Charsets;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class ConfigManager {

    private HashMap<String, FileConfiguration> configs;
    private Plugin plugin;


    //TODO: Complete the class
    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
        //this.getYAML(new File(this.plugin.getDataFolder(), file));
    }

    public void create(String path) {

    }

    private void getYAML(File resourcePath) {
        if (!resourcePath.exists()) {
            createYAML(resourcePath.getName(), false);
        }
    }

    public void reloadConfiguration() {
        this.configs.keySet().forEach((file) -> {
            this.loadFile(file);
            InputStream stream = this.plugin.getResource(file);
            if (stream != null) {
                this.setDefaults(file, stream);
            }
        });

    }

    public void loadFile(String config) {
        this.configs.put(config, YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder() + config)));
    }

    public void setDefaults(String s, InputStream stream) {
        FileConfiguration conf = this.configs.get(s);
        conf.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8)));
    }

    private void createYAML(String resourcePath, boolean replace) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.plugin.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " +
                        (new File(this.plugin.getDataFolder(), resourcePath + ".yml")));
            } else {
                File outFile = new File(this.plugin.getDataFolder(), resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(this.plugin.getDataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                        Bukkit.getLogger().log(Level.WARNING, outFile.getName() + "loaded with success!");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    Bukkit.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

}
