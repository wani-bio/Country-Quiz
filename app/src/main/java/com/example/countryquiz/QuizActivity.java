package com.example.countryquiz;

/*
 * Android libraries for activity lifecycle, UI navigation, background tasks, and toasts.
 * Java utilities for date formatting, localization, and memory-safe references.
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The activity that handles quiz gameplay using a ViewPager2 and QuestionFragments.
 * It loads questions from the database, manages quiz flow, and saves results.
 */
public class QuizActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    CountryQuizData quizData;
    Quiz quiz;
    boolean resultStored = false; // Track if result has been saved

    /**
     * Called when the quiz activity is created. Initializes the ViewPager
     * and loads country data asynchronously.
     *
     * @param savedInstanceState the saved instance state, if any
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        viewPager = findViewById(R.id.viewPager);

        quizData = new CountryQuizData(this);
        quizData.open();

        // Load countries in background
        new LoadCountriesTask(this).execute();
    }

    /**
     * Closes the database connection when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        quizData.close();
    }

    /**
     * AsyncTask to load countries from the database and start the quiz.
     * Uses WeakReference to avoid memory leaks.
     */
    private static class LoadCountriesTask extends AsyncTask<Void, Void, List<Country>> {
        private WeakReference<QuizActivity> activityRef;

        /**
         * Constructs the task with a weak reference to the activity.
         *
         * @param activity the quiz activity instance
         */
        LoadCountriesTask(QuizActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        /**
         * Loads the list of countries in the background.
         *
         * @param voids unused
         * @return list of countries from the database
         */
        @Override
        protected List<Country> doInBackground(Void... voids) {
            QuizActivity activity = activityRef.get();
            if (activity == null) return null;
            return activity.quizData.getAllCountries();
        }

        /**
         * Initializes the quiz and sets up ViewPager2 with quiz fragments.
         *
         * @param countries the list of countries loaded from the database
         */
        @Override
        protected void onPostExecute(List<Country> countries) {
            QuizActivity activity = activityRef.get();
            if (activity == null || countries == null) return;

            if (countries.size() < 6) {
                Toast.makeText(activity, "Not enough countries!", Toast.LENGTH_SHORT).show();
                activity.finish();
            } else {
                activity.quiz = new Quiz(countries);
                activity.viewPager.setAdapter(new QuizPagerAdapter(
                        activity.quiz,
                        activity.getSupportFragmentManager(),
                        activity.getLifecycle()
                ));

                activity.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);

                        int total = activity.quiz.getQuestions().size();
                        if (position == total && !activity.resultStored) {
                            activity.resultStored = true;

                            // Save quiz result in background
                            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
                            new SaveQuizResultTask(activity, date, activity.quiz.getCurrentScore()).execute();
                        }
                    }
                });
            }
        }
    }

    /**
     * AsyncTask to save the quiz result (date and score) into the database.
     * Uses WeakReference to avoid leaking the activity context.
     */
    private static class SaveQuizResultTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<QuizActivity> activityRef;
        private String date;
        private int score;

        /**
         * Constructs the task with activity reference, quiz date, and score.
         *
         * @param activity the quiz activity instance
         * @param date     the date of the quiz attempt
         * @param score    the final score
         */
        SaveQuizResultTask(QuizActivity activity, String date, int score) {
            this.activityRef = new WeakReference<>(activity);
            this.date = date;
            this.score = score;
        }

        /**
         * Performs the database insert operation in the background.
         *
         * @param voids unused
         * @return null
         */
        @Override
        protected Void doInBackground(Void... voids) {
            QuizActivity activity = activityRef.get();
            if (activity != null) {
                activity.quizData.storeQuizResult(date, score);
            }
            return null;
        }

        /**
         * Displays a toast message to show that the result was saved.
         *
         * @param unused unused
         */
        @Override
        protected void onPostExecute(Void unused) {
            QuizActivity activity = activityRef.get();
            if (activity != null) {
                Toast.makeText(activity, "Quiz finished! Score: " + score + "/6", Toast.LENGTH_LONG).show();
            }
        }
    }
}