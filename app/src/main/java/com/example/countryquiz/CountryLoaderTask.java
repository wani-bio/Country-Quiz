package com.example.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An AsyncTask that loads country data from a CSV file in the assets folder
 * and inserts it into the SQLite database.
 */
public class CountryLoaderTask extends AsyncTask<Void, Void, Void> {
    private final Context context;

    /**
     * Constructs a CountryLoaderTask.
     *
     * @param context the application context used to access assets and the database
     */
    public CountryLoaderTask(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Reads country and continent data from the CSV file and inserts them into the database.
     *
     * @param voids no parameters used
     * @return null (no result needed)
     */
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            CountryQuizDBHelper dbHelper = CountryQuizDBHelper.getInstance(context);
            InputStream inputStream = context.getAssets().open("country_continent.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));

            String[] row;
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Clear existing countries before inserting new ones
            db.delete(CountryQuizDBHelper.TABLE_COUNTRIES, null, null);

            while ((row = reader.readNext()) != null) {
                String name = row[0].trim();
                String continent = row[1].trim();

                ContentValues values = new ContentValues();
                values.put(CountryQuizDBHelper.COLUMN_NAME, name);
                values.put(CountryQuizDBHelper.COLUMN_CONTINENT, continent);

                db.insert(CountryQuizDBHelper.TABLE_COUNTRIES, null, values);
            }

            reader.close();
        } catch (Exception e) {
            Log.e("CountryLoaderTask", "Error loading CSV", e);
        }

        return null;
    }
}
