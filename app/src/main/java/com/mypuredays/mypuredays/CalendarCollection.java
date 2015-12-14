package com.mypuredays.mypuredays;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarCollection {
    public String date="";
    public String notes="";
    public int _dateTypeId;

    public static ArrayList<CalendarCollection> date_collection_arr;

    public CalendarCollection(String date,String notes){

        this.date=date;
        this.notes=notes;


    }

    public CalendarCollection(Day day){
        DateFormat df;
       // ArrayList<String> item = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        this.date = df.format(day.get_date());

        // this.date=day.get_date().toString();
        this.notes=day.get_notes().toString();
        this._dateTypeId=day.get_dayTypeId();
    }

}
