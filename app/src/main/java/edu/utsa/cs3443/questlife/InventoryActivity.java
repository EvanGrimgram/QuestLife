package edu.utsa.cs3443.questlife;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import android.content.SharedPreferences;
import android.widget.TextView;

import edu.utsa.cs3443.questlife.model.Enemy;
import edu.utsa.cs3443.questlife.model.UserInventory;

/**
 * The InventoryActivity class sets up the layout for the Inventory screen in the application.
 * The displayInventory() method uses the items ArrayList in UserInventory which is
 * iterated through and then used to populate the layout by making use of the inventory_item_card.
 * The InventoryActivity class includes a method for refreshing the screen upon being navigated to, ensuring continuation.
 *
 * @author JavaJuicers
 * UTSA CS 3443 - Final Application
 *
 */
public class InventoryActivity extends AppCompatActivity {
    private Button returnButton;
    private LinearLayout inventoryLayout;
    private ImageView enemyImageView;
    private Enemy currentEnemy;

    /**
     * Initializes the activity, sets up the layout and retrieves current enemy data.
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        inventoryLayout = findViewById(R.id.inventoryLayout);

        // Set up the image view for the currently active enemy
        enemyImageView = findViewById(R.id.enemyImageView);
        currentEnemy = getCurrentEnemy();

        // Set up the button for returning to the main screen
        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(InventoryActivity.this, MainActivity.class);
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

        // Display the inventory items
        displayInventory();
    }

    /**
     * Refreshes the inventory list when navigating back to this screen from another activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        displayInventory();
    }

    /**
     * Displays the items in the user's inventory by populating the layout.
     * This method iterates through the items in UserInventory and creates views for each item.
     */
    private void displayInventory() {
        inventoryLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        List<UserInventory.Item> items = UserInventory.getInstance().getItems();

        for (UserInventory.Item item : items) {
            View cardView = inflater.inflate(R.layout.inventory_item_card, inventoryLayout, false);

            ImageView lootImageView = cardView.findViewById(R.id.LootImageView);
            TextView textLootName = cardView.findViewById(R.id.textLootName);

            lootImageView.setImageResource(item.getImageResource());
            textLootName.setText(item.getName());

            inventoryLayout.addView(cardView);
        }
    }

    /**
     * Retrieves the current enemy state from SharedPreferences.
     * This method is used to display the current enemy's image on the inventory screen.
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