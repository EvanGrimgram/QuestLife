package edu.utsa.cs3443.questlife;

import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import java.util.Random;

import edu.utsa.cs3443.questlife.model.BossHistory;
import edu.utsa.cs3443.questlife.model.Enemy;
import edu.utsa.cs3443.questlife.model.Quest;
import edu.utsa.cs3443.questlife.model.UserInventory;
import edu.utsa.cs3443.questlife.model.UserQuests;

/**
 * The MainActivity class sets up the layout for the main screen of the application.
 * The MainActivity class contains buttons to access the rest of the screens and a list of active quests.
 * The MainActivity class also handles quest completion functions, enemy health, and enemy death functions.
 * The MainActivity class includes a method for refreshing the screen upon being navigated to, ensuring continuation.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class MainActivity extends AppCompatActivity {
    private Button bossHistoryButton;
    private Button inventoryButton;
    private Button questButton;
    private LinearLayout questContainer;
    private Enemy currentEnemy;
    private final Enemy[] enemies = {
            new Enemy("Demon", R.drawable.demon, "Demon Ring", R.drawable.demonring, 10),
            new Enemy("Bigman", R.drawable.bigman, "Bigman Shoe", R.drawable.bigmanshoe, 10),
            new Enemy("Wizard", R.drawable.wizard, "Wizard Hat", R.drawable.wizardhat, 10),
            new Enemy("Zombie", R.drawable.zombie, "Zombie Tooth", R.drawable.zombietooth, 10),
            new Enemy("Cloudman", R.drawable.cloudman, "Cloudman Raindrop", R.drawable.cloudmanwaterdrop, 10)
    };

    /**
     * Called when the activity is first created. This is where you should do all of your
     * normal static set up: create views, bind data to lists, etc.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons and container
        bossHistoryButton = findViewById(R.id.bosshistory);
        // Sets up the inventory button
        inventoryButton = findViewById(R.id.inventory);
        // Sets up the quest creation button
        questButton = findViewById(R.id.quest);
        questContainer = findViewById(R.id.questContainer);

        // Restore the last enemy state or initialize a new one
        restoreEnemyState();
        updateEnemyUI();
        displayQuests();

        // Set up button listeners for navigation
        bossHistoryButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
        inventoryButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, InventoryActivity.class)));
        questButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, QuestActivity.class)));

        // Handle window insets for immersive experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        // If no current enemy, spawn a new random one and save its state
        if (currentEnemy == null) {
            currentEnemy = getRandomEnemy();
            saveEnemyState();
            updateEnemyUI();
        }
    }

    /**
     * Called when the activity becomes visible to the user. Ensures that the UI is updated and the enemy state is restored.
     */
    @Override
    protected void onResume() {
        super.onResume();
        restoreEnemyState(); // Ensure enemy state is correctly restored
        updateEnemyUI(); // Update UI based on restored state
        displayQuests();
    }

    /**
     * Saves the current enemy's state to SharedPreferences.
     * This includes enemy name, health, image resources, and item data.
     */
    private void saveEnemyState() {
        if (currentEnemy != null) {
            SharedPreferences prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("enemy_name", currentEnemy.getName());
            editor.putInt("enemy_health", currentEnemy.getHealth());
            editor.putInt("enemy_original_health", currentEnemy.getOriginalHealth());
            editor.putInt("enemy_image_resource", currentEnemy.getImageResource());
            editor.putInt("enemy_item_image", currentEnemy.getItemImageResource());
            editor.putString("enemy_item", currentEnemy.getItem());
            editor.apply(); // Apply changes to save the state
        }
    }

    /**
     * Restores the enemy state from SharedPreferences. If no state is found, initializes a new enemy.
     */
    private void restoreEnemyState() {
        SharedPreferences prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        String name = prefs.getString("enemy_name", null);
        String item = prefs.getString("enemy_item", null);
        int health = prefs.getInt("enemy_health", 0);
        int originalHealth = prefs.getInt("enemy_original_health", 0);
        int imageResource = prefs.getInt("enemy_image_resource", R.drawable.cloudman);
        int itemImage = prefs.getInt("enemy_item_image", R.drawable.cloudmanwaterdrop);

        if (name != null && originalHealth > 0) {
            // Restore the enemy's state if found
            currentEnemy = new Enemy(name, imageResource, item, itemImage, originalHealth);
            currentEnemy.setHealth(health);
        } else {
            // Initialize a new random enemy if no previous state is found
            currentEnemy = getRandomEnemy();
            saveEnemyState();
        }
    }

    /**
     * Updates the UI components to reflect the current enemy's state.
     * This includes setting the enemy's image, health bar, and health text.
     */
    private void updateEnemyUI() {
        TextView healthTextView = findViewById(R.id.healthTextView);
        ProgressBar healthProgressBar = findViewById(R.id.healthProgressBar);
        ImageView enemyImageView = findViewById(R.id.enemyImageView);

        if (currentEnemy != null) {
            healthTextView.setText("Health: " + currentEnemy.getHealth());

            // Calculate new progress
            int newProgress = (int) (((float) currentEnemy.getHealth() / currentEnemy.getOriginalHealth()) * 100);

            // Animate the progress bar
            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(healthProgressBar, "progress", healthProgressBar.getProgress(), newProgress);
            progressAnimator.setDuration(1000);
            progressAnimator.setInterpolator(new DecelerateInterpolator());
            progressAnimator.start();

            // Update the image resource for the enemy
            enemyImageView.setImageResource(currentEnemy.getImageResource());
        }
    }

    /**
     * Displays the list of quests available for the user.
     * This method iterates over the user's quests and inflates views to display each quest.
     */
    private void displayQuests() {
        questContainer.removeAllViews();

        List<Quest> quests = UserQuests.getInstance().getQuests();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Quest quest : quests) {
            View cardView = inflater.inflate(R.layout.quest_card, questContainer, false);

            TextView questNameTextView = cardView.findViewById(R.id.textCurrentQuest);
            TextView difficultyTextView = cardView.findViewById(R.id.textDifficulty);
            ImageView difficultyImageView = cardView.findViewById(R.id.difficultyImageView);

            Button completedQuestButton = cardView.findViewById(R.id.completedQuestButton);

            questNameTextView.setText(quest.getUserInput());
            difficultyTextView.setText(quest.getDifficulty());
            // Set difficulty icon based on the quest's difficulty level
            switch (quest.getDifficulty().toLowerCase()) {
                case "easy":
                    difficultyImageView.setImageResource(R.drawable.easy);
                    break;
                case "medium":
                    difficultyImageView.setImageResource(R.drawable.medium);
                    break;
                case "hard":
                    difficultyImageView.setImageResource(R.drawable.hard);
                    break;
            }

            // Set a click listener for completing quests
            completedQuestButton.setOnClickListener(v -> {
                MainActivity.this.completeQuest(quest);
                UserQuests.getInstance().getQuests().remove(quest);
                MainActivity.this.displayQuests();
            });
            questContainer.addView(cardView);
        }
    }

    /**
     * Handles the completion of a quest, dealing damage to the current enemy based on the quest's difficulty.
     * @param quest The quest that has been completed.
     */
    private void completeQuest(Quest quest) {
        int damage = 0;
        // Determine damage based on quest difficulty
        switch (quest.getDifficulty().toLowerCase()) {
            case "easy":
                damage = 3;
                break;
            case "medium":
                damage = 5;
                break;
            case "hard":
                damage = 10;
                break;
        }

        // Apply damage to the current enemy and update UI
        currentEnemy.reduceHealth(damage);
        updateEnemyUI();

        if (currentEnemy.getHealth() <= 0) {
            // Add DefeatedEnemy to boss history
            BossHistory.getInstance().addDefeatedEnemy(currentEnemy.getName(), currentEnemy.getImageResource(),currentEnemy.getHealth() + damage, currentEnemy.getOriginalHealth());

            // Add Item to inventory
            UserInventory.getInstance().addItem(currentEnemy.getItem(), currentEnemy.getItemImageResource());

            // Replace enemy
            currentEnemy = getRandomEnemy();
            saveEnemyState();
            updateEnemyUI();
            Toast.makeText(this, "Enemy defeated! A new enemy appears.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enemy takes " + damage + " damage. Remaining health: " + currentEnemy.getHealth(), Toast.LENGTH_SHORT).show();
        }
        saveEnemyState();
        displayQuests();
    }

    /**
     * Randomly selects and returns an enemy from the predefined list of enemies.
     * @return A randomly selected Enemy object.
     */
    private Enemy getRandomEnemy() {
        Random random = new Random();
        return enemies[random.nextInt(enemies.length)];
    }
}