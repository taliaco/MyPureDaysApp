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
        if (day.moveToFirst()) {
            lastDateStr=day.getString(1);
        }

        if (definition.moveToFirst()) {
            textViewPeriodLength.setText( Integer.toString(definition.getInt(1)));
        }

        textViewLastPeriodDate.setText(lastDateStr);
        //convert date type to string
        String date = sdf.format(addDaysToDate(28,lastDateStr));
        textViewNextPeriodDate.setText(date);


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
