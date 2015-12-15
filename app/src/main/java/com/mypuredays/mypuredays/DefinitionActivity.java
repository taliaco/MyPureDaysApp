package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DefinitionActivity extends Activity {


    private TextView minPeriodLengthColumn,regularColumn,prishaDaysColumn,periodLengthColumn,ovulationNotificationColumn,
            cleanNotificationColumn,countCleanColumn,dailyNotificationColumn;

    private Switch switch_period_constant, switch_prisha_day, switch_counter_pure_day,switch_pellet_reminder_day,
            switch_reminder_ovulation_day;

    private Spinner spinner_day_period_min,spinner_period_during, spinner_reminder_pure_day;

    private BL bl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        bl = new BL(this);
        minPeriodLengthColumn= (TextView)findViewById(R.id.item_name1);
        regularColumn= (TextView)findViewById(R.id.item_name2);
        prishaDaysColumn= (TextView)findViewById(R.id.item_name3);
        periodLengthColumn= (TextView)findViewById(R.id.item_name4);
        ovulationNotificationColumn= (TextView)findViewById(R.id.item_name5);
        cleanNotificationColumn= (TextView)findViewById(R.id.item_name6);
        countCleanColumn= (TextView)findViewById(R.id.item_name7);
        dailyNotificationColumn= (TextView)findViewById(R.id.item_name8);

        Cursor c = bl.getDefinitionCursor();

        for(int i=1; i<c.getColumnCount();i++){

            switch (i){

                case 1: minPeriodLengthColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;
                case 2: regularColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;
                case 3: prishaDaysColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;
                case 4: periodLengthColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;
                case 5: ovulationNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;
                case 6: cleanNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;
                case 7: countCleanColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;
                case 8: dailyNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    break;


            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
