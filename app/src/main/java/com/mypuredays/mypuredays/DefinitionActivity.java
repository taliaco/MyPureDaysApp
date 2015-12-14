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

    private ArrayAdapter<ItemDefinition> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        bl = new BL(this);
        listDef= (ListView)findViewById(R.id.definition_list);


        // Create and populate a List of planet names.

        ArrayList<ItemDefinition> defsList = new ArrayList<ItemDefinition>();


        // Create ArrayAdapter using the planet list.
        listAdapter = new DefinitionListAdapter(this, R.layout.item_definition, defsList);

        Cursor c = bl.getDefinitionCursor();

        for(int i=0; i<c.getColumnCount();i++){

            listAdapter.add(new ItemDefinition (Utils.getDefName(c.getColumnName(i), this), Utils.getDefType(c.getColumnName(i))));
        }
        listDef.setAdapter( listAdapter );
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
