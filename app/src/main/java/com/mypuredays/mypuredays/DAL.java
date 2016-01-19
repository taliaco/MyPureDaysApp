package com.mypuredays.mypuredays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by yael on 23/11/2015.
 */
public class DAL {

    private static DbHelper db;
    private static SQLiteDatabase dbRead;
    private static SQLiteDatabase dbWrite;

    public DAL(Context context) {
        db = new DbHelper(context);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();
    }

    public long DBWrite(String tableName, ContentValues val) {
        return dbWrite.insertOrThrow(tableName, null, val);
    }
    //return all the table
    public Cursor DBRead(String tableName) {
        String[] cols = Utils.getColumnsArray(tableName);
        Cursor c = dbRead.query(tableName, cols, null, null, null, null, null);
        return c;
    }
    public Cursor DBReadByCol(String tableName,  String [] cols) {
        // String[] cols = Utils.getColumnsArray(tableName);
        Cursor c = dbRead.query(tableName, cols, null, null, null, null, null);
        return c;
    }
    public Cursor DBReadByCol(String tableName,  String [] cols,String selection, String[] selectionArgs) {
        // String[] cols = Utils.getColumnsArray(tableName);
        Cursor c = dbRead.query(tableName, cols, selection, selectionArgs, null, null, null);
        return c;
    }
    //return one row
    public Cursor DBReadRow(String tableName, String selection, String[] selectionArgs) {
        String[] cols = Utils.getColumnsArray(tableName);
        Cursor c = dbRead.query(tableName, cols, selection, selectionArgs, null, null, null);
        return c;
    }

    public Cursor DBReadRow(String tableName ,String[] cols , String selection, String[] selectionArgs) {
        Cursor c = dbRead.query(tableName, cols, selection, selectionArgs, null, null, null);
        return c;
    }

    public void DBUpdate(String tableName, ContentValues val, String criteria, String[] criteriaVal) {
        dbWrite.update(tableName, val, criteria, criteriaVal);
    }

    public void DBDeleteItem(String tableName, String criteria) {
        dbWrite.delete(tableName, criteria, null);
    }

    public Cursor getMaxId(String tableName, String[] cols){
        return dbRead.query(tableName, cols, null, null, null, null, null);
    }

//
//    public Cursor readFromDay(Date date) {
//        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
//        SQLiteDatabase dbReader = db.getReadableDatabase();
//
//        String[] cols = {Constants._ID, Constants.COL_DATE, Constants.COL_DAY_TYPE, Constants.COL_NOTES};
//        String selection = Constants.COL_DATE + "=?";
//        String[] selectionArgs = {sdf.format(date)};
//
//        Cursor c = dbReader.query(Constants.TABLE_DAY, cols, selection, selectionArgs, null, null, null);
//
//        dbReader.close();
//        return c;
//    }
//
//    public DayType readFromDayType() {
//        SQLiteDatabase dbReader = db.getReadableDatabase();
//        DayType dayType = null;
//        //String dataRead = "";
//        String[] cols = {Constants._ID, Constants.COL_ID_DAY_TYPE, Constants.COL_TYPE_DAY_TYPE};
//        Cursor c = dbReader.query(Constants.TABLE_DAY_TYPE, cols, null, null, null, null, null);
//
//        while (c.moveToNext()) {
//            dayType = new DayType(c.getInt(0), c.getInt(1), c.getString(2));
//        }
//        dbReader.close();
//
//        return dayType;
//    }
//
//    public ClearDayType readFromClearDayType() {
//        SQLiteDatabase dbReader = db.getReadableDatabase();
//        ClearDayType clearDayType = null;
//        //String dataRead = "";
//        String[] cols = {Constants._ID, Constants.COL_ID_CLEAR_DAY_TYPE, Constants.COL_TYPE_CLEAR_DAY_TYPE};
//        Cursor c = dbReader.query(Constants.COL_TYPE_CLEAR_DAY_TYPE, cols, null, null, null, null, null);
//
//
//        while (c.moveToNext()) {
//            clearDayType = new ClearDayType(c.getInt(0), c.getInt(1), c.getString(2));
//        }
//        dbReader.close();
//        return clearDayType;
//    }
//
//    public Definition readFromDefinition() {
//        SQLiteDatabase dbReader = db.getReadableDatabase();
//        Definition definition = null;
//        //String dataRead = "";
//        String[] cols = {Constants._ID, Constants.COL_MIN_PERIOD_LENGTH, Constants.COL_REGULAR, Constants.COL_PRISHA_DAYS,
//                Constants.COL_PERIOD_LENGTH, Constants.COL_OVULATION_NOTIFICATION, Constants.COL_CLEAN_NOTIFICATION,
//                Constants.COL_COUNT_CLEAN, Constants.COL_DAILY_NOTIFICATION};
//        Cursor c = dbReader.query(Constants.TABLE_DEFINITION, cols, null, null, null, null, null);
//
//        Boolean regularColumn, prishaDaysColumn, countCleanColumn, dailyNotificationColumn;
//
//
//        while (c.moveToNext()) {
//
//            regularColumn = (c.getInt(1) != 0);
//            prishaDaysColumn = (c.getInt(2) != 0);
//            countCleanColumn = (c.getInt(7) != 0);
//            dailyNotificationColumn = (c.getInt(8) != 0);
//
//            definition = new Definition(c.getInt(0), c.getInt(1), regularColumn, prishaDaysColumn, c.getInt(4), c.getInt(5), c.getInt(6), countCleanColumn, dailyNotificationColumn);
//        }
//        dbReader.close();
//
//        return definition;
//    }
//
//    public Cursor getDefinitionCursor() {
//
//        SQLiteDatabase dbReader = db.getReadableDatabase();
//        Cursor c = null;
//
//        String[] cols = {Constants._ID, Constants.COL_MIN_PERIOD_LENGTH, Constants.COL_REGULAR, Constants.COL_PRISHA_DAYS,
//                Constants.COL_PERIOD_LENGTH, Constants.COL_OVULATION_NOTIFICATION, Constants.COL_CLEAN_NOTIFICATION,
//                Constants.COL_COUNT_CLEAN, Constants.COL_DAILY_NOTIFICATION};
//        c = dbReader.query(Constants.TABLE_DEFINITION, cols, null, null, null, null, null);
//
//        return c;
//
//    }
//
//
//    public void writeToDay(Date date, int dateTypeId, String notes) {
//        SQLiteDatabase dbWriter = db.getWritableDatabase();
//
//        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        String strDate = sdf.format(date);
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.COL_DATE, strDate);
//        values.put(Constants.COL_DAY_TYPE, dateTypeId);
//        values.put(Constants.COL_NOTES, notes);
//
//        dbWriter.insertOrThrow(Constants.TABLE_DAY, null, values);
//        dbWriter.close();
//
//    }
//
//    public void writeToDayType(int ID, String Type) {
//        SQLiteDatabase dbWriter = db.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.COL_ID_DAY_TYPE, ID);
//        values.put(Constants.COL_TYPE_DAY_TYPE, Type);
//
//        dbWriter.insertOrThrow(Constants.TABLE_DAY_TYPE, null, values);
//        dbWriter.close();
//
//    }
//
//    public void writeToClearDayType(int ID, String Type) {
//        SQLiteDatabase dbWriter = db.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.COL_ID_CLEAR_DAY_TYPE, ID);
//        values.put(Constants.COL_TYPE_CLEAR_DAY_TYPE, Type);
//
//        dbWriter.insertOrThrow(Constants.TABLE_CLEAR_DAY_TYPE, null, values);
//        dbWriter.close();
//
//    }
//
//    public void writeToDefinition(int minPeriodLength, boolean regulary, boolean prishaDays, int periodLength,
//                                  int ovulationNutification, int cleanNotification, boolean countClean, boolean dailyNotification) {
//
//        int regularyInt, prishaDaysInt, countCleanInt, dailyNotificationInt;
//
//        regularyInt = (regulary) ? 1 : 0;
//        prishaDaysInt = (prishaDays) ? 1 : 0;
//        countCleanInt = (countClean) ? 1 : 0;
//        dailyNotificationInt = (dailyNotification) ? 1 : 0;
//
//        SQLiteDatabase dbWriter = db.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.COL_MIN_PERIOD_LENGTH, minPeriodLength);
//        values.put(Constants.COL_REGULAR, regularyInt);
//        values.put(Constants.COL_PRISHA_DAYS, prishaDaysInt);
//        values.put(Constants.COL_PERIOD_LENGTH, periodLength);
//        values.put(Constants.COL_OVULATION_NOTIFICATION, ovulationNutification);
//        values.put(Constants.COL_CLEAN_NOTIFICATION, cleanNotification);
//        values.put(Constants.COL_COUNT_CLEAN, countCleanInt);
//        values.put(Constants.COL_DAILY_NOTIFICATION, dailyNotificationInt);
//
//
//        dbWriter.insertOrThrow(Constants.TABLE_DEFINITION, null, values);
//        dbWriter.close();
//
//    }
//
//    public void writeToDefinition(Definition def) {
//
//        int regularyInt, prishaDaysInt, countCleanInt, dailyNotificationInt;
//
//        regularyInt = (def.is_regulary()) ? 1 : 0;
//        prishaDaysInt = (def.is_prishaDays()) ? 1 : 0;
//        countCleanInt = (def.is_countClean()) ? 1 : 0;
//        dailyNotificationInt = (def.is_dailyNotification()) ? 1 : 0;
//
//        SQLiteDatabase dbWriter = db.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.COL_MIN_PERIOD_LENGTH, def.get_minPeriodLength());
//        values.put(Constants.COL_REGULAR, regularyInt);
//        values.put(Constants.COL_PRISHA_DAYS, prishaDaysInt);
//        values.put(Constants.COL_PERIOD_LENGTH, def.get_periodLength());
//        values.put(Constants.COL_OVULATION_NOTIFICATION, def.get_ovulationNutification());
//        values.put(Constants.COL_CLEAN_NOTIFICATION, def.get_cleanNotification());
//        values.put(Constants.COL_COUNT_CLEAN, countCleanInt);
//        values.put(Constants.COL_DAILY_NOTIFICATION, dailyNotificationInt);
//
//
//        dbWriter.insertOrThrow(Constants.TABLE_DEFINITION, null, values);
//        dbWriter.close();
//
//    }


}
