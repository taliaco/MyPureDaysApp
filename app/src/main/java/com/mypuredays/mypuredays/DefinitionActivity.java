package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DefinitionActivity extends Activity {

    private ListView listDef;
    private BL bl;

    private ArrayAdapter<String> listAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        bl = new BL(this);
        listDef= (ListView)findViewById(R.id.definition_list);


        // Create and populate a List of planet names.
        String[] defs = new String[] { };
        ArrayList<String> defsList = new ArrayList<String>();
        defsList.addAll(Arrays.asList(defs));

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.item_definition, defsList);

        Cursor c = bl.readCursorFromDefinition();

        for(int i=0; i<c.getColumnCount();i++){
            listAdapter.add(Constants.getDefName(c.getColumnName(i),this) );
        }
        listDef.setAdapter( listAdapter );
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
