package com.mypuredays.mypuredays;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import net.sourceforge.zmanim.ComplexZmanimCalendar;
import net.sourceforge.zmanim.hebrewcalendar.HebrewDateFormatter;
import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;
import net.sourceforge.zmanim.hebrewcalendar.JewishDate;
import net.sourceforge.zmanim.util.GeoLocation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
    public void printAllDays(BL bl){
        ArrayList<Day> d=new ArrayList<Day>();
        d=bl.getAllDays();
        for(int i=0; i<d.size(); i++){
            Log.e("date:  ", d.get(i).get_date() + "ona type:  " + d.get(i).get_ona());
        }
    }
    public static String getHebDate(){
        String locationName = "Lakewood, NJ";
        double latitude = 40.096; //Lakewood, NJ
        double longitude = -74.222; //Lakewood, NJ
        double elevation = 0; //optional elevation
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
        GeoLocation location = new GeoLocation(locationName, latitude, longitude, elevation, timeZone);
        ComplexZmanimCalendar czc = new ComplexZmanimCalendar(location);


        czc.getCalendar().set(Calendar.MONTH, Calendar.JANUARY);
        czc.getCalendar().set(Calendar.DAY_OF_MONTH, 24);
        czc.getCalendar().set(Calendar.YEAR, 2016);
        Date sunrise = czc.getSunrise();

        HebrewDateFormatter hdf = new HebrewDateFormatter();

        JewishCalendar jd = new JewishCalendar(5775, JewishDate.ADAR, 23);
        System.out.println(hdf.formatParsha(jd));
        hdf.setHebrewFormat(true);
        System.out.println(hdf.formatParsha(jd));
        JewishDate Jdate = new JewishDate(new Date());

        return String.valueOf(Jdate.getJewishDayOfMonth()) + " " + Jdate.getJewishMonth();
//return "kakakka";

    }
}
