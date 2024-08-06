package edu.utsa.cs3443.questlife.model;

/**
 * The Enemy class creates the Enemy objects which take damage
 * upon quest completion and then are added to boss history and drop an item on defeat.
 * An Enemy object contains a name, enemy image, item, item image, health, and original health.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class Enemy {
    private String name;
    private int imageResource;
    private String item;
    private int itemImageResource;
    private int health;
    private int originalHealth;

    /**
     * Constructs an Enemy object with the specified name, image, item, item image, and health.
     * The health parameter is used for both health and originalHealth
     *
     * @param name the name of the Enemy
     * @param imageResource the image associated with the Enemy
     * @param item the item associated with the Enemy
     * @param itemImageResource the item image associated with the Enemy
     * @param health the health of the Enemy
     */
    public Enemy(String name, int imageResource, String item, int itemImageResource, int health) {
        this.name = name;
        this.imageResource = imageResource;
        this.item = item;
        this.itemImageResource = itemImageResource;
        this.health = health;
        this.originalHealth = health;
    }

    /**
     * Returns the name of the Enemy
     * @return the name of the Enemy
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Enemy
     * @param name the name of the Enemy
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the image associated with the Enemy
     * @return the image associated with the Enemy
     */
    public int getImageResource() {
        return imageResource;
    }

    /**
     * Sets the image associated with the Enemy
     * @param imageResource the image associated with the Enemy
     */
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    /**
     * Returns the item associated with the Enemy
     * @return the item associated with the Enemy
     */
    public String getItem() {
        return item;
    }

    /**
     * Sets the item associated with the Enemy
     * @param item the item associated with the Enemy
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Returns the item image associated with the Enemy
     * @return the item image associated with the Enemy
     */
    public int getItemImageResource() {
        return itemImageResource;
    }

    /**
     * Sets the item image associated with the Enemy
     * @param itemImageResource the item image associated with the Enemy
     */
    public void setItemImageResource(int itemImageResource) {
        this.itemImageResource = itemImageResource;
    }

    /**
     * Returns the health of the Enemy
     * @return the health of the Enemy
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the Enemy
     * @param health the health of the Enemy
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns the original health of the Enemy
     * @return the original health of the Enemy
     */
    public int getOriginalHealth(){
        return originalHealth;
    }

    /**
     * Sets the original health of the Enemy
     * @param originalHealth the original health of the Enemy
     */
    public void setOriginalHealth(int originalHealth) {
        this.originalHealth = originalHealth;
    }

    /**
     * Called upon quest completion to reduce the Enemy health
     * @param amount amount to reduce the Enemy health by
     */
    public void reduceHealth(int amount) {
        this.health -= amount;
    }
}
