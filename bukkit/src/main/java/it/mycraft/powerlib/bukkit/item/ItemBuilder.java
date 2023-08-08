package it.mycraft.powerlib.bukkit.item;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import it.mycraft.powerlib.bukkit.reflection.ReflectionAPI;
import it.mycraft.powerlib.common.chat.Message;
import it.mycraft.powerlib.common.configuration.Configuration;
import it.mycraft.powerlib.common.utils.ColorAPI;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class ItemBuilder implements Cloneable {

    private Material material;
    private String name;
    private List<String> lore;
    private int amount;
    private short metadata;
    private boolean glowing;
    private HashMap<Enchantment, Integer> enchantments;
    private HashMap<PotionEffect, Boolean> potions;
    private HashMap<String, Object> placeholders;

    public ItemBuilder() {
        lore = new ArrayList<>();
        enchantments = new HashMap<>();
        potions = new HashMap<>();
        placeholders = new HashMap<>();
        amount = 1;
        metadata = 0;
    }

    /**
     * Sets the item's material
     *
     * @param material The material
     * @return The ItemBuilder
     */
    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Sets the item's material getting it from a String
     *
     * @param material The material
     * @return The ItemBuilder
     */
    public ItemBuilder setMaterial(String material) {
        Optional<Material> optMaterial = Enums.getIfPresent(Material.class, material);
        if (optMaterial.isPresent())
            this.material = optMaterial.get();
        return this;
    }

    /**
     * Sets the item's material getting it from an int ID. 1.13+
     *
     * @param id The ID
     * @return The ItemBuilder
     */
    public ItemBuilder setMaterial(int id) {
        this.material = LegacyItemAPI.getMaterial(id);
        return this;
    }

    /**
     * Sets the item's material data
     *
     * @param id   The ID
     * @param data The Data
     * @return The ItemBuilder
     */
    public ItemBuilder setMaterial(int id, int data) {
        this.material = LegacyItemAPI.getMaterial(id, data);
        return this;
    }

    /**
     * Sets the item's custom name
     *
     * @param name The name
     * @return The ItemBuilder
     */
    public ItemBuilder setName(String name) {
        this.name = ColorAPI.color(name);
        return this;
    }

    /**
     * Sets the item's lore Array
     *
     * @param lore The lore array
     * @return The ItemBuilder
     */
    public ItemBuilder setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Sets the item's lore List
     *
     * @param lore The lore list
     * @return The ItemBuilder
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore = ColorAPI.color(lore);
        return this;
    }

    /**
     * Sets the item's enchantment
     *
     * @param enchantment The enchant
     * @param level       The level
     * @return The ItemBuilder
     */
    public ItemBuilder setEnchantment(Enchantment enchantment, Integer level) {
        enchantments.put(enchantment, level);
        return this;
    }


    /**
     * Sets the potion's effect
     *
     * @param type     The effect type
     * @param duration The duration measured in ticks
     * @param level    The amplifier
     * @return The ItemBuilder
     */
    public ItemBuilder setPotionEffect(PotionEffectType type, int duration, int level) {
        return setPotionEffect(type, duration, level, true, true, true);
    }

    /**
     * Sets the potion's effect
     *
     * @param type      The potion effect type to add
     * @param duration  The duration measured in ticks
     * @param level     The amplifier
     * @param overwrite true if any existing effect of the same type should be overwritten
     * @return The ItemBuilder
     */
    public ItemBuilder setPotionEffect(PotionEffectType type, int duration, int level, boolean overwrite) {
        return setPotionEffect(type, duration, level, overwrite, true, true);
    }

    /**
     * Sets the potion's effect
     *
     * @param type      The potion effect type to add
     * @param duration  The duration measured in ticks
     * @param level     The amplifier
     * @param overwrite true if any existing effect of the same type should be overwritten
     * @param ambient   The ambient status
     * @return The ItemBuilder
     */
    public ItemBuilder setPotionEffect(PotionEffectType type, int duration, int level, boolean overwrite, boolean ambient) {
        return setPotionEffect(type, duration, level, overwrite, ambient, true);
    }

    /**
     * Sets the potion's effect
     *
     * @param type      The potion effect type to add
     * @param duration  The duration measured in ticks
     * @param level     The amplifier
     * @param overwrite true if any existing effect of the same type should be overwritten
     * @param ambient   The ambient status
     * @param particles The particle status
     * @return The ItemBuilder
     */
    public ItemBuilder setPotionEffect(PotionEffectType type, int duration, int level, boolean overwrite, boolean ambient, boolean particles) {
        potions.put(new PotionEffect(type, duration, (level - 1), overwrite, ambient), particles);
        return this;
    }

    /**
     * Sets the item's amount
     *
     * @param amount The amount
     * @return The ItemBuilder
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Dets the item's metadata (for older Minecraft versions)
     *
     * @param metadata The metadata
     * @return The ItemBuilder
     */
    public ItemBuilder setMetaData(short metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Sets the item's itemGlowing
     *
     * @param itemGlowing True to set item itemGlowing
     * @return The ItemBuilder
     */
    public ItemBuilder setGlowing(boolean itemGlowing) {
        this.glowing = itemGlowing;
        return this;
    }

    /**
     * Clones another itemstack into the builder and stores its data
     *
     * @param itemStack The ItemStack to clone
     * @return The ItemBuilder
     */
    public ItemBuilder clone(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        material = itemStack.getType();
        amount = itemStack.getAmount();
        metadata = itemStack.getDurability();

        if (itemMeta != null) {
            name = itemMeta.getDisplayName();
        }

        if (itemMeta.hasLore())
            lore = itemMeta.getLore();

        if (itemMeta.hasEnchants() && itemMeta.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS))
            glowing = true;

        return this;
    }

    /**
     * Sets a custom skin to a skull
     *
     * @param itemBuilder The itemBuilder
     * @param url         The minecraft.net url
     * @return The skull with a non-player skin
     */
    private ItemStack setCustomSkin(ItemBuilder itemBuilder, String url) {
        int version = ReflectionAPI.getNumericalVersion();
        String material = version >= 13 ? "PLAYER_HEAD" : "SKULL_ITEM";
        ItemStack skull = itemBuilder.setMaterial(material).setMetaData((byte) 3).setAmount(1).setName(name).setLore(lore).build();
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData;

        encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());

        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            new Message("Error while creating an head with custom skin!", "Message: " + ex.getMessage()).sendConsole();
            return null;
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

    /**
     * Sets a player's skin to a skull
     *
     * @param itemBuilder The itemBuilder
     * @param playerName  The player name
     * @return The skull with a player skin
     */
    private ItemStack setPlayerSkin(ItemBuilder itemBuilder, String playerName) {
        int version = ReflectionAPI.getNumericalVersion();
        String material = version >= 13 ? "PLAYER_HEAD" : "SKULL_ITEM";
        ItemStack skull = itemBuilder
                .setMaterial(material)
                .setAmount(1)
                .setMetaData((short) 3)
                .setName(name)
                .setLore(lore)
                .build();

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(playerName);
        skull.setItemMeta(skullMeta);

        return skull;
    }

    /**
     * Sets a custom RGB color to a leather armor
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
        try {
            Color color = Color.fromRGB(red, green, blue);
            leatherArmorMeta.setColor(color);
        } catch (Exception ignored) {
        }
        leatherArmor.setItemMeta(leatherArmorMeta);
        return leatherArmor;
    }

    /**
     * Adds a custom value on a placeholder to the item's name and lore
     *
     * @param placeholder The placeholder
     * @param value       The value
     * @return The ItemBuilder
     */
    public ItemBuilder addPlaceHolder(String placeholder, Object value) {
        placeholders.put(placeholder, value);
        return this;
    }

    /**
     * Builds an ItemStack with the data provided previously
     *
     * @return The ItemStack
     */
    public ItemStack build() {
        for(String placeholder : placeholders.keySet()) {
            Object value = placeholders.get(placeholder);
            setName(name.replace(placeholder, value.toString()));

            setLore(lore.stream().map((s) -> s.replace(placeholder, value.toString()))
                    .collect(Collectors.toList()));
        }
        ItemStack itemStack = new ItemStack(material, amount, metadata);

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null)
            itemMeta.setDisplayName(name);

        if (lore != null)
            itemMeta.setLore(lore);

        if (glowing && enchantments.isEmpty()) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (!enchantments.isEmpty()) {
            for (Enchantment enchantment : enchantments.keySet()) {
                itemMeta.addEnchant(enchantment, enchantments.get(enchantment), true);
            }
        }

        itemStack.setItemMeta(itemMeta);

        if (material == Material.POTION && !potions.isEmpty()) {
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            for (PotionEffect potionEffect : potions.keySet()) {
                potionMeta.addCustomEffect(potionEffect, potions.get(potionEffect));
            }
            itemStack.setItemMeta(potionMeta);
        }
        return itemStack;
    }

    /**
     * Uses the skin URL to make a custom skull
     *
     * @param skinURL The skin url
     * @return The ItemStack
     */
    public ItemStack customHeadBuild(String skinURL) {
        if (!skinURL.endsWith("="))
            return setCustomSkin(this, "http://textures.minecraft.net/texture/" + skinURL);

        return setCustomSkin(this, base64Decoder(skinURL));
    }

    /**
     * This method is used for extracting the minecraft.net link from the base64 text
     *
     * @param base64 The base64 text
     * @return The minecraft.net link
     */
    private String base64Decoder(String base64) {
        byte[] decoded = Base64.getDecoder().decode(base64);
        String s = new String(decoded);

        return s.replace("\"}}}", "").split(":\"")[1];
    }

    /**
     * Uses the player's name to make a player skull
     *
     * @param playerName The player name
     * @return The ItemStack
     */
    public ItemStack playerHeadBuild(String playerName) {
        return setPlayerSkin(this, playerName);
    }

    /**
     * Uses RGB color values to set a custom color to a leather armor
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
     * Looks for any item's info in the provided FileConfiguration and builds an ItemStack from it
     *
     * @param fileConfiguration The file configuration to get the item's info from
     * @param path              The section where the item's info are stored
     * @return The related ItemBuilder
     */
    public ItemBuilder fromConfig(FileConfiguration fileConfiguration, String path) {
        boolean itemLegacy = false;
        boolean itemGlowing = false;
        String newPath;
        String itemMaterial = "STONE";
        String itemName = null;
        List<String> itemLore = null;
        int itemAmount = 1;
        short itemMetadata = 0;

        for (String s : fileConfiguration.getConfigurationSection(path).getKeys(false)) {
            switch (s) {
                case "legacy":
                    newPath = path + ".legacy";
                    itemLegacy = fileConfiguration.getBoolean(newPath);
                    break;
                case "material":
                    newPath = path + ".material";
                    itemMaterial = itemLegacy ? "LEGACY_" + fileConfiguration.getString(newPath) :
                            fileConfiguration.getString(newPath);
                    break;
                case "name":
                    newPath = path + ".name";
                    itemName = fileConfiguration.getString(newPath);
                    break;
                case "lore":
                    newPath = path + ".lore";
                    itemLore = fileConfiguration.getStringList(newPath);
                    break;
                case "amount":
                    newPath = path + ".amount";
                    itemAmount = fileConfiguration.getInt(newPath);
                    break;
                case "metadata":
                    newPath = path + ".metadata";
                    itemMetadata = (short) fileConfiguration.getInt(newPath);
                    break;
                case "glowing":
                    newPath = path + ".glowing";
                    itemGlowing = fileConfiguration.getBoolean(newPath);
                    break;
                default:
                    break;
            }
        }
        return this.setMaterial(itemMaterial)
                .setName(itemName).setLore(itemLore)
                .setAmount(itemAmount).setMetaData(itemMetadata)
                .setGlowing(itemGlowing);
    }

    /**
     * Looks for any item's info in the provided NATIVE Configuration and builds an ItemStack from it
     *
     * @param configuration NATIVE CONFIGURATION to get the item's info from
     * @param path              The section where the item's info are stored
     * @return The related ItemBuilder
     */
    public ItemBuilder fromConfig(Configuration configuration, String path) {
        boolean itemLegacy = false;
        boolean itemGlowing = false;
        String newPath;
        String itemMaterial = "STONE";
        String itemName = null;
        List<String> itemLore = null;
        int itemAmount = 1;
        short itemMetadata = 0;

        for (String s : configuration.getSection(path).getKeys()) {
            switch (s) {
                case "legacy":
                    newPath = path + ".legacy";
                    itemLegacy = configuration.getBoolean(newPath);
                    break;
                case "material":
                    newPath = path + ".material";
                    itemMaterial = itemLegacy ? "LEGACY_" + configuration.getString(newPath) :
                            configuration.getString(newPath);
                    break;
                case "name":
                    newPath = path + ".name";
                    itemName = configuration.getString(newPath);
                    break;
                case "lore":
                    newPath = path + ".lore";
                    itemLore = configuration.getStringList(newPath);
                    break;
                case "amount":
                    newPath = path + ".amount";
                    itemAmount = configuration.getInt(newPath);
                    break;
                case "metadata":
                    newPath = path + ".metadata";
                    itemMetadata = (short) configuration.getInt(newPath);
                    break;
                case "glowing":
                    newPath = path + ".glowing";
                    itemGlowing = configuration.getBoolean(newPath);
                    break;
                default:
                    break;
            }
        }
        return this.setMaterial(itemMaterial)
                .setName(itemName).setLore(itemLore)
                .setAmount(itemAmount).setMetaData(itemMetadata)
                .setGlowing(itemGlowing);
    }

    public ItemBuilder hex() {
        setName(ColorAPI.hex(getName()));
        setLore(ColorAPI.hex(getLore()));
        return this;
    }

    /**
     * Just puts in the ItemBuilder object its default values
     */
    private void reset() {
        material = Material.STONE;
        name = "";
        glowing = false;
        lore = new ArrayList<>();
        enchantments = new HashMap<>();
        potions = new HashMap<>();
        placeholders = new HashMap<>();
        amount = 1;
        metadata = 0;
    }

    @Override
    public ItemBuilder clone() {
        try {
            ItemBuilder clone = (ItemBuilder) super.clone();
            clone.enchantments = new HashMap<>(enchantments);
            clone.potions = new HashMap<>(potions);
            clone.placeholders = new HashMap<>(placeholders);
            clone.lore = new ArrayList<>(lore);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
