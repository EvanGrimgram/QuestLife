package edu.utsa.cs3443.questlife;

import android.os.Bundle;

import android.content.Intent;
import android.view.Gravity;
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

import edu.utsa.cs3443.questlife.model.BossHistory;

public class HistoryActivity extends AppCompatActivity {

    private Button returnButton;
    private LinearLayout enemyHistoryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        returnButton = findViewById(R.id.returnButton);
        enemyHistoryLayout = findViewById(R.id.enemyHistoryLayout);

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
        for (BossHistory.DefeatedEnemy enemy : BossHistory.getInstance().getDefeatedEnemies()) {
            addDefeatedEnemy(enemy);
        }
    }

    private void addDefeatedEnemy(BossHistory.DefeatedEnemy enemy) {
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
    }
}
