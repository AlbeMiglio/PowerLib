package it.mycraft.powerlib.bukkit;

import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;

public class PowerLib {

    private static BukkitAudiences adventure;

    public static void inject(Plugin plugin) {
        adventure = BukkitAudiences.create(plugin);
    }

    public static @NonNull BukkitAudiences adventure() {
        if(adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }
}
