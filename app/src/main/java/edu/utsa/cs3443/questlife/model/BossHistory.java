package edu.utsa.cs3443.questlife.model;

import java.util.ArrayList;
import java.util.List;

// Bosses on boss history screen
public class BossHistory {
    private static BossHistory instance = null;
    private List<DefeatedEnemy> defeatedEnemies;

    private BossHistory() {
        defeatedEnemies = new ArrayList<>();
    }

    public static BossHistory getInstance() {
        if (instance == null) {
            instance = new BossHistory();
        }
        return instance;
    }

    public void addDefeatedEnemy(String name, int imageResource, int health) {
        defeatedEnemies.add(new DefeatedEnemy(name, imageResource, health));
    }

    public List<DefeatedEnemy> getDefeatedEnemies() {
        return defeatedEnemies;
    }

    public static class DefeatedEnemy {
        private String name;
        private int imageResource;
        private int health;

        public DefeatedEnemy(String name, int imageResource, int health) {
            this.name = name;
            this.imageResource = imageResource;
            this.health = health;
        }

        public String getName() {
            return name;
        }

        public int getImageResource() {
            return imageResource;
        }

        public int getHealth() {
            return health;
        }
    }
}