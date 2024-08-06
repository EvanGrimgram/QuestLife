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


    // Sets up the Boss History screen layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory);

        inventoryLayout = findViewById(R.id.inventoryLayout);

        // Sets up the button for returning to the main screen
        returnButton = findViewById(R.id.returnButton);
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

        // Calling the displayInventory method to list out the Item objects
        displayInventory();
    }


    // Refreshes the Inventory list when navigating back to this screen from the main screen
    @Override
    protected void onResume() {
        super.onResume();
        displayInventory();
    }


    // Gets the Item information from the items ArrayList in UserInventory and then
    // populates the respective fields of the layout by making use of the inventory_item_card
    private void displayInventory() {
        inventoryLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        List<UserInventory.Item> item = UserInventory.getInstance().getItems();

        for (UserInventory.Item items : item) {
            View cardView = inflater.inflate(R.layout.inventory_item_card, inventoryLayout, false);

            ImageView lootImageView = cardView.findViewById(R.id.LootImageView);
            TextView textLootName = cardView.findViewById(R.id.textLootName);

            lootImageView.setImageResource(items.getImageResource());
            textLootName.setText(items.getName());

            inventoryLayout.addView(cardView);
        }
    }
}
