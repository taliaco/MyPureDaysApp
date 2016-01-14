package com.mypuredays.mypuredays;

import android.content.Context;
import android.content.res.Resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Talia on 02/12/2015.
 */
public class Utils {
    public static String getDefName(String columnName, Context context){
        Resources res = context.getResources();
        switch (columnName){
            case Constants.COL_MIN_PERIOD_LENGTH:
                return res.getString(R.string.minPeriodLengthColumn);
            case Constants.COL_REGULAR:
                return res.getString((R.string.regularColumn));
            case Constants.COL_PRISHA_DAYS:
                return res.getString((R.string.prishaDaysColumn));
            case Constants.COL_PERIOD_LENGTH:
                return res.getString((R.string.periodLengthColumn));
            case Constants.COL_MIKVE_NOTIFICATION:
                return res.getString((R.string.mikveNotificationColumn));
            case Constants.COL_CLEAN_NOTIFICATION:
                return res.getString((R.string.cleanNotificationColumn));
            case Constants.COL_COUNT_CLEAN:
                return res.getString((R.string.countCleanColumn));
            case Constants.COL_TYPE_PERIOD:
                return res.getString((R.string.typePeriodColumn));
            default:
                return "";
        }
    }

    public static Constants.DEF_TYPE getDefType(String columnName){
        switch (columnName){
            case Constants.COL_MIN_PERIOD_LENGTH:
                return Constants.DEF_TYPE.INTEGER;
            case Constants.COL_REGULAR:
                return Constants.DEF_TYPE.BOOLEAN;
            case Constants.COL_PRISHA_DAYS:
                return Constants.DEF_TYPE.BOOLEAN;
            case Constants.COL_PERIOD_LENGTH:
                return Constants.DEF_TYPE.INTEGER;
            case Constants.COL_MIKVE_NOTIFICATION:
                return Constants.DEF_TYPE.BOOLEAN;
            case Constants.COL_CLEAN_NOTIFICATION:
                return Constants.DEF_TYPE.BOOLEAN;
            case Constants.COL_COUNT_CLEAN:
                return Constants.DEF_TYPE.BOOLEAN;
            case Constants.COL_DAILY_NOTIFICATION:
                return Constants.DEF_TYPE.BOOLEAN;
            default:
                return Constants.DEF_TYPE.STRING;
        }
    }
    public static String[] getColumnsArray(String tableName){
        switch (tableName){
            case Constants.TABLE_DAY:
                return Constants.TABLE_DAY_COLUMNS;
            case Constants.TABLE_DAY_TYPE:
                return Constants.TABLE_DAY_TYPE_COLUMNS;
            case Constants.TABLE_DEFINITION:
                return Constants.TABLE_DEFINITION_COLUMNS;
            case Constants.TABLE_CLEAR_DAY_TYPE:
                return Constants.TABLE_CLEAR_DAY_TYPE_COLUMNS;
        }
        return null;
    }

    public static int getDayTypeIDByName(Constants.DAY_TYPE dayType){
        return 0;
    }
    public static Date addDaysToDate(int numDays, String date){
        Date dateToString;
        Calendar cal=null;
        dateToString = StrToDate(date);
        cal = Calendar.getInstance();
        cal.setTime(dateToString);
        cal.add(Calendar.DATE, numDays);

        return cal.getTime();
        //return date;

    }
    public static Date StrToDate(String strDate){
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String DateToStr(Date strDate){
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        return sdf.format(strDate);
    }
}
