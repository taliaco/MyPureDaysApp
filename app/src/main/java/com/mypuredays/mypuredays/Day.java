package com.mypuredays.mypuredays;

import java.util.Date;

public class Day {

    private Date _date;
    private int _dayTypeId;
    private String _notes;
    private int _ona;

    public Day(Date date, int dayTypeId, String notes, int ona)
    {
        _date=date;
        _dayTypeId =dayTypeId;
        _notes=notes;
        _ona=ona;
        _ona=ona;
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

    public void set_ona(int _ona) {
        this._ona = _ona;
    }

    public int get_ona() {

        return _ona;
    }

    public int get_dayTypeId() {
        return _dayTypeId;
    }

    public String get_notes() {
        return _notes;
    }
}
