package it.mycraft.powerlib.config;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;

public class ConfigManager {

    private HashMap<String, FileConfiguration> configs;
    private Plugin plugin;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
    }

    /**
     * Gets a config file from the local Map
     * and creates it if it doesn't exist
     * @param file The config file name
     * @return The related FileConfiguration
     */
    public FileConfiguration get(String file) {
        if(this.configs.containsKey(file)) {
            return this.configs.get(file);
        }
        else {
            return this.create(file);
        }
    }

    /**
     * Creates a file if it doesn't exist and then
     * puts it into the local Map
     * @param file The config file name
     * @return The new file
     */
    public FileConfiguration create(String file) {
        File resourcePath = new File(this.plugin.getDataFolder() + file);
        if (!resourcePath.exists()) {
            createYAML(resourcePath.getName(), false);
        }
        return this.get(file);
    }

    /**
     * Saves the config file changes and
     * updates it in the local Map
     * @param config The saving configuration
     */
    public void save(FileConfiguration config) {
        try {
            config.save(config.getName());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.put(config);
    }

    /**
     * Reloads a config file
     * @param file The config file name
     */
    public void reload(String file) {
        FileConfiguration conf = this.load(file);
        InputStream stream = this.plugin.getResource(file);
        if (stream != null) {
            this.setDefaults(file, stream);
        }
        this.put(conf);
    }

    /**
     * Reloads all config files
     */
    public void reloadAll() {
        this.configs.keySet().forEach(this::reload);

    }

    /**
     * Loads a config file
     * @param file The config file name
     * @return the loaded file
     */
    private FileConfiguration load(String file) {
        return YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder() + file));
    }

    /**
     * Puts a config in the local Map
     * @param config The config file
     */
    private void put(FileConfiguration config) {
        this.configs.put(config.getName(), config);
    }

    /**
     * Set a config's defaults
     * @param file The config file name
     * @param stream The input stream for the file
     */
    private void setDefaults(String file, InputStream stream) {
        this.get(file).setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8)));
    }

    /**
     * Creates the file by looking for it inside the jar's resources,
     * then loads it and puts it in the local Map
     * @param resourcePath The file name
     * @param replace Whether the file has to be replaced by the default one although it already exists
     */
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
            FileConfiguration conf = this.load(resourcePath + ".yml");
            this.put(conf);
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

}
