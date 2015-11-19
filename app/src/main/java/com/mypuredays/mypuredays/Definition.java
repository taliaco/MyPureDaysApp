package com.mypuredays.mypuredays;

/**
 * Created by David on 19/11/2015.
 */
public class Definition {

    private int _minPeriodLength;
    private boolean _regulary;
    private boolean _prishaDays;
    private int _periodLength;
    private int _ovulationNutification;
    private ClearDayType _cleanNotification;
    private boolean _countClean;
    private boolean _dailyNotification;

    public Definition() {
        this._minPeriodLength = 0;
        this._dailyNotification = false;
        this._regulary = false;
        this._prishaDays = false;
        this._periodLength = 0;
        this._ovulationNutification = 0;
        this._countClean = false;
        this._cleanNotification = null;
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

    public ClearDayType get_cleanNotification() {
        return _cleanNotification;
    }

    public void set_cleanNotification(ClearDayType _cleanNotification) {
        this._cleanNotification = _cleanNotification;
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
