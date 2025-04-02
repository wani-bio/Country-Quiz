package com.example.countryquiz;

/*
 * Android libraries for database management using SQLite.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper class that manages database creation and versioning
 * for the Country Quiz app.
 */
public class CountryQuizDBHelper extends SQLiteOpenHelper {

    // Database configuration
    private static final String DB_NAME = "countryquiz.db";
    private static final int DB_VERSION = 1;

    // Table and column names for countries
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CONTINENT = "continent";

    // Table and column names for quizzes
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String COLUMN_QUIZ_ID = "_id";
    public static final String COLUMN_QUIZ_DATE = "date";
    public static final String COLUMN_SCORE = "score";

    private static CountryQuizDBHelper instance;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param context the application context
     */
    private CountryQuizDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Gets the singleton instance of the database helper.
     *
     * @param context the application context
     * @return the CountryQuizDBHelper instance
     */
    public static synchronized CountryQuizDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CountryQuizDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Creates the initial database schema: countries and quizzes tables.
     *
     * @param db the SQLite database instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCountries = "CREATE TABLE " + TABLE_COUNTRIES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_CONTINENT + " TEXT)";

        String createQuizzes = "CREATE TABLE " + TABLE_QUIZZES + " ("
                + COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_QUIZ_DATE + " TEXT, "
                + COLUMN_SCORE + " INTEGER)";

        db.execSQL(createCountries);
        db.execSQL(createQuizzes);
    }

    /**
     * Handles database upgrades by dropping existing tables and recreating them.
     *
     * @param db the SQLite database instance
     * @param oldVersion the previous database version
     * @param newVersion the new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        onCreate(db);
    }
}