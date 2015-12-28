package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatusActivity extends Activity {
    BL bl;
    DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        bl=new BL(this);
        TextView textViewCleanCount=(TextView) this.findViewById(R.id.FIELD_cleanCount);
        TextView textViewLastPeriodDate=(TextView) this.findViewById(R.id.FIELD_lastPeriodDate);
        TextView textViewDateMikveh=(TextView) this.findViewById(R.id.FIELD_dateMikveh);
        TextView textViewNextPeriodDate=(TextView) this.findViewById(R.id.FIELD_nextPeriodDate);
        TextView textViewPeriodAvg=(TextView) this.findViewById(R.id.FIELD_periodAvg);
        TextView textViewPeriodLength=(TextView) this.findViewById(R.id.FIELD_periodLength);
        TextView textViewPrishaDate=(TextView) this.findViewById(R.id.FIELD_prishaDate);


        //get the last date with start PeriodDate
        Cursor day = bl.getLastDate(Constants.TABLE_DAY);
        Cursor definition =bl.getDefinitionCursor();

        String lastDateStr=""; // last date of start period
        if (definition.moveToFirst()) {
            textViewPeriodLength.setText( Integer.toString(definition.getInt(1)));
        }

        if (day.moveToFirst()) {
            lastDateStr=day.getString(1);
        }
        textViewLastPeriodDate.setText(lastDateStr);
        //convert date type to string
        //add 28 days to date
        String date = sdf.format(addDaysToDate(28,lastDateStr));
        textViewNextPeriodDate.setText(date);

        textViewPeriodAvg.setText(avgBetweenPeriod());

       // textViewCleanCount();
    }

//    private String textViewCleanCount(){
//        Cursor day= bl.getLastDate();
//        Date today=new Date();
//        sdf.format(today);
//        if(day.moveToFirst()){
//            if( day.getInt(2)==1){//start looking
//              if(day.getInt(2)==1)
//            }
//            else if(day.getInt(2)==2){//end looking
//
//            }
//            else{
//
//            }
//
//        }
//        return "אין ספירה";
//    }
    private String avgBetweenPeriod(){
        boolean average=false;//Is there an average(Use at least 4 months )
        Cursor day=bl.getDateStartLooking();
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
                try {
                    countDate+= countDaysBetweenDates(sdf.parse(dates[dates.length-i-1]), sdf.parse(dates[dates.length-i-2]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Float f = new Float ((float)countDate);
            return (String.valueOf(Math.round(f/3)));
        }
        return "אין מספיק נתונים";
    }

    private long countDaysBetweenDates(Date date1, Date date2){

        long diff = date1.getTime() - date2.getTime();
        return (diff  / 1000 / 60 / 60 / 24);
    }

    private Date addDaysToDate(int numDays, String date){
        Date dateToString;
        Calendar cal=null;
        try {
            dateToString = sdf.parse(date);
            cal = Calendar.getInstance();
            cal.setTime(dateToString);
            cal.add(Calendar.DATE, numDays);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal.getTime();
        //return date;

    }

}
