package com.mypuredays.mypuredays;

import java.util.ArrayList;

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

        this.date=day.get_date().toString();
        this.notes=day.get_notes().toString();
        this._dateTypeId=day.get_dateTypeId();
    }

}
