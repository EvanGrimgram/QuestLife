package edu.utsa.cs3443.questlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
            new Enemy("Demon", R.drawable.demon, "Demon Ring", R.drawable.demonring, 5),
            new Enemy("Bigman", R.drawable.bigman, "Bigman Shoe", R.drawable.bigmanshoe, 5),
            new Enemy("Wizard", R.drawable.wizard, "Wizard Hat", R.drawable.wizardhat, 5),
            new Enemy("Zombie", R.drawable.zombie, "Zombie Tooth", R.drawable.zombietooth, 5),
            new Enemy("Cloudman", R.drawable.cloudman, "Cloudman Raindrop", R.drawable.cloudmanwaterdrop, 5)
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

        displayQuests();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayQuests();
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
            Button completedQuestButton = cardView.findViewById(R.id.completedQuestButton);

            questNameTextView.setText(quest.getUserInput());
            difficultyTextView.setText(quest.getDifficulty()); // Set the difficulty level

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
                damage = 7;
                break;
        }

        // Deals damage to the boss and then performs other functions if it dies
        // Toast displays remaining hp if it doesn't
        currentEnemy.reduceHealth(damage);
        if (currentEnemy.getHealth() <= 0) {
            // Add to boss history
            BossHistory.getInstance().addDefeatedEnemy(currentEnemy.getName(), currentEnemy.getImageResource(), currentEnemy.getHealth() + damage);

            // Add to inventory
            UserInventory.getInstance().addItem(currentEnemy.getItem(), currentEnemy.getItemImageResource());

            // Replace enemy
            currentEnemy = getRandomEnemy();
            Toast.makeText(this, "Enemy defeated! A new enemy appears.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enemy takes " + damage + " damage. Remaining health: " + currentEnemy.getHealth(), Toast.LENGTH_SHORT).show();
        }

        // Remove the completed quest from the list
        UserQuests.getInstance().getQuests().remove(quest);
        displayQuests();
    }

    // Spawns new random enemy out of the enemy arraylist
    private Enemy getRandomEnemy() {
        Random random = new Random();
        return enemies[random.nextInt(enemies.length)];
    }
}