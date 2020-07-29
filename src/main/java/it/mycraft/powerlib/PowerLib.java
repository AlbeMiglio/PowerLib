package it.mycraft.powerlib;

import it.mycraft.powerlib.chance.RandomDraw;
import it.mycraft.powerlib.config.ConfigManager;
import it.mycraft.powerlib.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 *  PowerLib Official Source code
 *  @authors AlbeMiglio, pompiere1
 *  https://www.github.com/AlbeMiglio/PowerLib
 */
public class PowerLib extends JavaPlugin {

    @Getter
    private static PowerLib instance;

    public void onEnable() {
        instance = this;

        RandomDraw r = new RandomDraw();
        r.addItem("Twenty-five-percent", 25);
        r.addItem("Seventy-percent", 70);
        r.addItem("Five-percent", 5);
        Double totalChance = r.getTotalChance(false);                        // outputs (25.0 + 70.0 + 5.0) = 100.0
        Double probabilityA = r.getProbability("Twenty-five-percent", false);// outputs (25.0 / 100.0)  = 0.25
        String rand = (String) r.shuffle(false);                             // outputs We don't know! Try it urself ;)

        ConfigManager cm = new ConfigManager(this);
        FileConfiguration config = cm.create("config.yml");
        cm.save(config);
        cm.reload("config.yml");
        cm.reloadAll();
        config = cm.get("config.yml");

        ItemStack item = new ItemBuilder()
                .setMaterial("DIRT")
                .setDamage((byte) 3)
                .setAmount(1)
                .setName("Dirt")
                .setLore(Arrays.asList("This is", "a dirt block"))
                .setGlowing(true)
                .build();
    }
}
