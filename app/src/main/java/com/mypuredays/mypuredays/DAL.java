package com.mypuredays.mypuredays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAL {

    private static SQLiteDatabase dbRead;
    private static SQLiteDatabase dbWrite;

    public DAL(Context context) {
        DbHelper db = new DbHelper(context);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();
    }
    //write row to table
    public long DBWrite(String tableName, ContentValues val) {
        return dbWrite.insertOrThrow(tableName, null, val);
    }
    //return all data from table
    public Cursor DBRead(String tableName) {
        String[] cols = Utils.getColumnsArray(tableName);
        return dbRead.query(tableName, cols, null, null, null, null, null);
    }
    //return data by criteria
    public Cursor DBReadRow(String tableName, String selection, String[] selectionArgs) {
        String[] cols = Utils.getColumnsArray(tableName);
        return dbRead.query(tableName, cols, selection, selectionArgs, null, null, null);
    }
    //return data by specific columns and criteria
    public Cursor DBReadRow(String tableName, String[] cols, String selection, String[] selectionArgs) {
        return dbRead.query(tableName, cols, selection, selectionArgs, null, null, null);
    }
    //update data by criteria
    public void DBUpdate(String tableName, ContentValues val, String criteria, String[] criteriaVal) {
        dbWrite.update(tableName, val, criteria, criteriaVal);
    }
    //delete item by criteria
    public void DBDeleteItem(String tableName, String criteria, String[] criteriaVal) {
        dbWrite.delete(tableName, criteria,criteriaVal);
    }

    public Cursor getMaxId(String tableName, String[] cols){
        return dbRead.query(tableName, cols, null, null, null, null, null);
    }
}
