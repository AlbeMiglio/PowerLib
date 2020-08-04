package it.mycraft.powerlib;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PowerLib Official Source code
 *
 * @authors AlbeMiglio, pompiere1
 * https://www.github.com/AlbeMiglio/PowerLib
 */
public class PowerLib extends JavaPlugin {

    @Getter
    private static PowerLib instance;

    public void onEnable() {
        instance = this;
    }
}
