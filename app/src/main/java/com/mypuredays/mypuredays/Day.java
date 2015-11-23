package com.mypuredays.mypuredays;

import java.util.Date;

/**
 * Created by David on 19/11/2015.
 */
public class Day {

    private int _rowId;
    private Date _date;
    private int _dateTypeId;
    private String _notes;

    public Day(int rowId, Date date, int dateTypeId, String notes)
    {
        _rowId=rowId;
        _date=date;
        _dateTypeId=dateTypeId;
        _notes=notes;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public void set_dateTypeId(int _dateTypeId) {
        this._dateTypeId = _dateTypeId;
    }

    public void set_notes(String _notes) {
        this._notes = _notes;
    }

    public Date get_date() {

        return _date;
    }

    public int get_dateTypeId() {
        return _dateTypeId;
    }

    public String get_notes() {
        return _notes;
    }
}
