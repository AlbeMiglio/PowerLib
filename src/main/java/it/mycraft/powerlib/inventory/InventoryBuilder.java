package it.mycraft.powerlib.inventory;

import it.mycraft.powerlib.item.ItemBuilder;
import it.mycraft.powerlib.utils.ColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryBuilder {

    private int size;
    private String title;
    private Map<Integer, ItemStack> setItem;
    private Map<Integer, ItemStack> fillRow;
    private Map<Integer, ItemStack> fillColumn;
    private ItemStack fillInventory;

    public InventoryBuilder() {
        setItem = new HashMap<>();
        fillRow = new HashMap<>();
        fillColumn = new HashMap<>();
    }

    /**
     * Sets the rows' number
     *
     * @param rows Amount of rows
     * @return The InventoryBuilder
     */
    public InventoryBuilder setRows(int rows) {
        this.size = rows * 9;
        return this;
    }

    /**
     * Sets the inventory title
     *
     * @param title The title
     * @return The InventoryBuilder
     */
    public InventoryBuilder setTitle(String title) {
        this.title = ColorAPI.color(title);
        return this;
    }

    /**
     * Sets an item in the inventory
     *
     * @param slot      The slot
     * @param itemStack The item to set
     * @return The InventoryBuilder
     */
    public InventoryBuilder setItem(Integer slot, ItemStack itemStack) {
        this.setItem.put(slot, itemStack);
        return this;
    }

    /**
     * Sets an item in an entire row
     *
     * @param row       The row
     * @param itemStack The item to set
     * @return The InventoryBuilder
     */
    public InventoryBuilder fillRow(Integer row, ItemStack itemStack) {
        this.fillRow.put(row, itemStack);
        return this;
    }

    /**
     * Sets an item in an entire column
     *
     * @param column    The column
     * @param itemStack The item to set
     * @return The InventoryBuilder
     */
    public InventoryBuilder fillColumn(Integer column, ItemStack itemStack) {
        this.fillColumn.put(column, itemStack);
        return this;
    }

    /**
     * Sets an item in the entire inventory
     *
     * @param itemStack The item
     * @return The InventoryBuilder
     */
    public InventoryBuilder fillInventory(ItemStack itemStack) {
        this.fillInventory = itemStack;
        return this;
    }

    /**
     * Builds an Inventory with the itemstacks provided previously
     *
     * @return The Inventory
     */
    public Inventory build() {
        Inventory inventory = Bukkit.createInventory(null, size, title);

        if (fillInventory != null) {
            fillInventory(inventory);
        }

        setRowAndColumnItems(inventory);
        setItems(inventory);

        reset();
        return inventory;
    }

    /**
     * Opens the built inventory to the player
     *
     * @param player The player
     */
    public void open(Player player) {
        open(player, this);
    }

    /**
     * Opens a built inventory to the player
     *
     * @param player           The player
     * @param inventoryBuilder The InventoryBuilder
     */
    private void open(Player player, InventoryBuilder inventoryBuilder) {
        player.openInventory(inventoryBuilder.build());
    }

    /**
     * Fills an inventory with an item
     *
     * @param inventory The inventory
     * @return The filled inventory
     */
    private Inventory fillInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, fillInventory);
        return inventory;
    }

    /**
     * Fills an inventory's rows and columns
     *
     * @param inventory The inventory
     * @return The filled inventory
     */
    private Inventory setRowAndColumnItems(Inventory inventory) {
        if (fillRow.size() != 0) {
            for (Integer integer : fillRow.keySet()) {
                int max = (integer * 9) - 1;
                int min = max - 8;
                for (int ignored; min <= max; min++) {
                    inventory.setItem(min, fillRow.get(integer));
                }
            }
        }
        if (fillColumn.size() != 0) {
            for (Integer integer : fillColumn.keySet()) {
                for (int i = integer - 1; i < inventory.getSize(); i = i + 9) {
                    System.out.println(i);
                    inventory.setItem(i, fillColumn.get(integer));
                }
            }
        }
        return inventory;
    }

    /**
     * Sets the provided items in an inventory
     *
     * @param inventory The inventory
     * @return The inventory filled
     */
    private Inventory setItems(Inventory inventory) {
        if (setItem.size() != 0) {
            for (Integer integer : setItem.keySet()) {
                inventory.setItem(integer, setItem.get(integer));
            }
        }
        return inventory;
    }

    /**
     * Adds a custom value on a placeholder to the inventory's title
     *
     * @param placeholder The placeholder
     * @param value       The value
     * @return The InventoryBuilder
     */
    public InventoryBuilder addPlaceHolder(String placeholder, Object value) {
        title = title.replaceAll(placeholder, value.toString());

        return this;
    }

    /**
     * Just puts in the InventoryBuilder object its default values
     */
    private void reset() {
        setItem = new HashMap<>();
        fillRow = new HashMap<>();
        fillColumn = new HashMap<>();
        int size = 9;
        String title = "null";
        fillInventory = new ItemBuilder().setMaterial(Material.STONE).build();
    }
}
