package it.mycraft.powerlib.bukkit.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemUtils {

    public static boolean compare(ItemStack i1, ItemStack i2, boolean ignoreAmount) {
        if (i1 == null || i2 == null || i1.getType() == Material.AIR || i2.getType() == Material.AIR) return false;
        if (i1 == i2) return true;
        if ((i1.getAmount() != i2.getAmount()) && !(ignoreAmount)) return false;
        Material comparisonType = (i1.getType().isLegacy()) ? Bukkit.getUnsafe().fromLegacy(i1.getData(), true) : i1.getType();
        return comparisonType == i2.getType()
                && i1.getDurability() == i2.getDurability()
                && i1.hasItemMeta() == i2.hasItemMeta()
                && new NBTItem(i1).getCompound().equals(new NBTItem(i2).getCompound());
    }

    public static boolean compare(ItemStack i1, ItemStack i2) {
        return compare(i1, i2, true);
    }
}