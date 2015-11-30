package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class DefinitionActivity extends Activity {

    private ListView listDef;
    private BL bl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        bl = new BL(this);
        listDef= (ListView)findViewById(R.id.definition_list);


        TextView name = (TextView)findViewById(R.id.item_name);
        Switch offOn = (Switch)findViewById(R.id.on_off_switch);


        Cursor c = bl.readCursorFromDefinition();

        for(int i=0; i<c.getColumnCount();i++){

            String defName = Constants.getDefName(c.getColumnName(i));
            name.setText(defName);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
