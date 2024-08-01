package edu.utsa.cs3443.questlife.model;

public class Enemy {
    private String name;
    private int imageResource;
    private String item;
    private int itemImageResource;
    private int health;

    public Enemy(String name, int imageResource, String item, int itemImageResource, int health) {
        this.name = name;
        this.imageResource = imageResource;
        this.item = item;
        this.itemImageResource = itemImageResource;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getItem() {
        return item;
    }

    public int getItemImageResource() {
        return itemImageResource;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void reduceHealth(int amount) {
        this.health -= amount;
    }
}
