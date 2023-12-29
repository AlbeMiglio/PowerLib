package it.mycraft.powerlib.configuration;

@Deprecated
public abstract class ConfigManager {

    public abstract Configuration get(String file);

    /**
     * Same as #create(String,String) but source name equals to the new one
     *
     * @param file The config file name
     * @return The new file
     */
    public abstract Configuration create(String file, String source);

    public abstract Configuration create(String file);

    /**
     * Saves the config file changes and updates it in the local Map
     *
     * @param file The config file name
     */
    public abstract void save(String file);

    /**
     * Reloads a config file
     *
     * @param file The config file name
     * @author Original code from JavaPlugin.class
     */
    public abstract void reload(String file);

    /**
     * Reloads all config files
     */
    public abstract void reloadAll();
}
