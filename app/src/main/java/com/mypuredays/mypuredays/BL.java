package com.mypuredays.mypuredays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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
    public BL(Context context) {
        dal = new DAL(context);
    }

    public void populateDB(){
        populateDefinition();

        //ClearDayType clrDayType = new ClearDayType()
    }
    public void populateDefinition(){
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

    public Definition getDefinition(){
        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        Definition def;
        Boolean regularColumn, prishaDaysColumn, countCleanColumn, dailyNotificationColumn;
        if(c.moveToFirst()) {
            regularColumn = (c.getInt(1) != 0);
            prishaDaysColumn = (c.getInt(2) != 0);
            countCleanColumn = (c.getInt(7) != 0);
            dailyNotificationColumn = (c.getInt(8) != 0);
            def = new Definition(c.getInt(0), c.getInt(1), regularColumn, prishaDaysColumn, c.getInt(4), c.getInt(5), c.getInt(6), countCleanColumn, dailyNotificationColumn);
            return def;
        }
        return null;
    }
    public Cursor getDefinitionCursor(){
        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        if(c.moveToFirst()) {
            return c;
        }
        return null;
    }

    public void setStartLooking(Date date){
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {sdf.format(date)};

        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, selection, selectionArgs);
        if(c.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DAY_TYPE, Utils.getDayTypeIDByName(Constants.DAY_TYPE.START_LOOKIND));
            String criteria = Constants.COL_DATE + "=" + sdf.format(date);

            dal.DBUpdate(Constants.TABLE_DAY,values,criteria);
        }
        else{

        }
    }
    public boolean setEndLooking(){

        return true;
    }


    public int getMaxIdDay(){
        Cursor c =  dal.getMaxId(Constants.TABLE_DAY);
        return c.getInt(0);

    }

    public ArrayList<Day> ReadAllDays() {

        ArrayList<Day> arrDays = new ArrayList<>();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;

        Cursor c = dal.DBRead(Constants.TABLE_DAY);
        while (c.moveToNext()) {
            try {
                dt = ft.parse(c.getString(1).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Day day=new Day(c.getInt(0),dt ,c.getInt(2),c.getString(3));
           arrDays.add(day);
        }

        return arrDays;
    }
}
