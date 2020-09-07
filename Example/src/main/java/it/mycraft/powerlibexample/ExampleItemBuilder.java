package it.mycraft.powerlibexample;

import it.mycraft.powerlib.chance.RandomDraw;
import it.mycraft.powerlib.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ExampleItemBuilder {

    private ItemStack melon;

    public ExampleItemBuilder() {
        melon = new ItemBuilder()
                .setMaterial("MELON_BLOCK")
                .setAmount(9)
                .setName("&aMelon")
                .setLore("&1I'm a melon.", "&6Nice to meet you!")
                .build();
    }

    public ItemStack giveSword(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("&6Solo chi riuscirà a");
        lore.add("&6rimuovere la spada");
        lore.add("&6dalla roccia, la potrà");
        lore.add("&6impugnare.");
        lore.add("&6");
        lore.add("&7Owner: &6&K&LOO&c%player&6&K&LOO");
        return new ItemBuilder()
                .setMaterial(Material.DIAMOND_SWORD)
                .setName("La spada nella roccia of %player")
                .setLore(lore)
                .addPlaceHolder("%player", player.getDisplayName())
                .setGlowing(true)
                .build();
    }

    public ItemStack giveCustomHead() {
        return new ItemBuilder()
                .setName("&dTV!!!")
                .setAmount(64)
                .setGlowing(true)
                .customHeadBuild("add521d321abe06d139e5f5cf29f6734a65beb31c13794a5ec0fdcda121b9fe6");
    }

    public ItemStack giveDeveloperHead() {
        RandomDraw randomDraw = new RandomDraw();
        randomDraw.addItem("AlbeMiglio", 1);
        randomDraw.addItem("Pompiere1", 1);
        String randomHead = (String) randomDraw.shuffle(false);
        return new ItemBuilder()
                .playerHeadBuild(randomHead);
    }

    public ItemStack givePlayerHead() {
        return new ItemBuilder()
                .playerHeadBuild("MrRazer_");
    }

    public ItemStack giveRandomColoredGlass() {
        RandomDraw randomDraw = new RandomDraw();
        for (int i = 0; i < 16; i++) {
            randomDraw.addItem(i, 1);
        }
        return new ItemBuilder()
                .setMaterial(Material.STAINED_GLASS_PANE)
                .setAmount(100)
                .setMetaData((short) randomDraw.shuffle(false))
                .setGlowing(true)
                .build();
    }

    public ItemStack giveColoredArmor() {
        return new ItemBuilder()
                .setMaterial(Material.LEATHER_CHESTPLATE)
                .setGlowing(true)
                .coloredArmorBuild(154, 178, 52);
    }

    public ItemStack getMelon() {
        return this.melon;
    }

    public ItemStack getPotion(){
        return new ItemBuilder()
                .setMaterial(373)
                .setName("God help us")
                .potionBuild(PotionEffectType.HEALTH_BOOST, 30000, 2, true);
    }
}
