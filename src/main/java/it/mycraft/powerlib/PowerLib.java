package it.mycraft.powerlib;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class PowerLib extends JavaPlugin {

    @Getter
    private static PowerLib instance;

    public void onEnable() {
        instance = this;
        //TODO: Plugin's everything
        //ConfigAPI.create("Plugin", "config.yml");
        //FileConfiguration config = ConfigAPI.get("Plugin", "config.yml");
        //
        //RandomDraw r = new RandomDraw();
        // r.addItem("Venticinquepercento", 25);
        // r.addItem("Settantapercento", 70);
        // r.addItem("cinquepercento", 5);
        // int totalChance = r.getTotalChance();
        // int probabilityA = r.getProbability("Venticinquepercento");  // outputs  25.0 / 100.0  = 0.25
        // String rand = r.shuffle();
    }
}
