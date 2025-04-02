package com.example.countryquiz;

/*
 * Android and Java libraries used for:
 * - Database access and queries
 * - Content value mapping
 * - Cursor navigation
 * - List management
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for interacting with the quiz database,
 * including reading country data and storing quiz results.
 */
public class CountryQuizData {
    private SQLiteDatabase db;
    private CountryQuizDBHelper dbHelper;

    /**
     * Constructs a CountryQuizData object with the given context.
     *
     * @param context the application context
     */
    public CountryQuizData(Context context) {
        dbHelper = CountryQuizDBHelper.getInstance(context);
    }

    /**
     * Opens a writable connection to the database.
     */
    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Closes the database connection if it is open.
     */
    public void close() {
        if (db != null) db.close();
    }

    /**
     * Retrieves all countries from the database.
     *
     * @return a list of Country objects
     */
    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        Cursor cursor = db.query(CountryQuizDBHelper.TABLE_COUNTRIES,
                null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(CountryQuizDBHelper.COLUMN_NAME));
            String continent = cursor.getString(cursor.getColumnIndexOrThrow(CountryQuizDBHelper.COLUMN_CONTINENT));
            countries.add(new Country(name, continent));
        }

        cursor.close();
        return countries;
    }

    /**
     * Stores a quiz result in the database.
     *
     * @param date  the date of the quiz attempt
     * @param score the score the user achieved
     */
    public void storeQuizResult(String date, int score) {
        ContentValues values = new ContentValues();
        values.put(CountryQuizDBHelper.COLUMN_QUIZ_DATE, date);
        values.put(CountryQuizDBHelper.COLUMN_SCORE, score);
        db.insert(CountryQuizDBHelper.TABLE_QUIZZES, null, values);
    }

    /**
     * Retrieves all past quiz results from the database, ordered by date descending.
     *
     * @return a list of formatted quiz results
     */
    public List<String> getPastQuizResults() {
        List<String> results = new ArrayList<>();
        Cursor cursor = db.query(CountryQuizDBHelper.TABLE_QUIZZES,
                null, null, null, null, null, CountryQuizDBHelper.COLUMN_QUIZ_DATE + " DESC");

        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(CountryQuizDBHelper.COLUMN_QUIZ_DATE));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(CountryQuizDBHelper.COLUMN_SCORE));
            results.add(date + " - Score: " + score + "/6");
        }

        cursor.close();
        return results;
    }
}