package it.mycraft.powerlibexample.bukkit;

import it.mycraft.powerlib.bukkit.inventory.InventoryBuilder;
import it.mycraft.powerlib.bukkit.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ExampleInventoryBuilder {

    public Inventory getInventory() {
        ItemStack bluePane = new ItemBuilder()
                .setMaterial(Material.STAINED_GLASS_PANE)
                .setMetaData((short) 11)
                .build();

        ItemStack redPane = new ItemBuilder()
                .setMaterial(Material.STAINED_GLASS_PANE)
                .setMetaData((short) 11)
                .build();

        ItemStack whitePane = new ItemBuilder()
                .setMaterial(Material.STAINED_GLASS_PANE)
                .setMetaData((short) 11)
                .build();

        return new InventoryBuilder()
                .setRows(6)
                .setTitle("&6I'm a USA GUI")
                .fillRow(1, whitePane)
                .fillRow(3, whitePane)
                .fillRow(5, whitePane)
                .fillRow(2, redPane)
                .fillRow(4, redPane)
                .fillRow(6, redPane)
                .setItem(0, bluePane)
                .setItem(1, bluePane)
                .setItem(2, bluePane)
                .setItem(3, bluePane)
                .setItem(9, bluePane)
                .setItem(10, bluePane)
                .setItem(11, bluePane)
                .setItem(12, bluePane)
                .setItem(18, bluePane)
                .setItem(19, bluePane)
                .setItem(20, bluePane)
                .setItem(21, bluePane)
                .build();
    }

    public void getFiveRowInventory(Player player) {
        new InventoryBuilder()
                .setRows(5)
                .setTitle("&7Hi %playerDisplayName")
                .fillInventory(new ItemBuilder()
                        .setMaterial(Material.STAINED_GLASS_PANE)
                        .setMetaData((short) 14)
                        .build())
                .fillRow(3, new ItemStack(Material.ICE))
                .fillColumn(5, new ItemBuilder()
                        .setMaterial("STONE")
                        .setName("&6I'm a column")
                        .build())
                .setItem(22, new ItemBuilder()
                        .setMaterial(Material.DIAMOND_BLOCK)
                        .setName("&6I'm the center")
                        .setGlowing(true)
                        .build())
                .fillBorder(new ItemBuilder()
                        .setMaterial(Material.EMERALD_BLOCK)
                        .setName("&6I'm the border")
                        .build())
                .addPlaceHolder("%playerDisplayName", player.getDisplayName())
                .open(player);
    }

}
