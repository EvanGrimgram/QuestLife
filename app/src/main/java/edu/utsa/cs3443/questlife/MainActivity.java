package edu.utsa.cs3443.questlife;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bossHistoryButton = findViewById(R.id.bosshistory);
        inventoryButton = findViewById(R.id.inventory);
        questButton = findViewById(R.id.quest);
        questContainer = findViewById(R.id.questContainer);

        restoreEnemyState();
        updateEnemyUI();
        displayQuests();


        bossHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        questButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
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

    @Override
    protected void onResume() {
        super.onResume();
        displayQuests();
    }

    // Saves enemy health state until defeated
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

    // Restores the health state of the enemy
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
            progressAnimator.setDuration(1000); // Animation duration in milliseconds
            progressAnimator.setInterpolator(new DecelerateInterpolator()); // Optional: smooth easing
            progressAnimator.start();

            // Update the image resource for the enemy
            enemyImageView.setImageResource(currentEnemy.getImageResource());
        }
    }

    // Displays the created quests
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
        // Toast displays remaining hp if it doesn't
        currentEnemy.reduceHealth(damage);
        updateEnemyUI();


        if (currentEnemy.getHealth() <= 0) {
            // Add to boss history
            BossHistory.getInstance().addDefeatedEnemy(currentEnemy.getName(), currentEnemy.getImageResource(),currentEnemy.getHealth() + damage, currentEnemy.getOriginalHealth());

            // Add to inventory
            UserInventory.getInstance().addItem(currentEnemy.getItem(), currentEnemy.getItemImageResource());

            // Replace enemy
            currentEnemy = getRandomEnemy();
            updateEnemyUI();
            Toast.makeText(this, "Enemy defeated! A new enemy appears.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enemy takes " + damage + " damage. Remaining health: " + currentEnemy.getHealth(), Toast.LENGTH_SHORT).show();
        }

        // Remove the completed quest from the list
        /*UserQuests.getInstance().getQuests().remove(quest);*/
        saveEnemyState();
        displayQuests();
    }

    // Spawns new random enemy out of the enemy arraylist
    private Enemy getRandomEnemy() {
        Random random = new Random();
        return enemies[random.nextInt(enemies.length)];
    }

}
