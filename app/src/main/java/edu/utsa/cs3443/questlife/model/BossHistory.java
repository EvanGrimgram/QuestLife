package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The BossHistory class keeps a record of all defeated enemies.
 * The BossHistory class contains an ArrayList of DefeatedEnemy objects.
 * The nested static class DefeatedEnemy is used to create DefeatedEnemy objects.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class BossHistory {
    private static BossHistory instance = null;
    private List<DefeatedEnemy> defeatedEnemies;

    /**
     * Private constructor which initializes the defeatedEnemies ArrayList
     */
    private BossHistory() {
        defeatedEnemies = new ArrayList<>();
    }

    /**
     * Provides access to the BossHistory class instance
     * If the instance is null, it creates a new one
     * @return the BossHistory instance
     */
    public static BossHistory getInstance() {
        if (instance == null) {
            instance = new BossHistory();
        }
        return instance;
    }

    /**
     * Adds a new DefeatedEnemy object to the list.
     * This is used to display the past bosses on the Boss History screen.
     *
     * @param name name of the new DefeatedEnemy
     * @param imageResource image for the new DefeatedEnemy
     * @param health remaining health for the new DefeatedEnemy
     * @param originalHealth original health of the DefeatedEnemy
     */
    public void addDefeatedEnemy(String name, int imageResource, int health, int originalHealth) {
        defeatedEnemies.add(new DefeatedEnemy(name, imageResource, health, originalHealth));
    }

    /**
     * Returns the List of DefeatedEnemy objects
     * @return the List of DefeatedEnemy objects
     */
    public List<DefeatedEnemy> getDefeatedEnemies() {
        return defeatedEnemies;
    }

    /**
     * The nested static class DefeatedEnemy which is used to fill
     * the defeatedEnemies ArrayList in BossHistory.
     * A DefeatedEnemy object contains the enemies name, image, health, and original health.
     */
    public static class DefeatedEnemy {
        private String name;
        private int imageResource;
        private int health;
        private int originalHealth;

        /**
         * Constructs a DefeatedEnemy object with the specified name, image, health, and original health.
         *
         * @param name name of the new DefeatedEnemy
         * @param imageResource image for the new DefeatedEnemy
         * @param health remaining health for the new DefeatedEnemy
         * @param originalHealth original health of the DefeatedEnemy
         */
        public DefeatedEnemy(String name, int imageResource, int health, int originalHealth) {
            this.name = name;
            this.imageResource = imageResource;
            this.health = health;
            this.originalHealth = originalHealth;
        }

        /**
         * Returns the name of the DefeatedEnemy
         * @return the name of the DefeatedEnemy
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the DefeatedEnemy
         * @param name the name of the DefeatedEnemy
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Returns the image of the DefeatedEnemy
         * @return the image of the DefeatedEnemy
         */
        public int getImageResource() {
            return imageResource;
        }

        /**
         * Sets the image of the DefeatedEnemy
         * @param imageResource the image of the DefeatedEnemy
         */
        public void setImageResource(int imageResource) {
            this.imageResource = imageResource;
        }

        /**
         * Returns the health of the DefeatedEnemy
         * @return the health of the DefeatedEnemy
         */
        public int getHealth() {
            return health;
        }

        /**
         * Sets the health of the DefeatedEnemy
         * @param health the health of the DefeatedEnemy
         */
        public void setHealth(int health) {
            this.health = health;
        }

        /**
         * Returns the original health of the DefeatedEnemy
         * @return the original health of the DefeatedEnemy
         */
        public int getOriginalHealth() {
            return originalHealth;
        }

        /**
         * Sets the original health of the DefeatedEnemy
         * @param originalHealth the original health of the DefeatedEnemy
         */
        public void setOriginalHealth(int originalHealth) {
            this.originalHealth = originalHealth;
        }
    }
}