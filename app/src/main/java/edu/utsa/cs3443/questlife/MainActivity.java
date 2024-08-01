package edu.utsa.cs3443.questlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import edu.utsa.cs3443.questlife.model.Quest;
import edu.utsa.cs3443.questlife.model.UserQuests;

public class MainActivity extends AppCompatActivity {

    private Button bossHistoryButton;
    private Button inventoryButton;
    private Button questButton;
    private LinearLayout questContainer;


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
            // Inflate the card view layout
            View cardView = inflater.inflate(R.layout.quest_card, questContainer, false);

            // Find the TextView within the card layout and set the quest details
            TextView questNameTextView = cardView.findViewById(R.id.textCurrentQuest);
            TextView difficultyTextView = cardView.findViewById(R.id.textDifficulty);

            questNameTextView.setText(quest.getUserInput());
            difficultyTextView.setText(quest.getDifficulty()); // Set the difficulty level


            // Add the card view to the LinearLayout
            questContainer.addView(cardView);
        }
    }

}