package com.mypuredays.mypuredays;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Talia on 25/11/2015.
 */
public class BL {
    DAL dal;
    Context context;
    public BL(Context contextview) {
        context = contextview;
        dal = new DAL(contextview);
    }

    public void populateDB() {
        populateDefinition();

        //ClearDayType clrDayType = new ClearDayType()
    }

    public void populateDefinition() {
        Definition def = new Definition();
        int regularyInt, prishaDaysInt, countCleanInt, dailyNotificationInt;

        regularyInt = (def.is_regulary()) ? 1 : 0;
        prishaDaysInt = (def.is_prishaDays()) ? 1 : 0;
        countCleanInt = (def.is_countClean()) ? 1 : 0;
        dailyNotificationInt = (def.is_dailyNotification()) ? 1 : 0;


        ContentValues values = new ContentValues();
        values.put(Constants.COL_MIN_PERIOD_LENGTH, def.get_minPeriodLength());
        values.put(Constants.COL_REGULAR, regularyInt);
        values.put(Constants.COL_PRISHA_DAYS, prishaDaysInt);
        values.put(Constants.COL_PERIOD_LENGTH, def.get_periodLength());
        values.put(Constants.COL_OVULATION_NOTIFICATION, def.get_ovulationNutification());
        values.put(Constants.COL_CLEAN_NOTIFICATION, def.get_cleanNotification());
        values.put(Constants.COL_COUNT_CLEAN, countCleanInt);
        values.put(Constants.COL_DAILY_NOTIFICATION, dailyNotificationInt);

        dal.DBWrite(Constants.TABLE_DEFINITION, values);
    }

    public Definition getDefinition() {
        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        Definition def;
        Boolean regularColumn, prishaDaysColumn, countCleanColumn, dailyNotificationColumn;
        if (c.moveToFirst()) {
            regularColumn = (c.getInt(1) != 0);
            prishaDaysColumn = (c.getInt(2) != 0);
            countCleanColumn = (c.getInt(7) != 0);
            dailyNotificationColumn = (c.getInt(8) != 0);
            def = new Definition(c.getInt(0), c.getInt(1), regularColumn, prishaDaysColumn, c.getInt(4), c.getInt(5), c.getInt(6), countCleanColumn, dailyNotificationColumn);
            return def;
        }
        return null;
    }

    public Cursor getDefinitionCursor() {
        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        if (c.moveToFirst()) {
            return c;
        }
        return null;
    }

    public boolean getDefinitionSwitchState(String columnName){

        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        if (c.moveToFirst()) {

            for(int i=0; i<c.getColumnCount(); i++){
                if(c.getColumnName(i).equals(columnName)){

                   return (c.getInt(i) != 0);

                }
            }
        }

        return false;
    }

    public int getDefinitionSpinnerState(String columnName){

        return 0;
    }

    public void setStartEndLooking(Date date, Constants.DAY_TYPE dayType) {
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {sdf.format(date)};

        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, selection, selectionArgs);
        if (c.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DAY_TYPE, dayType.ordinal());
            //values.put(Constants.COL_DAY_TYPE, Utils.getDayTypeIDByName(dayType));
            String criteria = Constants.COL_DATE + "=" + sdf.format(date);

            dal.DBUpdate(Constants.TABLE_DAY, values, criteria);
        } else {
            String strDate = sdf.format(date);
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DATE, strDate);
            values.put(Constants.COL_DAY_TYPE, dayType.ordinal());
            dal.DBWrite(Constants.TABLE_DAY, values);
        }
    }



    public int getMaxIdDay(String tableName) {
        String[] cols = new String []{"MAX(" + Constants._ID + ")"};
        Cursor c = dal.getMaxId(tableName, cols);
        if (c.moveToFirst()) {
            Log.e("MAX ID", String.valueOf(c.getInt(0)));
            return c.getInt(0);
        }
        return -1;

    }

    public ArrayList<Day> getAllDays() {
        Resources res = context.getResources();
        String notes;
        int dayType = -1;
        Date dt = null;
        ArrayList<Day> arrDays = new ArrayList<>();

        SimpleDateFormat ft = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);


        Cursor c = dal.DBRead(Constants.TABLE_DAY);
        while (c.moveToNext()) {
            try {
                dt = ft.parse(c.getString(1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(c.getString(3)!=null){
                notes = c.getString(3);
            }
            else{
                notes = res.getString((R.string.defaultNote));
            }
            try {
                dayType = c.getInt(2);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


            Day day = new Day(c.getInt(0), dt, dayType, notes);
            arrDays.add(day);
        }

        return arrDays;
    }
}
