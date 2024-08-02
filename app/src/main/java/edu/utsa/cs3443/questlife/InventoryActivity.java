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
import edu.utsa.cs3443.questlife.model.UserInventory;

public class InventoryActivity extends AppCompatActivity {

    private Button returnButton;
    private LinearLayout inventoryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory);

        returnButton = findViewById(R.id.returnButton);
        inventoryLayout = findViewById(R.id.inventoryLayout);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        displayInventory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayInventory();
    }

    private void displayInventory() {
        inventoryLayout.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(this);
        List<UserInventory.Item> item = UserInventory.getInstance().getItems();

        for (UserInventory.Item items : item) {
            View cardView = inflater.inflate(R.layout.inventory_item_card, inventoryLayout, false);

            ImageView lootImageView = cardView.findViewById(R.id.LootImageView);
            TextView textLootName = cardView.findViewById(R.id.textLootName);

            lootImageView.setImageResource(items.getImageResource());
            textLootName.setText(String.valueOf(items.getName()));

            inventoryLayout.addView(cardView);
        }
    }

    /*private void addItemToInventory(UserInventory.Item item) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView itemImageView = new ImageView(this);
        itemImageView.setImageResource(item.getImageResource());
        itemImageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

        TextView itemTextView = new TextView(this);
        itemTextView.setText("Item: " + item.getName());
        itemTextView.setGravity(Gravity.CENTER);
        itemTextView.setTextSize(25);
        itemTextView.setPadding(10, 0, 0, 0);

        itemLayout.addView(itemImageView);
        itemLayout.addView(itemTextView);

        inventoryLayout.addView(itemLayout);
    }*/
}
