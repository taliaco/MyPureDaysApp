package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
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
        Cursor day = bl.getLastStartLooking();
        Definition definition =bl.getDefinition();
        String InsufficientData="אין מספיק נתונים";// Not enough data

        String lastDateStr=""; // last date of start period
            textViewPeriodLength.setText(Integer.toString(definition.get_periodLength()));


        if ( day.moveToFirst() && day.getString(1) != null) {
            lastDateStr=day.getString(1);
            textViewLastPeriodDate.setText(Utils.StrToDateDisplay(lastDateStr));
            Date[] arrDate =Utils.getPrishaDate(lastDateStr, bl);//return 3 optional prisha dates (2,3 may be null)
            //if arrDate[2 or 3] = null
            String prishaDateStr=Utils.StrToDateDisplay(Utils.DateToStr(arrDate[0]))+ '\n';
            if(arrDate[1]!=null){
                prishaDateStr+=Utils.StrToDateDisplay(Utils.DateToStr(arrDate[1]))+ '\n';
            }
            if(arrDate[2]!=null){
                prishaDateStr+=Utils.StrToDateDisplay(Utils.DateToStr(arrDate[2]))+ '\n';
            }
            textViewPrishaDate.setText(prishaDateStr);

            String nextPeriodDateStr;
            //calculate avg Between Period
            String avgBetweenPeriodStr= String.valueOf(Utils.avgBetweenPeriod(bl));
            if(avgBetweenPeriodStr.equals("-1")){
                avgBetweenPeriodStr="אין מספיק נתונים";
                nextPeriodDateStr = sdf.format(Utils.addDaysToDate(28, lastDateStr));
            }
            else{
                nextPeriodDateStr = sdf.format(Utils.addDaysToDate(Utils.avgBetweenPeriod(bl), lastDateStr));
            }
            textViewPeriodAvg.setText(avgBetweenPeriodStr);
            //END avg Between Period
            textViewNextPeriodDate.setText(Utils.StrToDateDisplay(nextPeriodDateStr));
            textViewCleanCount.setText(setTextViewCleanCount(definition));
            textViewDateMikveh.setText(setTextViewDateMikveh(definition));
        }
        else {
            textViewNextPeriodDate.setText(InsufficientData);
            textViewDateMikveh.setText(InsufficientData);
            textViewPeriodAvg.setText(InsufficientData);
            textViewPrishaDate.setText(InsufficientData);
            textViewLastPeriodDate.setText(InsufficientData);

        }


    }


    private String setTextViewDateMikveh(Definition definition){
        Cursor day= bl.getLastDate();
        int periodLength= definition.get_periodLength();//PERIOD_LENGTH from definition
        long numDayse;
        Date today=new Date();
        sdf.format(today);
        if(day.moveToFirst()){
            numDayse=Utils.countDaysBetweenDates(Utils.StrToDate(day.getString(1)), today);//num days between start/end looking until today
            if( day.getInt(2)==1){//start looking

                    if(numDayse<=periodLength+7) {//check if today is not after mikvhe
                        return Utils.StrToDateDisplay(sdf.format(Utils.addDaysToDate((periodLength + 7), day.getString(1))));
                    }
            }
            else if(day.getInt(2)==2){//end looking
                if (numDayse<=7) {
                    return Utils.StrToDateDisplay(sdf.format(Utils.addDaysToDate(7, day.getString(1))));
                }
            }
        }
        return "-";
    }
    private String setTextViewCleanCount(Definition definition){
        Cursor day= bl.getLastDate();
        int periodLength= definition.get_periodLength();//PERIOD_LENGTH from definition
        long numDayse;
        Date today=new Date();
        sdf.format(today);
        if(day.moveToFirst()){
            numDayse=Utils.countDaysBetweenDates(Utils.StrToDate(day.getString(1)), today);//num days between start/end looking until today
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







}
