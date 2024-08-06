package edu.utsa.cs3443.questlife;

import android.os.Bundle;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

import edu.utsa.cs3443.questlife.model.BossHistory;

/**
 * The HistoryActivity class sets up the layout for the Boss History screen in the application.
 * The displayBossHistory() method uses the defeatedEnemies ArrayList in BossHistory which is
 * iterated through and then used to populate the layout by making use of the defeated_enemy_card.
 * The HistoryActivity class includes a method for refreshing the screen upon being navigated to, ensuring continuation.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class HistoryActivity extends AppCompatActivity {
    private Button returnButton;
    private LinearLayout enemyHistoryLayout;


    // Sets up the Boss History screen layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        enemyHistoryLayout = findViewById(R.id.enemyContainer);

        // Sets up the button for returning to the main screen
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Calling the displayBossHistory method to list out the DefeatedEnemy objects
        displayBossHistory();
    }


    // Refreshes the Boss History list when navigating back to this screen from the main screen
    @Override
    protected void onResume() {
        super.onResume();
        displayBossHistory();
    }


    // Gets the Enemy information from the defeatedEnemies ArrayList in BossHistory and then
    // populates the respective fields of the layout by making use of the defeated_enemy_card
    private void displayBossHistory() {
        enemyHistoryLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        List<BossHistory.DefeatedEnemy> defeatedEnemies = BossHistory.getInstance().getDefeatedEnemies();

        for (BossHistory.DefeatedEnemy enemy : defeatedEnemies) {
            View cardView = inflater.inflate(R.layout.defeated_enemy_card, enemyHistoryLayout, false);

            ImageView enemyImageView = cardView.findViewById(R.id.enemyImageView);
            TextView enemyNameTextView = cardView.findViewById(R.id.textEnemy);
            TextView enemyHealthTextView = cardView.findViewById(R.id.textEnemyOriginalHealth);

            enemyImageView.setImageResource(enemy.getImageResource());
            enemyNameTextView.setText(enemy.getName());
            enemyHealthTextView.setText(String.valueOf(enemy.getOriginalHealth()));

            enemyHistoryLayout.addView(cardView);
        }
    }
}
