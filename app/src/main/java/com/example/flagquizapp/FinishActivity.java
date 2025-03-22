package com.example.flagquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);
        boolean isQuickQuiz = getIntent().getBooleanExtra("isQuickQuiz", false);

        // Get best score from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("FlagQuizPrefs", MODE_PRIVATE);
        String bestScoreKey = isQuickQuiz ? "quickQuizBestScore" : "fullQuizBestScore";
        int bestScore = prefs.getInt(bestScoreKey, 0);

        // Update best score if current score is better
        if (score > bestScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(bestScoreKey, score);
            editor.apply();
            bestScore = score;
        }

        TextView finalScoreText = findViewById(R.id.finalScoreText);
        TextView bestScoreText = findViewById(R.id.bestScoreText);
        
        finalScoreText.setText(getString(R.string.final_score, score, total));
        bestScoreText.setText(getString(R.string.best_score, bestScore, isQuickQuiz ? 10 : total));

        Button playAgainButton = findViewById(R.id.playAgainButton);
        Button exitButton = findViewById(R.id.exitButton);

        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(FinishActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        });

        exitButton.setOnClickListener(v -> finishAffinity());
    }
} 