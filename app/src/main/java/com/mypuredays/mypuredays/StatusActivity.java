package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        String date = sdf.format(Utils.addDaysToDate(28, lastDateStr));
        textViewNextPeriodDate.setText(date);

        textViewPeriodAvg.setText(avgBetweenPeriod());
        textViewCleanCount.setText(textViewCleanCount(definition));
        textViewDateMikveh.setText(textViewDateMikveh(definition));

        Log.e("SUNRISE", Utils.getHebDate());
    }

    private String textViewDateMikveh(Cursor definition){
        Cursor day= bl.getLastDate();
        int periodLength= definition.getInt(4);//PERIOD_LENGTH from definition
        long numDayse;
        Date today=new Date();
        sdf.format(today);
        if(day.moveToFirst()){
            numDayse=countDaysBetweenDates(Utils.StrToDate(day.getString(1)) ,today);//num days between start/end looking until today
            if( day.getInt(2)==1){//start looking

                    if(numDayse<=periodLength+7) {//check if today is not after mikvhe
                        return sdf.format(Utils.addDaysToDate((periodLength + 7), day.getString(1)));
                    }
            }
            else if(day.getInt(2)==2){//end looking
                if (numDayse<=7) {
                    return sdf.format(Utils.addDaysToDate(7, day.getString(1)));
                }
            }
        }
        return "-";
    }
    private String textViewCleanCount(Cursor definition){
        Cursor day= bl.getLastDate();
        int periodLength= definition.getInt(4);//PERIOD_LENGTH from definition
        long numDayse;
        Date today=new Date();
        sdf.format(today);
        if(day.moveToFirst()){
            numDayse=countDaysBetweenDates(Utils.StrToDate(day.getString(1)) ,today);//num days between start/end looking until today
            if( day.getInt(2)==1){//start looking
                if(numDayse<periodLength){//still in period
                    return "-";
                }else if(numDayse>=periodLength){//end perion
                    if(numDayse<=periodLength+7) {//check if today is not after nekiim
                        return String.valueOf(numDayse - periodLength);
                    }
                }
            }
            else if(day.getInt(2)==2){//end looking
                if (numDayse<=7) {
                    return String.valueOf(numDayse);
                }
            }

        }
        return "-";
    }


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
                    countDate+= countDaysBetweenDates(Utils.StrToDate(dates[dates.length - i - 1]), Utils.StrToDate(dates[dates.length - i - 2]));
            }
            Float f = new Float ((float)countDate);
            return (String.valueOf(Math.round(f/3)));
        }
        return "אין מספיק נתונים";
    }

    private long countDaysBetweenDates(Date date1, Date date2){
        long diff;
        if(date1.getTime()>date2.getTime()) {
            diff = date1.getTime() - date2.getTime();
        }else{
            diff = date2.getTime() - date1.getTime();
        }
        return (diff  / 1000 / 60 / 60 / 24);
    }



}
