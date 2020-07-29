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

    public InventoryBuilder(){
        setItem = new HashMap<>();
        fillRow = new HashMap<>();
        fillColumn = new HashMap<>();
    }

    /**
     * This method set the rows' number
     * @param rows Amount of rows
     * @return The InventoryBuilder
     */
    public InventoryBuilder setRows(int rows){
        this.size = rows * 9;
        return this;
    }

    /**
     * This method set the inventory title
     * @param title The title
     * @return The InventoryBuilder
     */
    public InventoryBuilder setTitle(String title){
        this.title = ColorAPI.color(title);
        return this;
    }

    /**
     * This method set an or more items in the inventory
     * @param slot The slot
     * @param itemStack The item to set
     * @return The InventoryBuilder
     */
    public InventoryBuilder setItem(Integer slot, ItemStack itemStack){
        this.setItem.put(slot, itemStack);
        return this;
    }

    /**
     * This method set an item into a row
     * @param row The row
     * @param itemStack The item to set
     * @return The InventoryBuilder
     */
    public InventoryBuilder fillRow(Integer row, ItemStack itemStack){
        this.fillRow.put(row, itemStack);
        return this;
    }

    /**
     * This method set an item into a column
     * @param column The column
     * @param itemStack The item to set
     * @return The InventoryBuilder
     */
    public InventoryBuilder fillColumn(Integer column, ItemStack itemStack){
        this.fillColumn.put(column, itemStack);
        return this;
    }

    /**
     * This method fill all inventory with an item
     * @param itemStack The item
     * @return The InventoryBuilder
     */
    public InventoryBuilder fillInventory(ItemStack itemStack){
        this.fillInventory = itemStack;
        return this;
    }

    /**
     * This method use all datas gived to return the Inventory
     * @return The Inventory
     */
    public Inventory build() {
        Inventory inventory = Bukkit.createInventory(null, size, title);

        if(fillInventory != null){
            fillInventory(inventory);
        }

        setRowAndColumnItems(inventory);
        setItems(inventory);

        reset();
        return inventory;
    }

    /**
     * This method open the inventory to a player
     * @param player The player
     */
    public void open(Player player){
        open(player, this);
    }

    /**
     * This method open the inventory to a player
     * @param player The player
     * @param inventoryBuilder The InventoryBuilder
     */
    private void open(Player player, InventoryBuilder inventoryBuilder){
        player.openInventory(inventoryBuilder.build());
    }

    /**
     * This method fill all inventory with an item
     * @param inventory The inventory
     * @return The inventory filled
     */
    private Inventory fillInventory(Inventory inventory){
        for (int i = 0; i<inventory.getSize(); i++)
            inventory.setItem(i, fillInventory);
        return inventory;
    }

    /**
     * This method fill inventory rows and columns with an item
     * @param inventory The inventory
     * @return The inventory filled
     */
    private Inventory setRowAndColumnItems(Inventory inventory){
        if(fillRow.size() != 0) {
            for (Integer integer : fillRow.keySet()) {
                int max = (integer * 9) - 1;
                int min = max - 8;
                for (int ignored; min <= max; min++) {
                    inventory.setItem(min, fillRow.get(integer));
                }
            }
        }

        if(fillColumn.size() != 0) {
            for (Integer integer : fillColumn.keySet()) {
                for (int i = integer - 1; i < inventory.getSize(); i = i + 9) {
                    System.out.println( i );
                    inventory.setItem(i, fillColumn.get(integer));
                }
            }
        }
        return inventory;
    }

    /**
     * This method set an item into inventory
     * @param inventory The inventory
     * @return The inventory filled
     */
    private Inventory setItems(Inventory inventory){
        if(setItem.size() != 0) {
            for (Integer integer : setItem.keySet()) {
                inventory.setItem(integer, setItem.get(integer));
            }
        }
        return inventory;
    }

    /**
     * This method add a custom value on a placeholder to title of the inventory
     * @param placeholder The placeholder
     * @param value The value
     * @return The InventoryBuilder
     */
    public InventoryBuilder addPlaceHolder(String placeholder, Object value) {
        title = title.replaceAll(placeholder, value.toString());

        return this;
    }

    /**
     * This is a security method used in case an instance is used to make many inventories
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
