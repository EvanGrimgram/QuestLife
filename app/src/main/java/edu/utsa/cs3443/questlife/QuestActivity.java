package edu.utsa.cs3443.questlife;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.textfield.TextInputEditText;

import edu.utsa.cs3443.questlife.model.Enemy;
import edu.utsa.cs3443.questlife.model.Quest;
import edu.utsa.cs3443.questlife.model.UserQuests;

/**
 * The QuestActivity class sets up the layout for quest creation on the create quest screen of the application.
 * The user creates a quest through a text input field for the name, a button selection for the difficulty,
 * and a submit button to finalize the quest creation.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class QuestActivity extends AppCompatActivity {
    private Button returnButton;
    private TextInputEditText inputText;
    private RadioGroup difficultyOptions;
    private RadioButton easyButton;
    private RadioButton mediumButton;
    private RadioButton hardButton;
    private Button submitButton;
    private ImageView enemyImageView;
    private Enemy currentEnemy;


    // Sets up the quest creation screen layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quest);

        // Sets up the text input field for the quest name
        inputText = findViewById(R.id.InputText);

        // Sets up the difficulty selection buttons
        difficultyOptions = findViewById(R.id.DifficultyGroup);
        easyButton = findViewById(R.id.EasyRadio);
        mediumButton = findViewById(R.id.MediumRadio);
        hardButton = findViewById(R.id.HardRadio);

        // Sets up the button for finalizing the created quest
        submitButton = findViewById(R.id.buttonSubmit);

        // Setting up the image for the currently active enemy
        enemyImageView = findViewById(R.id.enemyImageView);
        currentEnemy = getCurrentEnemy();

        // Sets up the button for returning to the main screen
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // Logic for finalizing the creation of a new quest upon clicking the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textInput;
                String radioInput = null;

                textInput = inputText.getText().toString();
                if (textInput.isEmpty()) {
                    textInput = "NULL";
                }

                int selectedId = difficultyOptions.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedButton = findViewById(selectedId);
                    if (selectedButton != null) {
                        radioInput = selectedButton.getText().toString();
                    }
                }

                if (radioInput == null) {
                    Toast.makeText(QuestActivity.this, "Please select a difficulty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Quest newQuest = new Quest(textInput, radioInput);
                UserQuests.getInstance().addQuest(newQuest);

                Toast.makeText(QuestActivity.this, textInput + ". " + radioInput + " Difficulty. Submitted!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(QuestActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Sets the enemy image if the current enemy is not null
        if (currentEnemy != null) {
            enemyImageView.setImageResource(currentEnemy.getImageResource());
        }
    }


    // Retrieve the current enemy state from SharedPreferences to use the image
    private Enemy getCurrentEnemy() {
        SharedPreferences prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        String name = prefs.getString("enemy_name", null);
        String item = prefs.getString("enemy_item", null);
        int health = prefs.getInt("enemy_health", 0);
        int originalHealth = prefs.getInt("enemy_original_health", 0);
        int imageResource = prefs.getInt("enemy_image_resource", R.drawable.cloudman);
        int itemImage = prefs.getInt("enemy_item_image", R.drawable.cloudmanwaterdrop);

        if (name != null && originalHealth > 0) {
            Enemy enemy = new Enemy(name, imageResource, item, itemImage, originalHealth);
            enemy.setHealth(health);
            return enemy;
        } else {
            return null;
        }
    }
}