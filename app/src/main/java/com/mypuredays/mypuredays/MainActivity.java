package com.mypuredays.mypuredays;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    BL bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
        bl = new BL(this);
        try {
            bl.setStartEndLooking(df.parse("08-12-2015"), Constants.DAY_TYPE.START_LOOKIND);
            bl.setStartEndLooking(df.parse("01-12-2015"), Constants.DAY_TYPE.START_LOOKIND);
            bl.setStartEndLooking(df.parse("20-12-2015"), Constants.DAY_TYPE.START_LOOKIND);
        } catch (ParseException e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
        }
        bl = new BL(this);
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);

        //first run
        if (settings.getBoolean("my_first_time", true)) {
            // first time task
            bl.populateDB();
            try {
                bl.setStartEndLooking(df.parse("01-12-2015"), Constants.DAY_TYPE.START_LOOKIND);
                bl.setStartEndLooking(df.parse("05-12-2015"), Constants.DAY_TYPE.END_LOOKING);
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
        Resources res = getResources();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Button b =(Button)findViewById(R.id.btStartEnd);
        String text = b.getText().toString();
        if(text.equals(res.getString(R.string.btStart))){
            b.setText(res.getString(R.string.btEnd));
            bl.setStartEndLooking(new Date(),Constants.DAY_TYPE.START_LOOKING);
            alertDialogBuilder.setMessage("התחלת ראיה היום" + new Date());
        }
        else{
            b.setText(res.getString(R.string.btStart));
            bl.setStartEndLooking(new Date(),Constants.DAY_TYPE.END_LOOKING);
            alertDialogBuilder.setMessage("הפסק ראיה היום" + new Date());
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




}