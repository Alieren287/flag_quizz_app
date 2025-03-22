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

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close splash activity
            } catch (Exception e) {
                e.printStackTrace();
                // You might want to show an error message to the user here
            }
        });
    }
} 