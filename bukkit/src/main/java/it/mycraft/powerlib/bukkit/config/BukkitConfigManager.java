package it.mycraft.powerlib.bukkit.config;

import it.mycraft.powerlib.common.chat.Message;
import it.mycraft.powerlib.common.configuration.ConfigManager;
import it.mycraft.powerlib.common.configuration.Configuration;
import it.mycraft.powerlib.common.configuration.ConfigurationProvider;
import it.mycraft.powerlib.common.configuration.YamlConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author AlbeMiglio
 * @version 1.2.2
 */
public class BukkitConfigManager extends ConfigManager {

    private HashMap<String, Configuration> configs;
    private JavaPlugin plugin;
    private File folder;
    private File serverJar;
    private File pluginJar;

    public BukkitConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
        try {
            serverJar = new File(JavaPlugin.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            pluginJar = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            folder = this.plugin.getDataFolder();
            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (URISyntaxException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Exception encountered while creating ConfigManager! Please contact an administrator!");
        }
    }

    /**
     * Gets a config file from the local Map
     * Returns NULL if the config file isn't inside the map!
     *
     * @param file The config file name
     * @return The related FileConfiguration
     */
    public Configuration get(String file) {
        return this.configs.getOrDefault(file, null);
    }

    /**
     * Same as #create(String,String) but source name equals to the new one
     *
     * @param file The config file name
     * @return The new file
     */
    public Configuration create(String file, String source) {
        File resourcePath = new File(folder + File.separator + file);
        if (!resourcePath.exists()) {
            createYAML(file, source, false);
        }
        reload(file);
        return this.configs.get(file);
    }

    public Configuration create(String file) {
        return create(file, file);
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
                    .save(config, new File(folder + File.separator + file));
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Exception encountered while saving a yaml file! Please contact an administrator!");
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
            createYAML(file, false);
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
                    .load(new File(folder + File.separator + file));
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Exception encountered while loading a yaml file! Please contact an administrator!");
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
     * @param resourcePath The file name
     * @param source       The source file name
     * @param replace      Whether the file has to be replaced by the default one although it already exists
     * @author Original code from JavaPlugin.class
     */
    private void createYAML(String resourcePath, String source, boolean replace) {
        try {
            File file = new File(folder + File.separator + resourcePath);
            if (!file.getParentFile().exists() || !file.exists()) {
                file.getParentFile().mkdirs();
                if (file.createNewFile()) {
                    replace = true;
                }
                if (file.length() == 0) {
                    replace = true;
                }
                if (replace) {
                    Files.copy(getResourceAsStream(source), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else Files.copy(getResourceAsStream(source), file.toPath());
            }
        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            new Message("&cException encountered while creating a yaml file! Please contact an administrator!",
                    "&cError message: %msg")
                    .addPlaceHolder("%msg", e.getMessage())
                    .sendConsole();
        }
    }

    private void createYAML(String resourcePath, boolean replace) {
        this.createYAML(resourcePath, resourcePath, replace);
    }


    private InputStream getResourceAsStream(String name) throws ClassNotFoundException, URISyntaxException, IOException {

        try (ZipFile file = new ZipFile(pluginJar);
             ZipInputStream zip = new ZipInputStream(pluginJar.toURL().openStream())) {
            boolean stop = false;
            while (!stop) {
                ZipEntry e = zip.getNextEntry();
                if (e == null) {
                    stop = true;
                    continue;
                }
                if (e.getName().equals(name)) {
                    return cloneInputStream(file.getInputStream(e));
                }
            }
        }
        return null;
    }

    private InputStream cloneInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] byteArray = outputStream.toByteArray();
        return new ByteArrayInputStream(byteArray);
    }
}