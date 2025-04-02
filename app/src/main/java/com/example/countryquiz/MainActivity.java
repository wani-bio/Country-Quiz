package com.example.countryquiz;

/*
 * Android and Jetpack libraries for UI components, button interactions,
 * and activity navigation.
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main activity that serves as the splash screen.
 * Allows the user to start a new quiz or view past quiz results.
 */
public class MainActivity extends AppCompatActivity {

    Button startQuizButton, viewResultsButton;

    /**
     * Initializes the splash screen, loads the country data from CSV,
     * and sets up button click listeners for navigation.
     *
     * @param savedInstanceState the saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start CSV Loader
        new CountryLoaderTask(this).execute();

        startQuizButton = findViewById(R.id.startQuizButton);
        viewResultsButton = findViewById(R.id.viewResultsButton);

        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        viewResultsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
        });
    }
}