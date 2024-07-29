package edu.utsa.cs3443.questlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class QuestActivity extends AppCompatActivity {

    private Button returnButton;
    private TextInputEditText inputText;
    private RadioGroup difficultyOptions;
    private RadioButton easyButton;
    private RadioButton mediumButton;
    private RadioButton hardButton;
    private Button submitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quest);

        returnButton = findViewById(R.id.returnButton);
        inputText = findViewById(R.id.InputText);

        difficultyOptions = findViewById(R.id.DifficultyGroup);
        easyButton = findViewById(R.id.EasyRadio);
        mediumButton = findViewById(R.id.MediumRadio);
        hardButton = findViewById(R.id.HardRadio);

        submitButton = findViewById(R.id.buttonSubmit);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(QuestActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the inputted text of Quest Name. Warning: This can be NULL.
                String textInput = inputText.getText().toString();


                // Selected difficulty will be "Easy" "Medium" or "Hard". Warning: This can be NULL.
                int selectedId = difficultyOptions.getCheckedRadioButtonId();
                RadioButton selectedButton = findViewById(selectedId);
                String radioInput = selectedButton.getText().toString();


                Toast.makeText(QuestActivity.this, textInput + ". " + radioInput + " Difficulty. Submitted!", Toast.LENGTH_SHORT).show();

                // Implement actual code to add the quest here. Toast text can stay to clarify submission.
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}