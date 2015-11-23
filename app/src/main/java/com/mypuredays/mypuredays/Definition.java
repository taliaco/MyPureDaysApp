package com.mypuredays.mypuredays;

/**
 * Created by David on 19/11/2015.
 */
public class Definition {

    private int _rowId;
    private int _minPeriodLength;
    private boolean _regulary;
    private boolean _prishaDays;
    private int _periodLength;
    private int _ovulationNutification;
    private int _cleanNotificationID;
    private boolean _countClean;
    private boolean _dailyNotification;

    public Definition(int rowId) {

        this._rowId = rowId;
        this._minPeriodLength = 0;
        this._dailyNotification = false;
        this._regulary = false;
        this._prishaDays = false;
        this._periodLength = 0;
        this._ovulationNutification = 0;
        this._countClean = false;
        this._cleanNotificationID = 0;
    }

    public Definition(int _rowId, int _minPeriodLength, boolean _regulary, boolean _prishaDays,int _periodLength,
                      int _ovulationNutification, int _cleanNotificationID, boolean _countClean, boolean _dailyNotification) {
        this._rowId = _rowId;
        this._minPeriodLength = _minPeriodLength;
        this._regulary = _regulary;
        this._prishaDays = _prishaDays;

        this._periodLength = _periodLength;
        this._ovulationNutification = _ovulationNutification;
        this._cleanNotificationID = _cleanNotificationID;
        this._countClean = _countClean;
        this._dailyNotification = _dailyNotification;
    }

    public boolean is_regulary() {
        return _regulary;
    }

    public void set_regulary(boolean _regulary) {
        this._regulary = _regulary;
    }

    public int get_minPeriodLength() {
        return _minPeriodLength;
    }

    public void set_minPeriodLength(int _minPeriodLength) {
        this._minPeriodLength = _minPeriodLength;
    }

    public boolean is_prishaDays() {
        return _prishaDays;
    }

    public void set_prishaDays(boolean _prishaDays) {
        this._prishaDays = _prishaDays;
    }

    public int get_periodLength() {
        return _periodLength;
    }

    public void set_periodLength(int _periodLength) {
        this._periodLength = _periodLength;
    }

    public int get_ovulationNutification() {
        return _ovulationNutification;
    }

    public void set_ovulationNutification(int _ovulationNutification) {
        this._ovulationNutification = _ovulationNutification;
    }

    public int get_cleanNotification() {
        return _cleanNotificationID;
    }

    public void set_cleanNotification(int _cleanNotificationID) {
        this._cleanNotificationID = _cleanNotificationID;
    }

    public boolean is_countClean() {
        return _countClean;
    }

    public void set_countClean(boolean _countClean) {
        this._countClean = _countClean;
    }

    public boolean is_dailyNotification() {
        return _dailyNotification;
    }

    public void set_dailyNotification(boolean _dailyNotification) {
        this._dailyNotification = _dailyNotification;
    }
}
