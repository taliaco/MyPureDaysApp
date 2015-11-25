package com.mypuredays.mypuredays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class MainActivity extends Activity {
    public enum StartEnd {
        START, END
    }
    BL bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bl = new BL(this);
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);

        //first run
        if (settings.getBoolean("my_first_time", true)) {
            // first time task
            bl.populateDB();
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
    public void startLooking(View view) {
        Button b = (Button)view;
        String text = b.getText().toString();
        if(text.equals(R.string.btStart)){
            b.setText(R.string.btEnd);
            setStartEndLooking(StartEnd.START);
        }
        else{
            b.setText(R.string.btStart);
            setStartEndLooking(StartEnd.END);
        }
    }
    public boolean setStartEndLooking(Enum StartEnd){

        return true;
    }
}
