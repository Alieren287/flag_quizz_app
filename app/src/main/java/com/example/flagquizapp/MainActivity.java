package com.example.flagquizapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView flagImageView;
    private Button option1Button, option2Button, option3Button, option4Button;
    private TextView resultTextView;

    private List<Flag> remainingFlags;
    private List<Flag> allFlags;
    private Flag currentFlag;
    private int correctAnswers = 0;
    private int totalAsked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        flagImageView = findViewById(R.id.flagImageView);
        option1Button = findViewById(R.id.button1);
        option2Button = findViewById(R.id.button2);
        option3Button = findViewById(R.id.button3);
        option4Button = findViewById(R.id.button4);
        resultTextView = findViewById(R.id.scoreTextView);

        // Load flags
        allFlags = loadFlags();
        startNewGame();

        // Set button click listeners
        option1Button.setOnClickListener(v -> checkAnswer(option1Button.getText().toString()));
        option2Button.setOnClickListener(v -> checkAnswer(option2Button.getText().toString()));
        option3Button.setOnClickListener(v -> checkAnswer(option3Button.getText().toString()));
        option4Button.setOnClickListener(v -> checkAnswer(option4Button.getText().toString()));
    }

    private List<Flag> loadFlags() {
        List<Flag> flags = new ArrayList<>();
        Resources resources = getResources();
        String[] flagNames = resources.getStringArray(R.array.flag_names); // Add flag names in res/values/strings.xml
        for (String name : flagNames) {
            int resId = resources.getIdentifier(name, "drawable", getPackageName());
            flags.add(new Flag(formatFlagName(name), resId));
        }
        return flags;
    }

    private void startNewGame() {
        // Reset scores
        correctAnswers = 0;
        totalAsked = 0;
        
        // Create a new copy of all flags for this game
        remainingFlags = new ArrayList<>(allFlags);
        Collections.shuffle(remainingFlags);
        
        nextQuestion();
    }

    private void nextQuestion() {
        if (remainingFlags.isEmpty()) {
            showGameCompleteDialog();
            return;
        }

        // Get and remove the next flag
        currentFlag = remainingFlags.remove(0);
        
        // Set the flag image
        flagImageView.setImageResource(currentFlag.getResId());

        // Generate random options
        List<String> options = new ArrayList<>();
        options.add(currentFlag.getName());
        
        // Get 3 random wrong answers from allFlags
        List<Flag> possibleWrongAnswers = new ArrayList<>(allFlags);
        possibleWrongAnswers.remove(currentFlag);
        Collections.shuffle(possibleWrongAnswers);
        
        for (int i = 0; i < 3 && i < possibleWrongAnswers.size(); i++) {
            options.add(possibleWrongAnswers.get(i).getName());
        }
        
        Collections.shuffle(options);

        // Set button texts
        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
        option4Button.setText(options.get(3));

        totalAsked++;
    }

    private String formatFlagName(String resourceName) {
        // Remove "flag_" prefix
        String countryName = resourceName.substring(resourceName.indexOf("_") + 1);

        // Split the string by underscores
        String[] words = countryName.split("_");
        StringBuilder formattedName = new StringBuilder();

        // Capitalize each word and add space between them
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty() && !word.equals("and")) {
                // Capitalize first letter and append rest of the word
                formattedName.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase());

                // Add space if not the last word
                if (i < words.length - 1) {
                    formattedName.append(" ");
                }
            }
        }

        return formattedName.toString();
    }

    private void showGameCompleteDialog() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.game_complete_title)
            .setMessage(getString(R.string.game_complete_message, correctAnswers, totalAsked))
            .setCancelable(false)
            .setPositiveButton(R.string.play_again, (dialog, which) -> {
                startNewGame();
            })
            .setNegativeButton(R.string.exit, (dialog, which) -> {
                finish();
            })
            .show();
    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(currentFlag.getName())) {
            resultTextView.setText(getString(R.string.correct_answer, correctAnswers++, totalAsked));
        } else {
            resultTextView.setText(getString(R.string.wrong_answer, currentFlag.getName()));
        }
        
        if (!remainingFlags.isEmpty()) {
            nextQuestion();
        } else {
            showGameCompleteDialog();
        }
    }

    private static class Flag {
        private final String name;
        private final int resId;

        public Flag(String name, int resId) {
            this.name = name;
            this.resId = resId;
        }

        public String getName() {
            return name;
        }

        public int getResId() {
            return resId;
        }
    }
}