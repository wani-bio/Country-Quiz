package com.example.countryquiz;

/*
 * Android and Jetpack libraries for activity lifecycle,
 * ListView population, and database access.
 */
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Activity that displays a list of all past quiz results
 * stored in the SQLite database.
 */
public class ResultsActivity extends AppCompatActivity {

    ListView resultsListView;
    CountryQuizData quizData;

    /**
     * Called when the activity is created. Initializes the ListView,
     * opens the database, retrieves quiz results, and displays them.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsListView = findViewById(R.id.resultsListView);
        quizData = new CountryQuizData(this);
        quizData.open();

        List<String> results = quizData.getPastQuizResults();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                results
        );

        resultsListView.setAdapter(adapter);
    }

    /**
     * Closes the database when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        quizData.close();
    }
}