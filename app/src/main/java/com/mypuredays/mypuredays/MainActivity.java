package com.mypuredays.mypuredays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    BL bl;
    CharSequence txt = "Hello toast!";

    final DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        bl = new BL(this);
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);

        //first run
        if (settings.getBoolean("my_first_time", true)) {
            // first time task
            bl.populateDB();
            try {
                bl.setStartEndLooking(df.parse("2015-08-01"), Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.DEFAULT);
                bl.setStartEndLooking(df.parse("2015-08-08"), Constants.DAY_TYPE.END_LOOKING,Constants.ONA_TYPE.DEFAULT);

                bl.setStartEndLooking(df.parse("2015-09-05"), Constants.DAY_TYPE.START_LOOKING,Constants.ONA_TYPE.DEFAULT);
                bl.setStartEndLooking(df.parse("2015-09-07"), Constants.DAY_TYPE.END_LOOKING,Constants.ONA_TYPE.DEFAULT);

                bl.setStartEndLooking(df.parse("2015-10-05"), Constants.DAY_TYPE.START_LOOKING,Constants.ONA_TYPE.DEFAULT);
                bl.setStartEndLooking(df.parse("2015-11-05"), Constants.DAY_TYPE.START_LOOKING,Constants.ONA_TYPE.DEFAULT);
                bl.setStartEndLooking(df.parse("2015-12-05"), Constants.DAY_TYPE.START_LOOKING,Constants.ONA_TYPE.DEFAULT);
            } catch (ParseException e) {
                Log.e("ERROR", e.getMessage());
                e.printStackTrace();
            }
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
            //openDefinition();

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
        Intent intent = new Intent(this, DefinitionActivity.class);
        //intent.putExtra("definition",(Serializable)studentObject);
        startActivity(intent);
    }
    public void openStatus(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }
    public void openCalender(View v) {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }
    public void startLooking(View view) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;
        Definition def =bl.getDefinition();
        Resources res = getResources();
       // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
           // @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        final Dialog dialogPrisha = new Dialog(this);
        Button b =(Button)findViewById(R.id.btStartEnd);
        String text = b.getText().toString();
        if(text.equals(res.getString(R.string.btStart))){//START
            b.setText(res.getString(R.string.btEnd));
            if(def.is_prishaDays()==true){//user keep prisha days
                dialogPrisha.setContentView(R.layout.activity_dialog_onot);

                Button dialogButtonDay = (Button) dialogPrisha.findViewById(R.id.dialogButtonDay);
                Button dialogButtonNight = (Button) dialogPrisha.findViewById(R.id.dialogButtonNight);

                dialogButtonDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date date = new Date();
                        bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.DAY);
                        Cursor c= bl.getLastDate();
                        if (c.moveToFirst()) {
                            txt= c.getString(1)+" "+c.getInt(2)+" "+c.getString(3)+" "+c.getInt(4);//t i t i
                            Toast toast = Toast.makeText(context, txt, duration);
                            toast.show();
                        }
//                        CalendarCollection tmpCalendarCollection = new CalendarCollection(date,"",Constants.DAY_TYPE.START_LOOKING.ordinal());
//                        CalendarCollection.date_collection_arr.add(tmpCalendarCollection);
                        dialogPrisha.dismiss();
                    }
                });

                dialogButtonNight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date date = new Date();
                        bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.NIGHT);
                        Cursor c= bl.getLastDate();
                        if (c.moveToFirst()) {
                            txt= c.getString(1)+" "+c.getInt(2)+" "+c.getString(3)+" "+c.getInt(4);//t i t i
                            Toast toast = Toast.makeText(context, txt, duration);
                            toast.show();
                        }
                        dialogPrisha.dismiss();
                    }
                });

               //dialog.setTitle(day_string.get(pos));
//                txt="התחל ראייה היום"  + " "+ new Date();
//                Toast toast = Toast.makeText(context, txt, duration);
//                toast.show();
                dialogPrisha.show();
            }
            else {//user not keep prisha days
                bl.setStartEndLooking(new Date(), Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.DEFAULT);//ona=0
                //alertDialogBuilder.setMessage("התחלת ראיה היום" + new Date());
                Cursor c= bl.getLastDate();
                if (c.moveToFirst()) {
                    txt= c.getString(1)+" "+c.getInt(2)+" "+c.getString(3)+" "+c.getInt(4);//t i t i
                }
               // txt="התחל ראייה היום"  + " "+ new Date();
                Toast toast = Toast.makeText(context, txt, duration);
                toast.show();
            }
        }
        else{//END
                b.setText(res.getString(R.string.btStart));
                bl.setStartEndLooking(new Date(), Constants.DAY_TYPE.END_LOOKING, Constants.ONA_TYPE.DEFAULT);
                //alertDialogBuilder.setMessage("הפסק ראיה היום" + new Date());
            Cursor c= bl.getLastDate();
            if (c.moveToFirst()) {
                txt= c.getString(1)+" "+c.getInt(2)+" "+c.getString(3)+" "+c.getInt(4);//t i t i
            }
            //txt="הפסק ראייה היום"    +new Date();
            Toast toast = Toast.makeText(context, txt, duration);
            toast.show();
        }

       // AlertDialog alertDialog = alertDialogBuilder.create();
        //alertDialog.show();
    }




}