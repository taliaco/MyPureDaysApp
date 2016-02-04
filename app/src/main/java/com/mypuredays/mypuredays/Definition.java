package com.mypuredays.mypuredays;

public class Definition {

    private int _minPeriodLength;
    private boolean _regulary;
    private boolean _prishaDays;
    private int _periodLength;
    private boolean _countClean;
    private int _cleanNotificationID;
    private boolean _mikveNutification;
    private int _typeOna;


    public Definition() {
        this._minPeriodLength = 0;
        this._regulary = false;
        this._prishaDays = false;
        this._periodLength = 0;
        this._countClean = false;
        this._cleanNotificationID = 4;
        this._mikveNutification = false;
        this._typeOna = 1;
    }

    public Definition(int _minPeriodLength, boolean _regulary, boolean _prishaDays, int _periodLength,
                      boolean _countClean, int _cleanNotificationID, boolean _mikveNutification, int _typeOna) {
        this._minPeriodLength = _minPeriodLength;
        this._regulary = _regulary;
        this._prishaDays = _prishaDays;
        this._periodLength = _periodLength;
        this._mikveNutification = _mikveNutification;
        this._cleanNotificationID = _cleanNotificationID;
        this._countClean = _countClean;
        this._typeOna = _typeOna;
    }

    public boolean is_regulary() {
        return _regulary;
    }

    public void set_regulary(boolean _regulary) {
        this._regulary = _regulary;
    }

    public int get_minPeriodLengthID() {
        return _minPeriodLength;
    }

    public int get_minPeriodLength() {
        return Integer.parseInt(Constants.MIN_PERIOD_LENGTH_SPINNER[_minPeriodLength]);
    }

    public void set_minPeriodLength(int _minPeriodLength) {
        this._minPeriodLength = _minPeriodLength;
    }

    public boolean is_prishaDays() {//true=display prisha date
        return _prishaDays;
    }

    public void set_prishaDays(boolean _prishaDays) {
        this._prishaDays = _prishaDays;
    }

    public int get_periodLengthID() {
        return _periodLength;
    }

    public int get_periodLength() {
        return Integer.parseInt(Constants.PERIOD_LENGTH_SPINNER[_periodLength]);
    }

    public void set_periodLength(int _periodLength) {
        this._periodLength = _periodLength;
    }

    public boolean get_mikveNutification() {
        return _mikveNutification;
    }

    public void set_mikveNutification(boolean _mikveNutification) {
        this._mikveNutification = _mikveNutification;
    }

    public int get_cleanNotification() {
        return _cleanNotificationID;
    }

    public int get_typeOnaID() {
        return _typeOna;
    }

    public int get_typeOna() {
        String str=Constants.TYPE_ONA_SPINNER[_typeOna];
        if(str.equals("לילה")){
            return 3;
        }else if(str.equals("יום")){
                return 2;
        }
        else if(str.equals("לא ידוע")){
            return 1;
        }
        else if(str.equals("לא רלוונטי")){
            return 0;
        }
        else return 2;
        //return Integer.parseInt();
    }

    public void set_typeOna(int _typeOna) {
        this._typeOna = _typeOna;
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

}
