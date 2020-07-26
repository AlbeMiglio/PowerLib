package it.mycraft.powerlib;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class PowerLib extends JavaPlugin {

    @Getter
    PowerLib instance;

    public void onEnable() {
        instance = this;
        //TODO: Plugin's everything
    }
}
