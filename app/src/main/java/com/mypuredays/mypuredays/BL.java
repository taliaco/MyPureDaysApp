package com.mypuredays.mypuredays;

import android.content.Context;
import android.database.Cursor;

import com.mypuredays.mypuredays.DAL;

/**
 * Created by Talia on 25/11/2015.
 */
public class BL {
    DAL dal;
    public BL(Context context) {
        dal = new DAL(context);
    }

    public void populateDB(){
        Definition def = new Definition();
        dal.writeToDefinition(def);

        //ClearDayType clrDayType = new ClearDayType()
    }

    public Definition readFromDefinition(){

        return dal.readFromDefinition();
    }

    public Cursor readCursorFromDefinition() {

        return dal.readCursorFromDefinition();
    }


}
