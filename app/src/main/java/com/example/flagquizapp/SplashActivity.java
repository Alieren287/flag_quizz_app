package com.example.flagquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button startFullQuizButton = findViewById(R.id.startFullQuizButton);
        Button startQuickQuizButton = findViewById(R.id.startQuickQuizButton);

        startFullQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("isQuickQuiz", false);
            startActivity(intent);
            finish();
        });

        startQuickQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("isQuickQuiz", true);
            startActivity(intent);
            finish();
        });
    }
} 