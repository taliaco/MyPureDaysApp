package com.mypuredays.mypuredays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends Activity {

    BL bl;
    Button b;
    Resources res;
    public CharSequence txt = "Hello toast!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        bl = new BL(this);
        res = getResources();
        b = (Button) findViewById(R.id.btStartEnd);
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);

        int lastPeriodType = bl.getTypeOfDate("1970-01-01", Utils.DateToStr(new Date()));
        if (lastPeriodType == Constants.DAY_TYPE.START_LOOKING.ordinal() || lastPeriodType == Constants.DAY_TYPE.PERIOD.ordinal()) {
            b.setText(res.getString(R.string.btEnd));
        } else {
            b.setText(res.getString(R.string.btStart));
        }

        //first run
        if (settings.getBoolean("my_first_time", true)) {
            // first time task
            bl.populateDB();

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).apply();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDefinition(View view) {

       // Intent intentB = new Intent("android.intent.action.BOOT_COMPLETED");
       // sendBroadcast(intentB);

        Intent intent = new Intent(this, DefinitionActivity.class);
        //intent.putExtra("definition",(Serializable)studentObject);
        startActivity(intent);


    }

    public void openStatus(View view) {

//        Intent intent = new Intent(this, alarm_clear.class);
//        startActivity(intent);

        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    public void openCalender(View v) {
        finish();
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    public void startLooking(View view) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;
        Definition def = bl.getDefinition();
        final Cursor dayStartOrEndLook= bl.getLastDate();
        final Date date = new Date();//today
        final String dateStr =Utils.StrToDateDisplay(Utils.DateToStr(date));
         final Dialog dialogPrisha = new Dialog(this);



        String text = b.getText().toString();
        if (text.equals(res.getString(R.string.btStart))) {//START

            if (def.is_prishaDays() && def.get_typeOnaID() == Constants.ONA_TYPE.UNKNOWN.ordinal()) {//user keep prisha days and the ona unknow
                dialogPrisha.setContentView(R.layout.activity_dialog_onot);
                dialogPrisha.setTitle("בחרי עונה");
                Button dialogButtonDay = (Button) dialogPrisha.findViewById(R.id.dialogButtonDay);
                Button dialogButtonNight = (Button) dialogPrisha.findViewById(R.id.dialogButtonNight);

                dialogButtonDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.DAY);
                        txt = "התחל ראייה היום" + " " + dateStr;
                        Toast toast = Toast.makeText(context, txt, duration);
                        toast.show();
                        dialogPrisha.dismiss();
                        b.setText(res.getString(R.string.btEnd));
                    }
                });

                dialogButtonNight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.NIGHT);
                        txt = "התחל ראייה היום" + " " + dateStr;
                        Toast toast = Toast.makeText(context, txt, duration);
                        toast.show();
                        dialogPrisha.dismiss();
                        b.setText(res.getString(R.string.btEnd));
                    }
                });
                dialogPrisha.show();
            } else if (def.is_prishaDays()) {//user keep prisha && ona=day or night
                // take the ona type from definition
                if (def.get_typeOnaID() == Constants.ONA_TYPE.NIGHT.ordinal()) {
                    bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.NIGHT);
                } else if (def.get_typeOnaID() == Constants.ONA_TYPE.DAY.ordinal()) {
                    bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.DAY);
                }
                txt = "התחל ראייה היום" + " " + dateStr;
                Toast toast = Toast.makeText(context, txt, duration);
                toast.show();
                b.setText(res.getString(R.string.btEnd));
            } else {//user not keep prisha days
                bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.DEFAULT);//ona=0
                txt = "התחל ראייה היום" + " " + dateStr;
                Toast toast = Toast.makeText(context, txt, duration);
                toast.show();
                b.setText(res.getString(R.string.btEnd));
            }
        } else {//END LOOKING
            b.setText(res.getString(R.string.btStart));
            String todayStr = Utils.DateToStr(new Date());
            Date today = new Date();

            final Day day = bl.getPrevType(todayStr);//day=last day in the DB before today

            if (day.get_date() != null && Utils.countDaysBetweenDates(day.get_date(), today) < def.get_minPeriodLength()) {//can not end period befor minPeriodLength
                b.setText(res.getString(R.string.btEnd));
                txt ="לא עברו מינימום ימי מחזור";
                Toast toast = Toast.makeText(context, txt, duration);
                toast.show();
            } else {

                if (dayStartOrEndLook.moveToFirst() && dayStartOrEndLook.getString(1) != null) {
                    if (Utils.DateToStr(date).equals(dayStartOrEndLook.getString(1))) {//if end period and start period==same day
                        bl.deleteDay(Utils.DateToStr(date));
                    } else {
                        bl.setStartEndLooking(date, Constants.DAY_TYPE.END_LOOKING, Constants.ONA_TYPE.DEFAULT);//last start period != today
                    }
                } else {//if the table is empty
                    bl.setStartEndLooking(date, Constants.DAY_TYPE.END_LOOKING, Constants.ONA_TYPE.DEFAULT);
                }

                txt = "הפסק  ראייה היום" + " " + dateStr;
                Toast toast = Toast.makeText(context, txt, duration);
                toast.show();
            }
        }
    }
}