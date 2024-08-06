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
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
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


    // Sets up the main screen layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Sets up the boss history button
        bossHistoryButton = findViewById(R.id.bosshistory);
        // Sets up the inventory button
        inventoryButton = findViewById(R.id.inventory);
        // Sets up the quest creation button
        questButton = findViewById(R.id.quest);
        questContainer = findViewById(R.id.questContainer);

        // Calling other methods to set up the screen
        restoreEnemyState();
        updateEnemyUI();
        displayQuests();

        bossHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        questButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Spawn the initial enemy
        if (currentEnemy == null) {
            currentEnemy = getRandomEnemy();
        }
    }


    // Refreshes the active quests list when navigating back to this screen from the main screen
    @Override
    protected void onResume() {
        super.onResume();
        displayQuests();
    }


    // Saves all of the enemies information state to SharedPreferences
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
            editor.apply();
        }
    }


    // Restores the state of the enemy from SharedPreferences
    // If no previous state is found, spawns a new random enemy
    private void restoreEnemyState() {
        SharedPreferences prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        String name = prefs.getString("enemy_name", null);
        String item = prefs.getString("enemy_item", null);
        int health = prefs.getInt("enemy_health", 0);
        int originalHealth = prefs.getInt("enemy_original_health", 0);
        int imageResource = prefs.getInt("enemy_image_resource", R.drawable.cloudman);
        int itemImage = prefs.getInt("enemy_item_image", R.drawable.cloudmanwaterdrop);

        if (name != null && originalHealth > 0) {
            currentEnemy = new Enemy(name, imageResource, item, itemImage, originalHealth);
            currentEnemy.setHealth(health);
        } else {
            if (currentEnemy == null) {
                currentEnemy = getRandomEnemy();
            }
        }
    }


    // Updates the Enemy's health text, health bar, and their image
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


    // Displays the active quests
    // Gets the Quest information from the quests ArrayList in UserQuests and then
    // populates the respective fields of the layout by making use of quest_card
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

            // Using the respective quest difficulty icons
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

            completedQuestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.completeQuest(quest);
                    UserQuests.getInstance().getQuests().remove(quest);
                    MainActivity.this.displayQuests();
                }
            });
            questContainer.addView(cardView);
        }
    }


    // Deals damage upon pressing quest complete based on difficulty
    private void completeQuest(Quest quest) {
        int damage = 0;
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


        // Deals damage to the boss and then performs other functions if it dies
        // Toast displays enemy defeated if it dies or the remaining hp if it doesn't die
        currentEnemy.reduceHealth(damage);
        updateEnemyUI();

        if (currentEnemy.getHealth() <= 0) {
            // Add DefeatedEnemy to boss history
            BossHistory.getInstance().addDefeatedEnemy(currentEnemy.getName(), currentEnemy.getImageResource(),currentEnemy.getHealth() + damage, currentEnemy.getOriginalHealth());

            // Add Item to inventory
            UserInventory.getInstance().addItem(currentEnemy.getItem(), currentEnemy.getItemImageResource());

            // Replace enemy
            currentEnemy = getRandomEnemy();
            updateEnemyUI();
            Toast.makeText(this, "Enemy defeated! A new enemy appears.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enemy takes " + damage + " damage. Remaining health: " + currentEnemy.getHealth(), Toast.LENGTH_SHORT).show();
        }
        saveEnemyState();
        displayQuests();
    }


    // Spawns new random enemy out of the enemy arraylist
    private Enemy getRandomEnemy() {
        Random random = new Random();
        return enemies[random.nextInt(enemies.length)];
    }
}
