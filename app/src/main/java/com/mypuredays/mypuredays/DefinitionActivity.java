package com.mypuredays.mypuredays;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DefinitionActivity extends Activity {

    private ListView listDef;
    private ArrayList<String> defNameList;
    private ArrayAdapter<String> adapter;


    private Definition def;

    private static int  NUM_OF_DEF=8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        listDef= (ListView)findViewById(R.id.definition_list);

        //def=

        for(int i=0; i<NUM_OF_DEF; i++){

            defNameList.add("1");
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, defNameList);
        listDef.setAdapter(adapter);
    }
}
