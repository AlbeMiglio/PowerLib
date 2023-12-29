package it.mycraft.powerlib.common.configuration;

import java.util.HashMap;
import java.util.Map;

public class ProviderRegistry {

    private static final Map<Class<? extends ConfigurationProvider>, ConfigurationProvider> providers = new HashMap<>();

    static {
        try {
            providers.put(YamlConfiguration.class, new YamlConfiguration());
        } catch (NoClassDefFoundError ex) {
            // Ignore, no SnakeYAML
        }

        try {
            providers.put(JsonConfiguration.class, new JsonConfiguration());
        } catch (NoClassDefFoundError ex) {
            // Ignore, no Gson
        }
    }

    public static ConfigurationProvider getProvider(Class<? extends ConfigurationProvider> provider) {
        return providers.get(provider);
    }
}
