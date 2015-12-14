package com.mypuredays.mypuredays;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by David on 19/11/2015.
 */
public class Day {

    private int _rowId;
    private Date _date;
    private int _dayTypeId;
    private String _notes;
    public static ArrayList<Day> day_arr;


    public Day(int rowId, Date date, int dayTypeId, String notes)
    {
        _rowId=rowId;
        _date=date;
        _dayTypeId =dayTypeId;
        _notes=notes;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public void set_dayTypeId(int _dayTypeId) {
        this._dayTypeId = _dayTypeId;
    }

    public void set_notes(String _notes) {
        this._notes = _notes;
    }

    public Date get_date() {

        return _date;
    }

    public int get_dayTypeId() {
        return _dayTypeId;
    }

    public String get_notes() {
        return _notes;
    }
}
