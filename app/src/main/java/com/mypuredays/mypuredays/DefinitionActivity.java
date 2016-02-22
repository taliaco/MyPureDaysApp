package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class DefinitionActivity extends Activity {


    private BL bl;

    private LinearLayout linear_ona_type, linear_reminder_pure_day,linear_mikve_reminder_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        bl = new BL(this);
        linear_ona_type = (LinearLayout)findViewById(R.id.linear_layout_type_period);
        linear_reminder_pure_day = (LinearLayout)findViewById(R.id.linear_reminder_pure_day);
        linear_mikve_reminder_day = (LinearLayout)findViewById(R.id.linear_mikve_reminder_day);

        TextView minPeriodLengthColumn = (TextView) findViewById(R.id.item_name_day_period_min);
        TextView regularColumn = (TextView) findViewById(R.id.item_name_period_constant);
        TextView prishaDaysColumn = (TextView) findViewById(R.id.item_name_prisha_day);
        TextView periodLengthColumn = (TextView) findViewById(R.id.item_name_period_during);
        TextView countCleanColumn = (TextView) findViewById(R.id.item_name_counter_pure_day);
        TextView cleanNotificationColumn = (TextView) findViewById(R.id.item_name_reminder_pure_day);
        TextView mikveNotificationColumn = (TextView) findViewById(R.id.item_name_mikve_reminder_day);
        TextView typePeriodColumn = (TextView) findViewById(R.id.item_name_type_period);

        Switch switch_period_constant = (Switch) findViewById(R.id.switch_period_constant);
        Switch switch_prisha_day = (Switch)findViewById(R.id.switch_prisha_day);
        Switch switch_counter_pure_day = (Switch) findViewById(R.id.switch_counter_pure_day);
        Switch switch_reminder_mikve_day = (Switch) findViewById(R.id.switch_mikve_reminder_day);

        Spinner spinner_day_period_min = (Spinner) findViewById(R.id.spinner_day_period_min);
        Spinner spinner_period_during = (Spinner) findViewById(R.id.spinner_period_during);
        final Spinner spinner_reminder_pure_day = (Spinner) findViewById(R.id.spinner_reminder_pure_day);
        final Spinner spinner_type_period = (Spinner)findViewById(R.id.spinner_type_period);

        ArrayAdapter<String> adapter;

        Cursor c = bl.getDefinitionCursor();
        Definition d = bl.getDefinition();
        try{
        String[] minPeriodLengthSpinner = Constants.MIN_PERIOD_LENGTH_SPINNER;
        String[] periodLengthSpinner = Constants.PERIOD_LENGTH_SPINNER;
        String[] clearDayNotificationSpinner = Constants.CLEAR_DAY_NOTIFICATION_SPINNER;
        String[] typeOnaSpinner = Constants.TYPE_ONA_SPINNER;

        for(int i=1; i<c.getColumnCount();i++){

            switch (i){

                case 1: minPeriodLengthColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item, minPeriodLengthSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_day_period_min.setAdapter(adapter);
                    spinner_day_period_min.setSelection(d.get_minPeriodLengthID());


                break;
                case 2: regularColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_period_constant.setChecked(d.is_regulary());
                    break;
                case 3: prishaDaysColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_prisha_day.setChecked(d.is_prishaDays());//bl.getDefinitionSwitchState(c.getColumnName(i)));
                    if(d.is_prishaDays())
                        linear_ona_type.setVisibility(View.VISIBLE);
                    break;
                case 4: periodLengthColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item, periodLengthSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_period_during.setAdapter(adapter);
                    spinner_period_during.setSelection(d.get_periodLengthID());

                    break;
                case 5: countCleanColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_counter_pure_day.setChecked(d.is_countClean());
                    if(d.is_countClean() && Constants.EXIST_REMINDER)
                        linear_reminder_pure_day.setVisibility(View.VISIBLE);
                    break;

                case 6: cleanNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item, clearDayNotificationSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_reminder_pure_day.setAdapter(adapter);
                    spinner_reminder_pure_day.setSelection(d.get_cleanNotification());

                    break;
                case 7: mikveNotificationColumn.setText(Utils.getDefName(c.getColumnName(i), this));
                    switch_reminder_mikve_day.setChecked(d.get_mikveNutification());
                    break;
                case 8: typePeriodColumn.setText(Utils.getDefName(c.getColumnName(i), this));

                    adapter = new ArrayAdapter<>(DefinitionActivity.this,
                            android.R.layout.simple_spinner_item, typeOnaSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_type_period.setAdapter(adapter);
                    spinner_type_period.setSelection(d.get_typeOnaID());

                    break;
                default:
                    break;

            }//switch end

        }//for end
        } finally {
            c.close();
        }
if(!Constants.EXIST_REMINDER){
    linear_mikve_reminder_day.setVisibility(View.GONE);
}

        //set all switch listener
        switch_period_constant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                bl.setSwitchDefinition(Constants.COL_REGULAR, isChecked);
            }
        });

        switch_prisha_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                bl.setSwitchDefinition(Constants.COL_PRISHA_DAYS, isChecked);
               // if(spinner_type_period.getSelectedItem() != Constants.ONA_TYPE.DEFAULT.ordinal()){
                //    linear_ona_type.setVisibility(View.GONE);
               // }
                if(!isChecked){
                    spinner_type_period.setSelection(Constants.DEFAULT_SPINNER_TYPE_ONA);
                    //linear_ona_type.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                    linear_ona_type.setVisibility(View.GONE);
                }
                else{
                   // linear_ona_type.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1f));
                    linear_ona_type.setVisibility(View.VISIBLE);
                }
            }
        });

        switch_counter_pure_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                bl.setSwitchDefinition(Constants.COL_COUNT_CLEAN, isChecked);

                if(!isChecked){
                    spinner_reminder_pure_day.setSelection(Constants.DEFAULT_SPINNER_COUNTER_PURE_DAY);
                    //linear_ona_type.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                    linear_reminder_pure_day.setVisibility(View.GONE);
                }
                else if(Constants.EXIST_REMINDER){
                    // linear_ona_type.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1f));
                    linear_reminder_pure_day.setVisibility(View.VISIBLE);
                }
            }
        });

        switch_reminder_mikve_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                bl.setSwitchDefinition(Constants.COL_MIKVE_NOTIFICATION, isChecked);
            }
        });


        //set all spinner listener
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

                bl.setSpinnerDefinition(Constants.COL_CLEAN_NOTIFICATION, position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("onNothingSelected", "onNothingSelected");
            }
        });

        spinner_type_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                bl.setSpinnerDefinition(Constants.COL_TYPE_PERIOD, position);
                if(position == (Constants.ONA_TYPE.DEFAULT.ordinal())){
                    Switch switch_prisha_day = (Switch)findViewById(R.id.switch_prisha_day);
                    switch_prisha_day.setChecked(false);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                Log.e("onNothingSelected", "onNothingSelected");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
