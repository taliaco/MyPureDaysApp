package com.mypuredays.mypuredays;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yael on 23/11/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pureDaysDB.db";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_DAY + "(" + Constants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.COLUMN_NAME_DATE + " TEXT_TYPE,"
                + Constants.COLUMN_NAME_DAY_TYPE + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_NOTES + " TEXT_TYPE);" );

        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_DAY_TYPE + "(" + Constants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.COLUMN_NAME_ID_DAY_TYPE + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_TYPE_DAY_TYPE + " TEXT_TYPE);" );

        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_CLEAR_DAY_TYPE + "(" + Constants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.COLUMN_NAME_ID_CLEAR_DAY_TYPE + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_TYPE_CLEAR_DAY_TYPE + " TEXT_TYPE);" );

        db.execSQL("CREATE TABLE " + Constants.TABLE_NAME_DEFINITION + "(" + Constants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.COLUMN_NAME_MIN_PERIOD_LENGTH + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_REGULAR + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_PRISHA_DAYS + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_PERIOD_LENGTH + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_OVULATION_NOTIFICATION + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_CLEAN_NOTIFICATION + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_COUNT_CLEAN + " INTEGER_TYPE,"
                + Constants.COLUMN_NAME_DAILY_NOTIFICATION + " INTEGER_TYPE);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_DAY);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_DAY_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CLEAR_DAY_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_DEFINITION);
    }

}
