package it.mycraft.powerlib;

import it.mycraft.powerlib.inventory.InventoryBuilder;
import it.mycraft.powerlib.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

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

        ItemStack item = new ItemBuilder()
                .setMaterial("DIRT")
                .setAmount(1)
                .setName("Dirt")
                .setLore(Arrays.asList("This is", "a dirt block"))
                .setGlowing(true)
                .build();

        Inventory inventory = new InventoryBuilder()
                .setRows(5)
                .setTitle("&cThis is a title")
                .fillInventory(new ItemBuilder()
                        .setMaterial(Material.STAINED_GLASS_PANE)
                        .setDamage((short) 15)
                        .build())
                .fillColumn(5, item)
                .fillRow(3, new ItemStack(Material.STONE))
                .setItem(22, new ItemBuilder()
                        .setMaterial("DIAMOND_BLOCK")
                        .setAmount(64)
                        .setName("&f&k&lOO&r&6I'm the center&f&k&lOO")
                        .setLore("&9This", "&9is", "&9an", "&9item")
                        .setGlowing(true)
                        .build())
                .setItem(21, new ItemBuilder()
                        .setMaterial(Material.QUARTZ_BLOCK)
                        .build())
                .setItem(23, new ItemBuilder()
                        .setMaterial(Material.QUARTZ_BLOCK)
                        .build())
                .build();

        new InventoryBuilder()
                .setRows(1)
                .setTitle("&8Title")
                .fillInventory(new ItemBuilder()
                        .setMaterial(Material.STAINED_GLASS_PANE)
                        .setDamage((short) 7)
                        .build())
                .open(Bukkit.getPlayer("Player"));
    }
}
