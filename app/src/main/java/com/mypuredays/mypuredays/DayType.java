package com.mypuredays.mypuredays;

/**
 * Created by David on 19/11/2015.
 */
public class DayType {
    private int _ID;
    private String _Type;

    public DayType(int _ID, String _Type) {
        this._ID = _ID;
        this._Type = _Type;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }
}
