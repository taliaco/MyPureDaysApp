package com.mypuredays.mypuredays;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        df = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        this.date = df.format(day.get_date());
        this.notes=day.get_notes().toString();
        this._dateTypeId=day.get_dayTypeId();
    }

    public CalendarCollection(Date date, String note, int dayTypeID){
        DateFormat df;
        df = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        this.date = df.format(date);
        this.notes = note;
        this._dateTypeId = dayTypeID;
    }

}
