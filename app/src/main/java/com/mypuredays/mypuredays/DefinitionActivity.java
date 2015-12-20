package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
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

    private static final String[]paths = {"4 ימים", "5 ימים"};
    private static final String[]paths1 = {"3 ימים","4 ימים", "5 ימים", "6 ימים", "7 ימים"};
    private static final String[]paths2 = {"ראשון ואחרון ", "כל יום ", "פעמיים ביום"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        bl = new BL(this);
        minPeriodLengthColumn= (TextView)findViewById(R.id.item_name1);
        regularColumn= (TextView)findViewById(R.id.item_name2);
        prishaDaysColumn= (TextView)findViewById(R.id.item_name3);
        periodLengthColumn= (TextView)findViewById(R.id.item_name4);
        countCleanColumn= (TextView)findViewById(R.id.item_name5);
        dailyNotificationColumn= (TextView)findViewById(R.id.item_name6);
        cleanNotificationColumn= (TextView)findViewById(R.id.item_name7);
        ovulationNotificationColumn= (TextView)findViewById(R.id.item_name8);

        switch_period_constant = (Switch)findViewById(R.id.switch_period_constant);
        switch_prisha_day = (Switch)findViewById(R.id.switch_prisha_day);
        switch_counter_pure_day = (Switch)findViewById(R.id.switch_counter_pure_day);
        switch_pellet_reminder_day = (Switch)findViewById(R.id.switch_pellet_reminder_day);
        switch_reminder_ovulation_day = (Switch)findViewById(R.id.switch_reminder_ovulation_day);

        spinner_day_period_min = (Spinner)findViewById(R.id.spinner_day_period_min);
        spinner_period_during = (Spinner)findViewById(R.id.spinner_period_during);
        spinner_reminder_pure_day = (Spinner)findViewById(R.id.spinner_reminder_pure_day);


        ArrayAdapter<String> adapter;


        Cursor c = bl.getDefinitionCursor();
        Definition d = bl.getDefinition();

        for(int i=1; i<c.getColumnCount();i++){

            switch (i){

                case 1: minPeriodLengthColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<String>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item,paths);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_day_period_min.setAdapter(adapter);


                break;
                case 2: regularColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_period_constant.setChecked(d.is_regulary());
                    Log.e("jjjjjj period  ", String.valueOf(d.is_regulary()));
                    break;
                case 3: prishaDaysColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_prisha_day.setChecked(d.is_prishaDays());//bl.getDefinitionSwitchState(c.getColumnName(i)));
                    break;
                case 4: periodLengthColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<String>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item,paths1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_period_during.setAdapter(adapter);
                    break;
                case 5: countCleanColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_counter_pure_day.setChecked(d.is_countClean());
                    break;
                case 6: dailyNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_pellet_reminder_day.setChecked(d.is_dailyNotification());
                    break;
                case 7: cleanNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<String>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item,paths2);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_reminder_pure_day.setAdapter(adapter);
                    break;
                case 8: ovulationNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_reminder_ovulation_day.setChecked(d.get_ovulationNutification());
                    break;
                default:
                    break;

            }//switch end

        }//for end


        spinner_day_period_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Log.e("onItemSelected", "onItemSelected");

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("onNothingSelected", "onNothingSelected");

            }
        });

        //set all switch listener
        switch_period_constant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                bl.setSwitchDefinition(Constants.COL_REGULAR, isChecked);
            }
        });

        switch_prisha_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                bl.setSwitchDefinition(Constants.COL_PRISHA_DAYS, isChecked);
            }
        });

        switch_counter_pure_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                bl.setSwitchDefinition(Constants.COL_COUNT_CLEAN, isChecked);
            }
        });

        switch_pellet_reminder_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                bl.setSwitchDefinition(Constants.COL_OVULATION_NOTIFICATION, isChecked);
            }
        });

        switch_reminder_ovulation_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
