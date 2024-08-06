package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserInventory class keeps a record of all items in the users inventory from defeated enemies.
 * The UserInventory class contains an ArrayList of Item objects.
 * The nested static class Item is used to create Item objects.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class UserInventory {
    private static UserInventory instance = null;
    private List<Item> items;

    /**
     * Private constructor which initializes the UserInventory ArrayList
     */
    private UserInventory() {
        items = new ArrayList<>();
    }

    /**
     * Provides access to the UserInventory class instance
     * If the instance is null, it creates a new one
     * @return the UserInventory instance
     */
    public static UserInventory getInstance() {
        if (instance == null) {
            instance = new UserInventory();
        }
        return instance;
    }

    /**
     * Adds a new Item object to the list.
     * This is used to display the items in the inventory on the Inventory screen.
     *
     * @param itemName the name of the item
     * @param itemImageResource the image associated of the item
     */
    public void addItem(String itemName, int itemImageResource) {
        items.add(new Item(itemName, itemImageResource));
    }

    /**
     * Returns the ArrayList of Item objects
     * @return the ArrayList of Item objects
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * The nested static class Item which is used to fill the items ArrayList in UserInventory.
     * An Item object contains the item's name and image.
     */
    public static class Item {
        private String name;
        private int imageResource;

        /**
         * Constructs an Item object with the specified name and image.
         * @param name the name of the Item
         * @param imageResource the image associated with the Item
         */
        public Item(String name, int imageResource) {
            this.name = name;
            this.imageResource = imageResource;
        }

        /**
         * Returns the name of the Item
         * @return the name of the Item
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the Item
         * @param name the name of the Item
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Returns the image associated with the Item
         * @return the image associated with the Item
         */
        public int getImageResource() {
            return imageResource;
        }

        /**
         * Sets the image associated with the Item
         * @param imageResource the image associated with the Item
         */
        public void setImageResource(int imageResource) {
            this.imageResource = imageResource;
        }
    }
}
