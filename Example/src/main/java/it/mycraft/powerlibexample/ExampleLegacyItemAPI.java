package it.mycraft.powerlibexample;

import it.mycraft.powerlib.item.LegacyItemAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ExampleLegacyItemAPI {

    public void setBrownConcrete(Player player) {
        player.getInventory().getItemInHand().setType(LegacyItemAPI.getMaterial(252, 12));
    }

    public void setMaterial() {
        Material stone = LegacyItemAPI.getMaterial(1);
    }

    public Material getEgg() {
        return LegacyItemAPI.getMaterial(383, 65);
    }

    public void setStone(Player player) {
        player.getLocation().getBlock().setType(LegacyItemAPI.STONE.toMaterial());
    }
}