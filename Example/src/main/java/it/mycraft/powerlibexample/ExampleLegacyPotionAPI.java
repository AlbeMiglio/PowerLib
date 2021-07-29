package it.mycraft.powerlibexample;

import it.mycraft.powerlib.bukkit.item.LegacyPotionAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExampleLegacyPotionAPI {

    public void givePotion(Player player){
        ItemStack potion = LegacyPotionAPI.getPotion("16454");
        player.getInventory().addItem(potion);
    }

    public ItemStack givePotion(){
        return LegacyPotionAPI.getPotion(8193);
    }


}
