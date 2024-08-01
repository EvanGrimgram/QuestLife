package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;
import java.util.List;

// Items on inventory screen
public class UserInventory {
    private static UserInventory instance = null;
    private List<Item> items;

    private UserInventory() {
        items = new ArrayList<>();
    }

    public static UserInventory getInstance() {
        if (instance == null) {
            instance = new UserInventory();
        }
        return instance;
    }

    public void addItem(String itemName, int itemImageResource) {
        items.add(new Item(itemName, itemImageResource));
    }

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        private String name;
        private int imageResource;

        public Item(String name, int imageResource) {
            this.name = name;
            this.imageResource = imageResource;
        }

        public String getName() {
            return name;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
