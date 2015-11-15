package com.learn2develop.creditcards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 11/7/2015.
 */
public class SQLHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLHelper.class.getSimpleName();

    public static final String TABLE_CREDIT_CARDS = "credit_cards";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_CREDIT = "credit";
    public static final String COLUMN_BALANCE = "balance";
    public static final String COLUMN_AVAILABLE = "available";
    public static final String COLUMN_DUE = "due";

    private static final String DATABASE_NAME = "credit_cards.db";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
            "CREATE TABLE "+TABLE_CREDIT_CARDS
                    +" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +COLUMN_NAME+" TEXT NOT NULL, "
                    +COLUMN_NUMBER+" INTEGER NOT NULL, "
                    +COLUMN_CREDIT+" REAL, "
                    +COLUMN_BALANCE+" REAL, "
                    +COLUMN_AVAILABLE+" REAL, "
                    +COLUMN_DUE+" INTEGER " +

    ");";
    public static final String TABLE_ACTIVITY = "credit_cards_activities";
    public static final String COLUMN_ACTIVITY_ID = "_id";
    public static final String COLUMN_ACTIVITY = "activity";
    public static final String COLUMN_ACTIVITY_DATE = "date";
    private static final String ACTIVITY_DATABASE_CREATE =
            "CREATE TABLE " + TABLE_ACTIVITY
                    + " (" + COLUMN_ACTIVITY_ID + " INTEGER, "
                    + COLUMN_ACTIVITY + " TEXT, "
                    + COLUMN_ACTIVITY_DATE + " INTEGER " +
                    ");";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "DB created");
        db.execSQL(DATABASE_CREATE);
        db.execSQL(ACTIVITY_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "DB upgrading. Dropping old table...");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDIT_CARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        onCreate(db);
    }
}