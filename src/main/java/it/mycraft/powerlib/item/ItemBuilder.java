package it.mycraft.powerlib.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import it.mycraft.powerlib.reflection.ReflectionAPI;
import it.mycraft.powerlib.utils.ColorAPI;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private Material material;

    private String name;
    private List<String> lore;
    private int amount;
    private short damage;
    private boolean glowing;

    public ItemBuilder() {
        amount = 1;
        damage = 0;
    }

    /**
     * This method sets the item material
     *
     * @param material The material
     * @return The ItemBuilder
     */
    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * This method sets the item material from a string
     *
     * @param material The material
     * @return The ItemBuilder
     */
    public ItemBuilder setMaterial(String material) {
        this.material = Material.valueOf(material);
        return this;
    }

    /**
     * This method sets the item name
     *
     * @param name The name
     * @return The ItemBuilder
     */
    public ItemBuilder setName(String name) {
        this.name = ColorAPI.color(name);
        return this;
    }

    /**
     * This method sets the item lore
     *
     * @param lore The lore
     * @return The ItemBuilder
     */
    public ItemBuilder setLore(String... lore) {
        this.lore = ColorAPI.color(Arrays.asList(lore));
        return this;
    }

    /**
     * This method sets the item lore
     *
     * @param lore The lore
     * @return The ItemBuilder
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore = ColorAPI.color(lore);
        return this;
    }

    /**
     * This method sets the item amount
     *
     * @param amount The amount
     * @return The ItemBuilder
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * This method sets the item damage for older minecraft versions
     *
     * @param damage The damage
     * @return The ItemBuilder
     */
    public ItemBuilder setDamage(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * This method sets the item glowing
     *
     * @param glowing True to set item glowing
     * @return The ItemBuilder
     */
    public ItemBuilder setGlowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    /**
     * This method clones your ItemStack in another ItemBuilder
     *
     * @param itemStack The ItemStack to clone
     * @return The ItemBuilder
     */
    public ItemBuilder clone(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        material = itemStack.getType();
        amount = itemStack.getAmount();
        damage = itemStack.getDurability();

        if (itemMeta.getDisplayName() != null)
            name = itemMeta.getDisplayName();

        if (itemMeta.hasLore())
            lore = itemMeta.getLore();

        if (itemMeta.hasEnchants())
            glowing = true;

        return this;
    }

    /**
     * This method sets a custom skin to a skull
     *
     * @param itemBuilder The itemBuilder
     * @param base64      The base64
     * @return The skull with a non-player skin
     */
    private ItemStack setCustomSkin(ItemBuilder itemBuilder, String base64) {
        String url = "http://textures.minecraft.net/texture/" + base64;
        int version = ReflectionAPI.getNumericalVersion();
        String material = "SKULL_ITEM";
        if (version >= 13) {
            material = "LEGACY_" + material;
        }
        ItemStack skull = itemBuilder.setMaterial(material).setDamage((byte) 3).setAmount(1).setName(name).setLore(lore).build();
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = null;

        if (version >= 14) {
            encodedData = org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64
                    .encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        }
        else {
            encodedData = org.apache.commons.codec.binary.Base64
                    .encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        }
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

    /**
     * This method sets a player skin to a skull
     *
     * @param itemBuilder The itemBuilder
     * @param playerName  The player name
     * @return The skull with a player skin
     */
    private ItemStack setPlayerSkin(ItemBuilder itemBuilder, String playerName) {
        int version = ReflectionAPI.getNumericalVersion();
        String material = "SKULL_ITEM";
        if (version >= 13) {
            material = "LEGACY_" + material;
        }
        ItemStack skull = itemBuilder.setMaterial(material)
                .setAmount(1).setDamage((short) 3).setName(name).setLore(lore).build();

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(playerName);
        skull.setItemMeta(skullMeta);

        return skull;
    }

    /**
     * This method sets a custom color to a leather armor
     *
     * @param red         The red value
     * @param green       The green value
     * @param blue        The blue value
     * @param itemBuilder The itemBuilder
     * @return The armor with a custom color
     */
    private ItemStack setColorToArmor(int red, int green, int blue, ItemBuilder itemBuilder) {
        ItemStack leatherArmor = itemBuilder.setMaterial(material).setName(name).setLore(lore).build();

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherArmor.getItemMeta();
        Color color = Color.fromRGB(red, green, blue);
        leatherArmorMeta.setColor(color);
        leatherArmor.setItemMeta(leatherArmorMeta);

        return leatherArmor;
    }

    /**
     * This method add a custom value on a placeholder to lore and name of the item
     *
     * @param placeholder The placeholder
     * @param value       The value
     * @return The ItemBuilder
     */
    public ItemBuilder addPlaceHolder(String placeholder, Object value) {
        name = name.replaceAll(placeholder, value.toString());

        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(s.replaceAll(placeholder, value.toString()));
        }
        lore = newLore;

        return this;
    }

    /**
     * This method use all datas gived to return the ItemStack
     *
     * @return The ItemStack
     */
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, damage);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name != null)
            itemMeta.setDisplayName(name);

        if (lore != null)
            itemMeta.setLore(lore);

        if (glowing) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemStack.setItemMeta(itemMeta);
        reset();
        return itemStack;
    }

    /**
     * This method use the skin url to make a custom skull
     *
     * @param skinURL The skin url
     * @return The ItemStack
     */
    public ItemStack customHeadBuild(String skinURL) {
        return setCustomSkin(this, skinURL);
    }

    /**
     * This method use the player name to make a player skull
     *
     * @param playerName The player name
     * @return The ItemStack
     */
    public ItemStack playerHeadBuild(String playerName) {
        return setPlayerSkin(this, playerName);
    }

    /**
     * This method use color values to sets a custom color to a leather armor
     *
     * @param red   The red value
     * @param green The green value
     * @param blue  The blue value
     * @return The ItemStack
     */
    public ItemStack coloredArmorBuild(int red, int green, int blue) {
        return setColorToArmor(red, green, blue, this);
    }

    /**
     * This is a security method used in case an instance is used to make many items
     */
    private void reset() {
        material = Material.STONE;

        name = "";
        lore = new ArrayList<>();
        amount = 1;
        damage = 0;

        glowing = false;
    }
}
