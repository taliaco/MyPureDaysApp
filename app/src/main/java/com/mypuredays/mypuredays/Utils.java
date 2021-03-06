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

    public static Date addDaysToDate(int numDays, String dateStr){
        Date date=new Date();
        if(dateStr!=null && !dateStr.equals("")) {
             date = StrToDate(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, (numDays-1));
            return cal.getTime();
        }
        else return null;
        //return date;
    }
    public static Date[] getPrishaDate(BL bl){
        String  lastDateStr="";
        int typeOna;
        Cursor day = bl.getLastStartLooking();
        try{
        if (day==null){
            return null;
        }
        if (day.moveToFirst() && day.getString(1) != null) {
            if (day==null){
                return null;
            }
            lastDateStr=day.getString(1);
            typeOna=day.getInt(4);//3
        }else {//EMPTY DB
            typeOna=-1;
        }
        } finally {
            day.close();
        }
        Definition def;
        def=bl.getDefinition();
        Date[] arrDate=new Date[3];//3 dates that can be prisha date. 1-same Jdate 2-the difference days 3-in 30 day's after last period
        int avg; //avg Between Period
        arrDate[0]=null;
        arrDate[1]=null;
        arrDate[2]=null;
        if(def.is_regulary()==true) {// if period sadir then prisha is AVG
            avg=avgBetweenPeriod(bl);
            if(avg!=-1){
                arrDate[0] = Utils.addDaysToDate(avg,lastDateStr);
            }else{
                arrDate[0] = Utils.addDaysToDate(28,lastDateStr);
            }
            return  arrDate;
        }
        //period not sadir----------
        arrDate[0]=getJdateNextMonth(lastDateStr);
        arrDate[1]= differenceDays(lastDateStr, bl);
        arrDate[2]=Utils.addDaysToDate(30, lastDateStr);
        if (def.get_typeOna()==Constants.ONA_TYPE.NIGHT.ordinal() ||  typeOna==3){//ONA == NIGHT
            arrDate[0]=addDaysToDate(2, Utils.DateToStr(arrDate[0]));
            arrDate[1]=addDaysToDate(2, Utils.DateToStr(arrDate[1]));
            arrDate[2]=addDaysToDate(2, Utils.DateToStr(arrDate[2]));

        }
        if(arrDate[0]!=null && arrDate[1]!=null && DateToStr(arrDate[0]).compareTo(DateToStr(arrDate[1]))==0){
            arrDate[1]=null;
        }
        if(arrDate[0]!=null && arrDate[2]!=null &&  DateToStr(arrDate[0]).compareTo(DateToStr(arrDate[2]))==0){
            arrDate[2]=null;
        }
        if(arrDate[2]!=null && arrDate[1]!=null &&  DateToStr(arrDate[1]).compareTo(DateToStr(arrDate[2]))==0){
            arrDate[2]=null;
        }

        return arrDate;

    }

//    public static Date[] getPrishaDate(BL bl) {
//
//        Cursor day = bl.getLastStartLooking();
//        if ( day.moveToFirst() && day.getString(1) != null) {
//            return getPrishaDate(day.getString(1), bl);
//        }
//        return null;
//    }
/*   count days between last period and corrent period.
     add the number of the day to the last begin period
 */
    public static Date differenceDays(String lastDateStr, BL bl){
        Cursor day=bl.getDateStartLooking();
        try{
        String[] dates=new String[day.getCount()];
        int i=0;//indwx of arry days
            while (day.moveToNext()) {
                dates[i]=day.getString(1);
                i++;
             }
        if(dates.length<2){
            return null;
        }else {
           Day pDate= bl.getPrevType(Utils.DateToStr(new Date()));
            if(pDate.get_dayTypeId()==Constants.DAY_TYPE.END_LOOKING.ordinal()){
                pDate=bl.getPprevType(Utils.DateToStr(pDate.get_date()));
            }
            Day prevPDate=bl.getPprevType(Utils.DateToStr(pDate.get_date()));
            if(prevPDate.get_dayTypeId()==Constants.DAY_TYPE.END_LOOKING.ordinal()){
                prevPDate=bl.getPprevType(Utils.DateToStr(prevPDate.get_date()));
            }
            long countDate = countDaysBetweenDates(prevPDate.get_date(), pDate.get_date());
            int numDays = Math.round(countDate);
            return addDaysToDate(numDays+1, Utils.DateToStr( pDate.get_date()));
        }
        } finally {
            day.close();
        }
    }

    public static Date getJdateNextMonth(String lastDateStr) {//return the same day in the next month
        int[] arrJDate;
        int[] arrJDateTemp;
        Date dt;
        if(lastDateStr==null){
            return null;
        }

        arrJDate = Utils.getHebDate(Utils.StrToDate(lastDateStr));//get int array {day, month, year} of last period date (jewish date)
        if(arrJDate==null){
            return null;
        }
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
            if(strDate!=null)
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
    public static String StrToDateDisplay(String strDate){
        return DateToStrDisplay(StrToDate(strDate));
    }
    public void printAllDays(BL bl){
        ArrayList<Day> d = bl.getAllDays();
        for(int i=0; i<d.size(); i++){
            Log.e("date:  ", d.get(i).get_date() + "ona type:  " + d.get(i).get_ona());
        }
    }
    public static int[] getHebDate(Date date){
        //hebrew date array format {day, month, year}
        if (date==null){
            return null;
        }
        int[] arrDate =new int[3];
        JewishDate Jdate = new JewishDate(date);
        arrDate[0]=Jdate.getJewishDayOfMonth();
        arrDate[1]=Jdate.getJewishMonth();
        arrDate[2]=Jdate.getJewishYear();
        return arrDate;
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

    public static int avgBetweenPeriod(BL bl){

        boolean average=false;//Is there an average(Use at least 4 months )
        Cursor day=bl.getDateStartLooking();
        try{
        String[] dates=new String[day.getCount()];
        int i=0;//indwx of varr days
        long countDate=0; //count the days between date for AVG
        if(day.getCount()<4){//there average
            average=false;
        }
        else {
            while (day.moveToNext()) {
                dates[i]=day.getString(1);
                i++;
            }
            average=true;
        }
        if (average==true){
            for (i=0; i< 3; i++){
                countDate+=Utils.countDaysBetweenDates(Utils.StrToDate(dates[dates.length - i - 1]), Utils.StrToDate(dates[dates.length - i - 2]));
            }
            Float f = new Float ((float)countDate);
            return (Math.round(f / 3));
        }
        } finally {
            day.close();
        }
        return -1;

    }
    public static Date[] getPrishaDateArr(BL bl) {
        Cursor day = bl.getLastStartLooking();
        String lastDateStr = ""; // last date of start period
        try {
            day.moveToFirst();

            Definition def = bl.getDefinition();
            if (def.is_prishaDays()) {
                lastDateStr = day.getString(1);
                return Utils.getPrishaDate( bl);//return 3 optional prisha dates (2,3 may be null)

            }

        } finally {
            day.close();
        }
        return null;
    }

    public static String getNextPeriodDate(BL bl) {
        Cursor day = bl.getLastStartLooking();
        String lastDateStr = ""; // last date of start period
        String nextPeriod = "";
        try {
            day.moveToFirst();
            lastDateStr = day.getString(1);
            if(lastDateStr==null){
                return null;
            }
            int avgBetweenPeriodStr = Utils.avgBetweenPeriod(bl);
            if (avgBetweenPeriodStr == -1) {
                nextPeriod =  Utils.DateToStr(Utils.addDaysToDate(28, lastDateStr));
            } else {
                nextPeriod =  Utils.DateToStr(Utils.addDaysToDate(Utils.avgBetweenPeriod(bl), lastDateStr));
            }
        } finally {
            day.close();
        }
        return nextPeriod;
    }

    public static int getDayType(BL bl, String dateEnd) {
        int dayType;
        Day day = bl.getDay(dateEnd);
        if (day != null && day.get_dayTypeId() != Constants.DAY_TYPE.DEFAULT.ordinal()) {
            return day.get_dayTypeId();
        }
        dayType = bl.getTypeOfDate("1980-01-01", dateEnd);

        if (dayType > 0) {

            return dayType;
        }

        return Constants.DAY_TYPE.DEFAULT.ordinal();
    }



   
}
