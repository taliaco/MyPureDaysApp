package com.mypuredays.mypuredays;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        Log.e("j populateDefinition  ", String.valueOf(def.is_regulary()));
        int regularyInt, prishaDaysInt, countCleanInt, dailyNotificationInt, ovulationNutificationInt;

        regularyInt = (def.is_regulary()) ? 1 : 0;
        prishaDaysInt = (def.is_prishaDays()) ? 1 : 0;
        countCleanInt = (def.is_countClean()) ? 1 : 0;
        dailyNotificationInt = (def.is_dailyNotification()) ? 1 : 0;
        ovulationNutificationInt = (def.get_ovulationNutification()) ? 1 : 0;

        ContentValues values = new ContentValues();
        values.put(Constants.COL_MIN_PERIOD_LENGTH, def.get_minPeriodLength());
        values.put(Constants.COL_REGULAR, regularyInt);
        values.put(Constants.COL_PRISHA_DAYS, prishaDaysInt);
        values.put(Constants.COL_PERIOD_LENGTH, def.get_periodLength());
        values.put(Constants.COL_COUNT_CLEAN, countCleanInt);
        values.put(Constants.COL_DAILY_NOTIFICATION, dailyNotificationInt);
        values.put(Constants.COL_CLEAN_NOTIFICATION, def.get_cleanNotification());
        values.put(Constants.COL_OVULATION_NOTIFICATION, ovulationNutificationInt);

        dal.DBWrite(Constants.TABLE_DEFINITION, values);
    }

    public Definition getDefinition() {
        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        Definition def;
        Boolean regularColumn, prishaDaysColumn, countCleanColumn, dailyNotificationColumn, ovulationNutification;
        if (c.moveToFirst()) {
            regularColumn = (c.getInt(2) != 0);
            prishaDaysColumn = (c.getInt(3) != 0);
            countCleanColumn = (c.getInt(5) != 0);
            dailyNotificationColumn = (c.getInt(6) != 0);
            ovulationNutification = (c.getInt(8) != 0);

            def = new Definition(c.getInt(0), c.getInt(1), regularColumn, prishaDaysColumn, c.getInt(4), countCleanColumn, dailyNotificationColumn, c.getInt(7), ovulationNutification);
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

    public void setSwitchDefinition(String columnName, boolean switchState){


        int switchStateInt = (switchState) ? 1 : 0;

        ContentValues values = new ContentValues();
        values.put(columnName, switchStateInt);

        dal.DBUpdate(Constants.TABLE_DEFINITION, values, null);
    }



    public void setStartEndLooking(Date date, Constants.DAY_TYPE dayType) {
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {sdf.format(date)};

        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, selection, selectionArgs);
        if (c.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DAY_TYPE, Utils.getDayTypeIDByName(dayType));
            String criteria = Constants.COL_DATE + "=" + sdf.format(date);

            dal.DBUpdate(Constants.TABLE_DAY, values, criteria);
        } else {
            String strDate = sdf.format(date);
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DATE, strDate);
            values.put(Constants.COL_DAY_TYPE, Utils.getDayTypeIDByName(dayType));
            dal.DBWrite(Constants.TABLE_DAY, values);
        }
    }



    public int getMaxId(String tableName) {
        String[] cols = new String []{"MAX(" + Constants._ID + ")"};
        Cursor c = dal.getMaxId(tableName, cols);
        if (c.moveToFirst()) {
            Log.e("MAX ID", String.valueOf(c.getInt(0)));
            return c.getInt(0);
        }
        return -1;
    }

    public Cursor getLastDate(String tableDay) {

        String selection = Constants.COL_DAY_TYPE + "=?";
        String[] selectionArgs = {Constants.DAY_TYPE.START_LOOKIND.toString()};

        String[] cols = new String []{Constants._ID,"MAX("+Constants.COL_DATE+")",Constants.COL_DAY_TYPE ,Constants.COL_NOTES};
        Cursor c = dal.DBReadRow(tableDay, cols, selection, selectionArgs);
        if (c.moveToFirst()) {

            return c;
        }
        return null;

    }

    public ArrayList<Day> getAllDays() {
        Resources res = context.getResources();
        String notes="";
        int dayType = -1;
        Date dt = null;
        ArrayList<Day> arrDays = new ArrayList<>();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");


        Cursor c = dal.DBRead(Constants.TABLE_DAY);
        while (c.moveToNext()) {
            try {
                dt = ft.parse(c.getString(1).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(c.getString(3)!=null){
                notes = c.getString(3);
            }
            else{
                notes = res.getString((R.string.defaultNote));
            }


            Day day = new Day(c.getInt(0), dt, dayType, notes);
            arrDays.add(day);
        }

        return arrDays;
    }
}
