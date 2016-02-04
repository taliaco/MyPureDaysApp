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
import java.util.Locale;

public class BL {
    DAL dal;
    Context context;

    public BL(Context contextview) {
        context = contextview;
        dal = new DAL(contextview);
    }

    public void populateDB() {
        populateDefinition();
    }

    public void populateDefinition() {
        Definition def = new Definition();

        int regularyInt, prishaDaysInt, countCleanInt, mikveNutificationInt;

        regularyInt = (def.is_regulary()) ? 1 : 0;
        prishaDaysInt = (def.is_prishaDays()) ? 1 : 0;
        countCleanInt = (def.is_countClean()) ? 1 : 0;
        // dailyNotificationInt = (def.is_dailyNotification()) ? 1 : 0;
        mikveNutificationInt = (def.get_mikveNutification()) ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put(Constants.COL_MIN_PERIOD_LENGTH, def.get_minPeriodLengthID());
        values.put(Constants.COL_REGULAR, regularyInt);
        values.put(Constants.COL_PRISHA_DAYS, prishaDaysInt);
        values.put(Constants.COL_PERIOD_LENGTH, def.get_periodLengthID());
        values.put(Constants.COL_COUNT_CLEAN, countCleanInt);
        // values.put(Constants.COL_DAILY_NOTIFICATION, dailyNotificationInt);
        values.put(Constants.COL_CLEAN_NOTIFICATION, def.get_cleanNotification());
        values.put(Constants.COL_MIKVE_NOTIFICATION, mikveNutificationInt);
        values.put(Constants.COL_TYPE_PERIOD, def.get_typeOnaID());
        dal.DBWrite(Constants.TABLE_DEFINITION, values);
    }

    public Definition getDefinition() {
        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        Definition def = new Definition();
        Boolean regularColumn, prishaDaysColumn, countCleanColumn, mikveNutification;

        if (c.moveToFirst()) {
            regularColumn = (c.getInt(2) != 0);
            prishaDaysColumn = (c.getInt(3) != 0);
            countCleanColumn = (c.getInt(5) != 0);
            mikveNutification = (c.getInt(7) != 0);
            def = new Definition(c.getInt(1), regularColumn, prishaDaysColumn, c.getInt(4), countCleanColumn, c.getInt(6), mikveNutification, c.getInt(8));
        }
        return def;
    }

    //save switch selection to definition
    public void setSwitchDefinition(String columnName, boolean switchState) {
        int switchStateInt = (switchState) ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put(columnName, switchStateInt);
        dal.DBUpdate(Constants.TABLE_DEFINITION, values, null,null);
    }
    //save drop down list selection to definition
    public void setSpinnerDefinition(String columnName, int position) {

        ContentValues values = new ContentValues();
        values.put(columnName, position);
        dal.DBUpdate(Constants.TABLE_DEFINITION, values, null,null);

    }

    public Cursor getDefinitionCursor() {
        Cursor c = dal.DBRead(Constants.TABLE_DEFINITION);
        if (c.moveToFirst()) {
            return c;
        }
        return null;
    }


    public void setStartEndLooking(Date date, Constants.DAY_TYPE dayType, Constants.ONA_TYPE ona) {
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {sdf.format(date)};

        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, selection, selectionArgs);
        if (c.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DAY_TYPE, dayType.ordinal());
            values.put(Constants.COL_ONA, ona.ordinal());
            String criteria = Constants.COL_DATE + "=?" ;
            dal.DBUpdate(Constants.TABLE_DAY, values, criteria, new String[] {sdf.format(date)});
        } else {
            String strDate = sdf.format(date);
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DATE, strDate);
            values.put(Constants.COL_DAY_TYPE, dayType.ordinal());
            values.put(Constants.COL_ONA, ona.ordinal());
            dal.DBWrite(Constants.TABLE_DAY, values);
        }
    }
    //return the last date with start looking
    public Cursor getLastStartLooking() {
        String selection = Constants.COL_DAY_TYPE + "=?";
        String[] selectionArgs = {String.valueOf(Constants.DAY_TYPE.START_LOOKING.ordinal())};
        String[] cols = new String[]{Constants._ID, "MAX(" + Constants.COL_DATE + ")", Constants.COL_DAY_TYPE, Constants.COL_NOTES, Constants.COL_ONA};
        return dal.DBReadRow(Constants.TABLE_DAY, cols, selection, selectionArgs);
    }
    //return last date in the db
    public Cursor getLastDate() {
        String[] cols = new String[]{Constants._ID, "MAX(" + Constants.COL_DATE + ")", Constants.COL_DAY_TYPE, Constants.COL_NOTES, Constants.COL_ONA};
        return dal.DBReadRow(Constants.TABLE_DAY, cols, null, null);
    }
    //return the real type of the "currentDay" day.
    public int getTypeOfDate(String startCheckDate, String currentDay) {//return last daytype before the date parameter

        String selection = Constants.COL_DATE + ">? AND " + Constants.COL_DATE + "<=? AND " + Constants.COL_DAY_TYPE + "!=?";
        String[] selectionArgs = {startCheckDate,currentDay, String.valueOf(Constants.DAY_TYPE.DEFAULT.ordinal())};
        String[] cols = new String[]{Constants._ID, "MAX(" + Constants.COL_DATE + ")", Constants.COL_DAY_TYPE, Constants.COL_NOTES, Constants.COL_ONA};
        int dayType = Constants.DAY_TYPE.DEFAULT.ordinal();
        Date[] prishaArr = Utils.getPrishaDateArr(this);
        String nextPeriod = Utils.getNextPeriodDate(this);
        Date dateEndDate = Utils.StrToDate(currentDay);
        Definition def = getDefinition();

        for(int i=0; i<prishaArr.length; i++){
            if (prishaArr[i].equals(dateEndDate)){
                return Constants.DAY_TYPE.PRISHA.ordinal();
            }
        }
        if (nextPeriod.equals(dateEndDate)){
            return Constants.DAY_TYPE.PRISHA.ordinal();
        }

        Day nextDay;

        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, cols, selection, selectionArgs);
        try {
            if (c.moveToFirst()) {
                nextDay = getFirstLooking(Utils.StrToDate(currentDay));
                int periodLength = def.get_periodLength();
                switch (c.getInt(2)) {
                    case 0:
                        dayType = Constants.DAY_TYPE.DEFAULT.ordinal();
                        break;
                    case 1: //start looking
                        if (Utils.addDaysToDate(periodLength, c.getString(1)).before(dateEndDate)) {
                            if (nextDay != null && nextDay.get_dayTypeId() == Constants.DAY_TYPE.END_LOOKING.ordinal()) {
                                dayType = Constants.DAY_TYPE.PERIOD.ordinal();
                            } else if (Utils.addDaysToDate(periodLength + Constants.CLEAR_DAYS_LENGTH, c.getString(1)).after(dateEndDate)) {
                                dayType = Constants.DAY_TYPE.CLEAR_DAY.ordinal();
                            } else {
                                dayType = Constants.DAY_TYPE.DEFAULT.ordinal();
                            }
                        } else {
                            dayType = Constants.DAY_TYPE.PERIOD.ordinal();
                        }
                        break;
                    case 2: //end looking
                        if (Utils.addDaysToDate(periodLength + Constants.CLEAR_DAYS_LENGTH, c.getString(1)).after(dateEndDate)) {
                            dayType = Constants.DAY_TYPE.CLEAR_DAY.ordinal();
                        } else {
                            dayType = Constants.DAY_TYPE.DEFAULT.ordinal();
                        }
                        break;
                    case 5://mikve
                        dayType = Constants.DAY_TYPE.MIKVEH.ordinal();
                        break;
                    default:
                        dayType = Constants.DAY_TYPE.DEFAULT.ordinal();
                        break;
                }
            }
        } finally {
            c.close();
        }
        return dayType;
    }

    public Day getFirstLooking(Date currentDate){
        String selection = Constants.COL_DATE + ">? AND " + Constants.COL_DAY_TYPE + "!=?";
        String[] selectionArgs = {Utils.DateToStr(currentDate), String.valueOf(Constants.DAY_TYPE.DEFAULT.ordinal())};
        String[] cols = new String[]{Constants._ID, "MIN(" + Constants.COL_DATE + ")", Constants.COL_DAY_TYPE, Constants.COL_NOTES, Constants.COL_ONA};
        String firstDate = "";
        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, cols, selection, selectionArgs);
        try {
            if (c.moveToFirst()) {
                firstDate = c.getString(1);
            }
        } finally {
            c.close();
        }
        if (firstDate!=null && !firstDate.equals(""))
            return getDay(firstDate) ;
        return null;
    }
    //return all days with start looking order by date
    public Cursor getDateStartLooking() {
        String selection = Constants.COL_DAY_TYPE + "=?";
        String[] selectionArgs = {String.valueOf(Constants.DAY_TYPE.START_LOOKING.ordinal())};
        String[] cols = new String []{Constants._ID, Constants.COL_DATE +" DESC",Constants.COL_DAY_TYPE ,Constants.COL_NOTES,Constants.COL_ONA};
        return dal.DBReadRow(Constants.TABLE_DAY, cols, selection, selectionArgs);

    }

    public ArrayList<Day> getAllDays() {
        Resources res = context.getResources();
        String notes;
        int dayType = -1;
        int ona;
        Date dt = null;
        ArrayList<Day> arrDays = new ArrayList<>();
        SimpleDateFormat ft = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        Cursor c = dal.DBRead(Constants.TABLE_DAY);
        try {
            while (c.moveToNext()) {
                try {
                    dt = ft.parse(c.getString(1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (c.getString(3) != null) {
                    notes = c.getString(3);
                } else {
                    notes = res.getString((R.string.defaultNote));
                }
                try {
                    dayType = c.getInt(2);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    ona = c.getInt(4);
                } catch (NullPointerException e) {
                    ona = Constants.ONA_TYPE.DEFAULT.ordinal();
                }
                Day day = new Day(dt, dayType, notes, ona);
                arrDays.add(day);
            }
        } finally {
            c.close();
        }
        return arrDays;
    }


    public void saveNote(Date date, String text) {
        String strDate = Utils.DateToStr(date);
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {strDate};

        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, selection, selectionArgs);
        if (c.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_NOTES, text);
            dal.DBUpdate(Constants.TABLE_DAY, values, selection, selectionArgs);
        } else {
            ContentValues values = new ContentValues();
            values.put(Constants.COL_DATE, strDate);
            values.put(Constants.COL_DAY_TYPE, Constants.DAY_TYPE.DEFAULT.ordinal());
            values.put(Constants.COL_NOTES, text);
            dal.DBWrite(Constants.TABLE_DAY, values);
        }

    }

    public int getMaxId(String tableName) {
        String[] cols = new String[]{"MAX(" + Constants._ID + ")"};
        Cursor c = dal.getMaxId(tableName, cols);
        if (c.moveToFirst()) {
            Log.e("MAX ID", String.valueOf(c.getInt(0)));
            return c.getInt(0);
        }
        return -1;
    }

    public Day getDay(String date) {
        Day day = null;
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {date};
        Resources res = context.getResources();
        String notes;
        int ona = Constants.ONA_TYPE.DEFAULT.ordinal();
        int dayType = Constants.DAY_TYPE.DEFAULT.ordinal();
        Date dt = null;
        SimpleDateFormat ft = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, selection, selectionArgs);
        try {
            if (c.moveToFirst()) {
                try {
                    dt = ft.parse(c.getString(1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (c.getString(3) != null) {
                    notes = c.getString(3);
                } else {
                    notes = res.getString((R.string.defaultNote));
                }
                try {
                    dayType = c.getInt(2);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    ona = c.getInt(4);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                day = new Day(dt, dayType, notes, ona);
            }
        } finally {
            c.close();
        }
        return day;
    }
    public void deleteDay(String date) {
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {date};
        dal.DBDeleteItem(Constants.TABLE_DAY, selection, selectionArgs);
    }
    public Boolean dayHaveNote(String date){
        String selection = Constants.COL_DATE + "=?";
        String[] selectionArgs = {date};
        Cursor c = dal.DBReadRow(Constants.TABLE_DAY, selection,selectionArgs);
        if (c.moveToFirst()) {
            if (c.getString(3) != null && !c.getString(3).equals("")) {
                return true;
            }
        }
        return false;
    }
}