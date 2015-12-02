package com.mypuredays.mypuredays;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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
        ArrayList<String> defsList = new ArrayList<String>();

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.item_definition, defsList);

        Cursor c = bl.getDefinitionCursor();
        for(int i=0; i<c.getColumnCount();i++){
            listAdapter.add(Utils.getDefName(c.getColumnName(i),this) );
        }
        listDef.setAdapter( listAdapter );
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
