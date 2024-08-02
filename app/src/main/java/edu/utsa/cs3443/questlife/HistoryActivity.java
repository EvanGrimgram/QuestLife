package edu.utsa.cs3443.questlife;

import android.os.Bundle;

import android.content.Intent;
import android.view.Gravity;
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
import edu.utsa.cs3443.questlife.model.Quest;
import edu.utsa.cs3443.questlife.model.UserQuests;

public class HistoryActivity extends AppCompatActivity {

    private Button returnButton;
    private LinearLayout enemyHistoryLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        returnButton = findViewById(R.id.returnButton);
        enemyHistoryLayout = findViewById(R.id.enemyContainer);

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
        displayBossHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayBossHistory();
    }

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



    /*private void addDefeatedEnemy(BossHistory.DefeatedEnemy enemy) {
        LinearLayout enemyLayout = new LinearLayout(this);
        enemyLayout.setOrientation(LinearLayout.HORIZONTAL);
        enemyLayout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView enemyImageView = new ImageView(this);
        enemyImageView.setImageResource(enemy.getImageResource());
        enemyImageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

        TextView enemyTextView = new TextView(this);
        enemyTextView.setText(enemy.getName() + " (Health: " + enemy.getOriginalHealth() + ")");
        enemyTextView.setGravity(Gravity.CENTER);
        enemyTextView.setTextSize(25);
        enemyTextView.setPadding(10, 0, 0, 0);

        enemyLayout.addView(enemyImageView);
        enemyLayout.addView(enemyTextView);

        enemyHistoryLayout.addView(enemyLayout);
    }*/
}
