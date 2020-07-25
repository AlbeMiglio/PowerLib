package it.albemiglio.chancelib;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ChanceLib extends JavaPlugin {

    @Getter
    ChanceLib instance;

    public void onEnable() {
        instance = this;
        //TODO: Plugin's everything
    }
}
