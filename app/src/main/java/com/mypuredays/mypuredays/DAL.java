package com.mypuredays.mypuredays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yael on 23/11/2015.
 */
public class DAL {

    DbHelper db;


    public DAL(Context context)
    {
        db = new DbHelper(context);
    }


    public Day readFromDay()
    {
        SQLiteDatabase dbReader = db.getReadableDatabase();
        Day day= null;
        //String dataRead = "";
        String[] cols = {Constants._ID, Constants.COLUMN_NAME_DATE, Constants.COLUMN_NAME_DAY_TYPE, Constants.COLUMN_NAME_NOTES};
        Cursor c = dbReader.query(Constants.TABLE_NAME_DAY,cols,null,null,null,null,null);

        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date date = null;


        while (c.moveToNext())
        {
            String dateString = c.getString(1);
            try {
                date = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            day = new Day(c.getInt(0),date,c.getInt(2),c.getString(3));
        }
        dbReader.close();

        return day;
    }

    public DayType readFromDayType()
    {
        SQLiteDatabase dbReader = db.getReadableDatabase();
        DayType dayType= null;
        //String dataRead = "";
        String[] cols = {Constants._ID, Constants.COLUMN_NAME_ID_DAY_TYPE, Constants.COLUMN_NAME_TYPE_DAY_TYPE};
        Cursor c = dbReader.query(Constants.TABLE_NAME_DAY_TYPE,cols,null,null,null,null,null);

        while (c.moveToNext())
        {
            dayType = new DayType(c.getInt(0),c.getInt(1),c.getString(2));
        }
        dbReader.close();

        return dayType;
    }

    public ClearDayType readFromClearDayType()
    {
        SQLiteDatabase dbReader = db.getReadableDatabase();
        ClearDayType clearDayType= null;
        //String dataRead = "";
        String[] cols = {Constants._ID, Constants.COLUMN_NAME_ID_CLEAR_DAY_TYPE, Constants.COLUMN_NAME_TYPE_CLEAR_DAY_TYPE};
        Cursor c = dbReader.query(Constants.COLUMN_NAME_TYPE_CLEAR_DAY_TYPE,cols,null,null,null,null,null);


        while (c.moveToNext())
        {
            clearDayType = new ClearDayType(c.getInt(0),c.getInt(1),c.getString(2));
        }
        dbReader.close();
        return clearDayType;
    }

    public Definition readFromDefinition()
    {
        SQLiteDatabase dbReader = db.getReadableDatabase();
        Definition definition= null;
        //String dataRead = "";
        String[] cols = {Constants._ID, Constants.COLUMN_NAME_MIN_PERIOD_LENGTH, Constants.COLUMN_NAME_REGULAR, Constants.COLUMN_NAME_PRISHA_DAYS,
                Constants.COLUMN_NAME_PERIOD_LENGTH, Constants.COLUMN_NAME_OVULATION_NOTIFICATION, Constants.COLUMN_NAME_CLEAN_NOTIFICATION,
                Constants.COLUMN_NAME_COUNT_CLEAN, Constants.COLUMN_NAME_DAILY_NOTIFICATION};
        Cursor c = dbReader.query(Constants.TABLE_NAME_DEFINITION,cols,null,null,null,null,null);

        Boolean regularColumn,prishaDaysColumn,countCleanColumn,dailyNotificationColumn;


        while (c.moveToNext())
        {

            regularColumn = (c.getInt(1) != 0);
            prishaDaysColumn = (c.getInt(2) != 0);
            countCleanColumn = (c.getInt(7) != 0);
            dailyNotificationColumn = (c.getInt(8) != 0);

            definition = new Definition(c.getInt(0),c.getInt(1),regularColumn,prishaDaysColumn,c.getInt(4),c.getInt(5),c.getInt(6),countCleanColumn, dailyNotificationColumn);
        }
        dbReader.close();

        return definition;
    }

    public Cursor readCursorFromDefinition() {

        SQLiteDatabase dbReader = db.getReadableDatabase();
        Cursor c = null;

        String[] cols = {Constants._ID, Constants.COLUMN_NAME_MIN_PERIOD_LENGTH, Constants.COLUMN_NAME_REGULAR, Constants.COLUMN_NAME_PRISHA_DAYS,
                Constants.COLUMN_NAME_PERIOD_LENGTH, Constants.COLUMN_NAME_OVULATION_NOTIFICATION, Constants.COLUMN_NAME_CLEAN_NOTIFICATION,
                Constants.COLUMN_NAME_COUNT_CLEAN, Constants.COLUMN_NAME_DAILY_NOTIFICATION};
        c = dbReader.query(Constants.TABLE_NAME_DEFINITION,cols,null,null,null,null,null);

        return c;

    }



    public void writeToDay(Date date, int dateTypeId, String notes)
    {
        SQLiteDatabase dbWriter = db.getWritableDatabase();

        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(date);

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME_DATE, strDate);
        values.put(Constants.COLUMN_NAME_DAY_TYPE, dateTypeId);
        values.put(Constants.COLUMN_NAME_NOTES, notes);

        dbWriter.insertOrThrow(Constants.TABLE_NAME_DAY, null, values);
        dbWriter.close();

    }

    public void writeToDayType(int ID, String Type)
    {
        SQLiteDatabase dbWriter = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME_ID_DAY_TYPE, ID);
        values.put(Constants.COLUMN_NAME_TYPE_DAY_TYPE, Type);

        dbWriter.insertOrThrow(Constants.TABLE_NAME_DAY_TYPE, null, values);
        dbWriter.close();

    }

    public void writeToClearDayType(int ID, String Type)
    {
        SQLiteDatabase dbWriter = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME_ID_CLEAR_DAY_TYPE, ID);
        values.put(Constants.COLUMN_NAME_TYPE_CLEAR_DAY_TYPE, Type);

        dbWriter.insertOrThrow(Constants.TABLE_NAME_CLEAR_DAY_TYPE, null, values);
        dbWriter.close();

    }

    public void writeToDefinition(int minPeriodLength, boolean regulary, boolean prishaDays,int periodLength,
                                  int ovulationNutification,int cleanNotification, boolean countClean, boolean dailyNotification)
    {

        int regularyInt,prishaDaysInt,countCleanInt,dailyNotificationInt;

        regularyInt    = (regulary) ? 1 : 0;
        prishaDaysInt    = (prishaDays) ? 1 : 0;
        countCleanInt    = (countClean) ? 1 : 0;
        dailyNotificationInt    = (dailyNotification) ? 1 : 0;

        SQLiteDatabase dbWriter = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME_MIN_PERIOD_LENGTH, minPeriodLength);
        values.put(Constants.COLUMN_NAME_REGULAR, regularyInt);
        values.put(Constants.COLUMN_NAME_PRISHA_DAYS, prishaDaysInt);
        values.put(Constants.COLUMN_NAME_PERIOD_LENGTH, periodLength);
        values.put(Constants.COLUMN_NAME_OVULATION_NOTIFICATION, ovulationNutification);
        values.put(Constants.COLUMN_NAME_CLEAN_NOTIFICATION, cleanNotification);
        values.put(Constants.COLUMN_NAME_COUNT_CLEAN, countCleanInt);
        values.put(Constants.COLUMN_NAME_DAILY_NOTIFICATION, dailyNotificationInt);


        dbWriter.insertOrThrow(Constants.TABLE_NAME_DEFINITION, null, values);
        dbWriter.close();

    }
    public void writeToDefinition(Definition def)
    {

        int regularyInt,prishaDaysInt,countCleanInt,dailyNotificationInt;

        regularyInt    = (def.is_regulary()) ? 1 : 0;
        prishaDaysInt    = (def.is_prishaDays()) ? 1 : 0;
        countCleanInt    = (def.is_countClean()) ? 1 : 0;
        dailyNotificationInt    = (def.is_dailyNotification()) ? 1 : 0;

        SQLiteDatabase dbWriter = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME_MIN_PERIOD_LENGTH, def.get_minPeriodLength());
        values.put(Constants.COLUMN_NAME_REGULAR, regularyInt);
        values.put(Constants.COLUMN_NAME_PRISHA_DAYS, prishaDaysInt);
        values.put(Constants.COLUMN_NAME_PERIOD_LENGTH, def.get_periodLength());
        values.put(Constants.COLUMN_NAME_OVULATION_NOTIFICATION, def.get_ovulationNutification());
        values.put(Constants.COLUMN_NAME_CLEAN_NOTIFICATION, def.get_cleanNotification());
        values.put(Constants.COLUMN_NAME_COUNT_CLEAN, countCleanInt);
        values.put(Constants.COLUMN_NAME_DAILY_NOTIFICATION, dailyNotificationInt);


        dbWriter.insertOrThrow(Constants.TABLE_NAME_DEFINITION, null, values);
        dbWriter.close();

    }

}
