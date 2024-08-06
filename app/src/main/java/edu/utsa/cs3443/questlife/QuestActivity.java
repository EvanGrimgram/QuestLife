package edu.utsa.cs3443.questlife;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
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

    /**
     * Initializes the activity, sets up the layout and retrieves the current enemy data.
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        // Initialize UI components for quest creation
        inputText = findViewById(R.id.InputText);

        // Sets up the difficulty selection buttons
        difficultyOptions = findViewById(R.id.DifficultyGroup);
        easyButton = findViewById(R.id.EasyRadio);
        mediumButton = findViewById(R.id.MediumRadio);
        hardButton = findViewById(R.id.HardRadio);

        // Sets up the button for finalizing the created quest
        submitButton = findViewById(R.id.buttonSubmit);

        // Set up the image view for the currently active enemy
        enemyImageView = findViewById(R.id.enemyImageView);
        currentEnemy = getCurrentEnemy();

        // Set up the button for returning to the main screen
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuestActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Logic for finalizing the creation of a new quest upon clicking the submit button
        submitButton.setOnClickListener(v -> {
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
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        // Display the current enemy's image if available
        if (currentEnemy != null) {
            enemyImageView.setImageResource(currentEnemy.getImageResource());
        }
    }

    /**
     * Retrieves the current enemy state from SharedPreferences.
     * This method is used to display the current enemy's image on the quest creation screen.
     * @return The current Enemy object, or null if no enemy data is found.
     */
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