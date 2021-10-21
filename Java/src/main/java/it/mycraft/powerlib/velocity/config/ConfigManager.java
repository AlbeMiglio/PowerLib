package it.mycraft.powerlib.velocity.config;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginDescription;
import it.mycraft.powerlib.configuration.Configuration;
import it.mycraft.powerlib.configuration.ConfigurationProvider;
import it.mycraft.powerlib.configuration.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

/**
 * @author AlbeMiglio
 * @version 1.2.0-TEST-5
 */
public class ConfigManager {

    private HashMap<String, Configuration> configs;
    private PluginDescription pluginDescription;
    private Object plugin;
    private File folder;
    private File serverJar;
    private File pluginJar;

    public ConfigManager(Object plugin, PluginDescription pluginDescription) {
        this.configs = new HashMap<>();
        this.pluginDescription = pluginDescription;
        this.plugin = plugin;
        try {
            serverJar = new File(Plugin.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            pluginJar = new File(serverJar.getParentFile(), pluginDescription.getSource().get().toString());
            folder = new File(serverJar.getParentFile() + "/plugins/"+this.pluginDescription.getId());
            if (!folder.exists()) {
                folder.mkdir();
            }
        }
        catch(URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets a config file from the local Map and creates it if it doesn't exist
     *
     * @param file The config file name
     * @return The related FileConfiguration
     */
    public Configuration get(String file) {
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
    public Configuration create(String file, String source) {
        File resourcePath = new File(folder + "/" + file);
        if (!resourcePath.exists()) {
            createYAML(resourcePath, source, false);
        } else this.reload(file);
        return this.configs.get(file);
    }

    /**
     * Same as #create(String,String) but source name equals to the new one
     *
     * @param file The config file name
     * @return The new file
     */
    public Configuration create(String file) {
        return this.create(file, file);
    }

    /**
     * Saves the config file changes and updates it in the local Map
     *
     * @param file The config file name
     */
    public void save(String file) {
        Configuration config = get(file);
        if (config == null) {
            throw new IllegalArgumentException("The specified configuration file doesn't exist!");
        }
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .save(config, new File(folder + "/" + file));
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
        if (!this.configs.containsKey(file)) {
            createYAML(new File(this.folder + "/" + file), false);
        }
        Configuration conf = this.load(file);
        this.put(file, conf);
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
    private Configuration load(String file) {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(folder + "/" + file));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Puts a config in the local Map
     *
     * @param config The config file
     */
    private void put(String file, Configuration config) {
        this.configs.put(file, config);
    }

    /**
     * Creates the file by looking for it inside the jar's resources, then loads it and puts it in the local Map
     *
     * @param file The file name
     * @param source       The source file name
     * @param replace      Whether the file has to be replaced by the default one although it already exists
     * @author Original code from JavaPlugin.class
     */
    private void createYAML(File file, String source, boolean replace) {
        try {
            if (!file.exists()) {
                if (replace) {
                    Files.copy(getResourceAsStream(source),
                            file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else Files.copy(getResourceAsStream(source), file.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createYAML(File resourcePath, boolean replace) {
        this.createYAML(resourcePath, resourcePath.getName(), replace);
    }

    private InputStream getResourceAsStream(String name) {
        File file = new File(pluginJar + "/" + name);
        System.out.println("file to get stream: "+file);
        System.out.println("classloader 1: "+ this.plugin.getClass().getClassLoader());
        System.out.println("classloader 2: "+ ClassLoader.getSystemClassLoader());
        return this.plugin.getClass().getClassLoader().getResourceAsStream(file.getPath());
    }
}


