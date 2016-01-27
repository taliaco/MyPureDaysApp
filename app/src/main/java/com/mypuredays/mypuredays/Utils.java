package com.mypuredays.mypuredays;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;

import net.sourceforge.zmanim.hebrewcalendar.JewishDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static String getPrishaDate(String lastDateStr, BL bl){

        /*
        CHECK IF THE USER KEEP PRISHA DAY
        ----CHECK IF THE PERIOD SADIR
         */



        Date[] arrDate=new Date[3];//3 dates that can be prisha date. 1-same Jdate 2-the difference days 3-in 30 day's after last period
        String strPrishaDates="";
        arrDate[0]=getJdateNextMonth(lastDateStr);
        arrDate[1]=differenceDayse(lastDateStr, bl);
        arrDate[2]=Utils.addDaysToDate(30, lastDateStr);

        if(arrDate[0].compareTo(arrDate[1])>0){
            strPrishaDates+=DateToStr(arrDate[0])+" "+ DateToStr(arrDate[1]);
            if(arrDate[2].compareTo(arrDate[0])>0 && arrDate[2].compareTo(arrDate[1])>0){
                strPrishaDates+=DateToStr(arrDate[2]);
            }
        }else{
            strPrishaDates+=DateToStr(arrDate[0]);
            if(arrDate[2].compareTo(arrDate[0])>0){
                strPrishaDates+=DateToStr(arrDate[2])+"";
            }
        }


        return strPrishaDates;
    }
/*   count days between last period and corrent period.
     add the number of the day to the last begin period
 */
    public static Date differenceDayse(String lastDateStr ,BL bl){
        Cursor day=bl.getDateStartLooking();
        String[] dates=new String[day.getCount()];
        int i=0;//indwx of arry days
            while (day.moveToNext()) {
                dates[i]=day.getString(1);
                i++;
             }
        long countDate = countDaysBetweenDates(Utils.StrToDate(dates[dates.length- 1]), Utils.StrToDate(dates[dates.length- 2]));
        int numDays=(int)(Math.round(countDate));

        return addDaysToDate(numDays ,lastDateStr);
    }

    public static Date getJdateNextMonth(String lastDateStr) {//return the same day in the next month
        int[] arrJDate = new int[3];
        int[] arrJDateTemp = new int[3];
        Date dt =new Date();
        arrJDate = Utils.getHebDate(Utils.StrToDate(lastDateStr));//get int array {day, month, year} of last period date (jewish date)
        for (int i = 0; i < 3; i++) {
            dt=Utils.addDaysToDate(30 + i, lastDateStr);
            arrJDateTemp = Utils.getHebDate(dt);//get date +30+i days
            if (arrJDate[0] == arrJDateTemp[0]) {//same day in next month
                return dt;
            }else {
                dt=Utils.addDaysToDate(30 - i, lastDateStr);
                arrJDateTemp = Utils.getHebDate(dt);//get date +30+i days
                if (arrJDate[0] == arrJDateTemp[0]) {//same day in next month
                    return dt;
            }
        }
    }
        return new Date();
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
    public static String DateToStrDisplay(Date strDate){
        DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_DISPLAY, Locale.US);
        return sdf.format(strDate);
    }
    public void printAllDays(BL bl){
        ArrayList<Day> d=new ArrayList<Day>();
        d=bl.getAllDays();
        for(int i=0; i<d.size(); i++){
            Log.e("date:  ", d.get(i).get_date() + "ona type:  " + d.get(i).get_ona());
        }
    }
    public static int[] getHebDate(Date date){
        //hebrew date array format {day, month, year}
        int[] arrDate =new int[3];
        JewishDate Jdate = new JewishDate(date);
        arrDate[0]=Jdate.getJewishDayOfMonth();
        arrDate[1]=Jdate.getJewishMonth();
        arrDate[2]=Jdate.getJewishYear();
        return arrDate;
       // return String.valueOf(Jdate.getJewishDayOfMonth()) + " " + Jdate.getJewishMonth()+" "+Jdate.getJewishYear();

    }
    public static long countDaysBetweenDates(Date date1, Date date2){
        long diff;
        if(date1.getTime()>date2.getTime()) {
            diff = date1.getTime() - date2.getTime();
        }else{
            diff = date2.getTime() - date1.getTime();
        }
        return (diff  / 1000 / 60 / 60 / 24);
    }


}
