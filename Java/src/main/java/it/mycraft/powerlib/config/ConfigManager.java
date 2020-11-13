package it.mycraft.powerlib.config;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginAwareness;

import java.io.*;
import java.nio.charset.Charset;
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
     * Gets a config file from the local Map and creates it if it doesn't exist
     *
     * @param file The config file name
     * @return The related FileConfiguration
     */
    public FileConfiguration get(String file) {
        if (this.configs.containsKey(file)) {
            return this.configs.get(file);
        } else {
            return this.create(file);
        }
    }

    /**
     * Creates a file if it doesn't exist and then puts it into the local Map
     *
     * @param file The config file name
     * @return The new file
     */
    public FileConfiguration create(String file, String source) {
        File resourcePath = new File(this.plugin.getDataFolder() + "/" + file);
        if (!resourcePath.exists()) {
            createYAML(resourcePath.getName(), source, false);
        }
        else this.reload(file);
        return this.configs.get(file);
    }

    /**
     * Same as #create(String,String) but source name equals to the new one
     *
     * @param file The config file name
     * @return The new file
     */
    public FileConfiguration create(String file) {
        return this.create(file, file);
    }

    /**
     * Saves the config file changes and updates it in the local Map
     *
     * @param file The config file name
     */
    public void save(String file) {
        FileConfiguration config = get(file);
        if (config == null) {
            throw new IllegalArgumentException("The specified configuration file doesn't exist!");
        }
        try {
            config.save(new File(this.plugin.getDataFolder(), file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.put(file, config);
    }

    /**
     * Reloads a config file
     *
     * @param file The config file name
     * @author Original code from JavaPlugin.class
     */
    public void reload(String file) {
        FileConfiguration conf = this.load(file);
        InputStream defStream = this.plugin.getResource(file);
        if (defStream != null) {
            YamlConfiguration yamlConfig;
            if (!this.isStrictlyUTF8()) {
                yamlConfig = new YamlConfiguration();
                byte[] contents;
                try {
                    contents = ByteStreams.toByteArray(defStream);
                } catch (IOException e) {
                    this.plugin.getLogger().log(Level.SEVERE, "Unexpected failure reading " + file, e);
                    return;
                }

                String text = new String(contents, Charset.defaultCharset());
                if (!text.equals(new String(contents, Charsets.UTF_8))) {
                    this.plugin.getLogger().warning("Default system encoding has misread " + file + " from plugin jar");
                }

                try {
                    yamlConfig.loadFromString(text);
                } catch (InvalidConfigurationException e) {
                    this.plugin.getLogger().log(Level.SEVERE, "Cannot load configuration from jar", e);
                }
            } else {
                yamlConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defStream, Charsets.UTF_8));
            }
            conf.setDefaults(yamlConfig);
            this.put(file, conf);
        }
    }

    /**
     * Reloads all config files
     */
    public void reloadAll() {
        this.configs.keySet().forEach(this::reload);
    }

    /**
     * Loads a config file
     *
     * @param file The config file name
     * @return the loaded file
     */
    private FileConfiguration load(String file) {
        return YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder() + "/" + file));
    }

    /**
     * Puts a config in the local Map
     *
     * @param config The config file
     */
    private void put(String file, FileConfiguration config) {
        this.configs.put(file, config);
    }

    /**
     * Creates the file by looking for it inside the jar's resources, then loads it and puts it in the local Map
     *
     * @param resourcePath The file name
     * @param source       The source file name
     * @param replace      Whether the file has to be replaced by the default one although it already exists
     * @author Original code from JavaPlugin.class
     */
    private void createYAML(String resourcePath, String source, boolean replace) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.plugin.getResource(source);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + source + "' cannot be found in " +
                        (new File(this.plugin.getDataFolder() + "/" + source)));
            } else {
                File outFile = new File(this.plugin.getDataFolder() + "/" + resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(this.plugin.getDataFolder() + "/" + resourcePath.substring(0, Math.max(lastIndex, 0)));
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
            this.reload(resourcePath);
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

    private void createYAML(String resourcePath, boolean replace) {
        this.createYAML(resourcePath, resourcePath, replace);
    }

    private boolean isStrictlyUTF8() {
        return this.plugin.getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8);
    }

}
