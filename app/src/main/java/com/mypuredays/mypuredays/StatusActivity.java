package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class StatusActivity extends Activity {
    BL bl;

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
        if (day.moveToFirst()) {
            Log.e("GGGGGGGGGG", day.getString(1));
            Log.e("GGGGGGGGGG", String.valueOf(day.getInt(2)));
            textViewLastPeriodDate.setText(day.getString(1));
        }


    }

}
