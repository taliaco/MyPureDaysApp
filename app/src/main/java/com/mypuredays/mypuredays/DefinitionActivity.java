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
            cleanNotificationColumn,countCleanColumn,dailyNotificationColumn, typePeriodColumn;

    private Switch switch_period_constant, switch_prisha_day, switch_counter_pure_day,switch_pellet_reminder_day,
            switch_reminder_ovulation_day;

    private Spinner spinner_day_period_min,spinner_period_during, spinner_reminder_pure_day, spinner_type_period;

    private BL bl;

    private static final String[]paths = {"4 ימים", "5 ימים"};
    private static final String[]paths1 = {"3 ימים","4 ימים", "5 ימים", "6 ימים", "7 ימים"};
    private static final String[]paths2 = {"ראשון ואחרון ", "כל יום ", "פעמיים ביום"};
    private static final String[]paths3 = {"יום", "לילה", "לא רלוונטי"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        bl = new BL(this);

        minPeriodLengthColumn= (TextView)findViewById(R.id.item_name_day_period_min);
        regularColumn= (TextView)findViewById(R.id.item_name_period_constant);
        prishaDaysColumn= (TextView)findViewById(R.id.item_name_prisha_day);
        periodLengthColumn= (TextView)findViewById(R.id.item_name_period_during);
        countCleanColumn= (TextView)findViewById(R.id.item_name_counter_pure_day);
        dailyNotificationColumn= (TextView)findViewById(R.id.item_name_pellet_reminder_day);
        cleanNotificationColumn= (TextView)findViewById(R.id.item_name_reminder_pure_day);
        ovulationNotificationColumn= (TextView)findViewById(R.id.item_name_reminder_ovulation_day);
        typePeriodColumn= (TextView)findViewById(R.id.item_name_type_period);

        switch_period_constant = (Switch)findViewById(R.id.switch_period_constant);
        switch_prisha_day = (Switch)findViewById(R.id.switch_prisha_day);
        switch_counter_pure_day = (Switch)findViewById(R.id.switch_counter_pure_day);
        switch_pellet_reminder_day = (Switch)findViewById(R.id.switch_pellet_reminder_day);
        switch_reminder_ovulation_day = (Switch)findViewById(R.id.switch_reminder_ovulation_day);

        spinner_day_period_min = (Spinner)findViewById(R.id.spinner_day_period_min);
        spinner_period_during = (Spinner)findViewById(R.id.spinner_period_during);
        spinner_reminder_pure_day = (Spinner)findViewById(R.id.spinner_reminder_pure_day);
        spinner_type_period = (Spinner)findViewById(R.id.spinner_type_period);

        ArrayAdapter<String> adapter;

        Cursor c = bl.getDefinitionCursor();
        Definition d = bl.getDefinition();

        //Log.e("getDefinition","  "+d.get_minPeriodLength());

        for(int i=1; i<c.getColumnCount();i++){

            switch (i){

                case 1: minPeriodLengthColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<String>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item,paths);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_day_period_min.setAdapter(adapter);
                    spinner_day_period_min.setSelection(d.get_minPeriodLength());


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
                    spinner_period_during.setSelection(d.get_periodLength());

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
                    spinner_reminder_pure_day.setSelection(d.get_cleanNotification());

                    break;
                case 8: ovulationNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_reminder_ovulation_day.setChecked(d.get_ovulationNutification());
                    break;
                case 9: typePeriodColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<String>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item,paths3);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_type_period.setAdapter(adapter);
                    spinner_type_period.setSelection(d.get_typePeriod());

                    break;
                default:
                    break;

            }//switch end

        }//for end



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

        //
        spinner_day_period_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
               // String pres_doctor = spinner_day_period_min.getSelectedItem().toString();
               // int index_item = position;

                bl.setSpinnerDefinition(Constants.COL_MIN_PERIOD_LENGTH, position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("onNothingSelected", "onNothingSelected");
            }
        });

        spinner_period_during.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // String pres_doctor = spinner_day_period_min.getSelectedItem().toString();
                // int index_item = position;

                bl.setSpinnerDefinition(Constants.COL_PERIOD_LENGTH, position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("onNothingSelected", "onNothingSelected");
            }
        });

        spinner_reminder_pure_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // String pres_doctor = spinner_day_period_min.getSelectedItem().toString();
                // int index_item = position;

                bl.setSpinnerDefinition(Constants.COL_CLEAN_NOTIFICATION, position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("onNothingSelected", "onNothingSelected");
            }
        });

        spinner_type_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // String pres_doctor = spinner_day_period_min.getSelectedItem().toString();
                // int index_item = position;

                bl.setSpinnerDefinition(Constants.COL_TYPE_PERIOD, position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("onNothingSelected", "onNothingSelected");
            }
        });


        //


    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
