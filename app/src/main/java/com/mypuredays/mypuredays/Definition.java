package com.mypuredays.mypuredays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by David on 19/11/2015.
 */
public class Definition {

    private int _rowId;
    private int _minPeriodLength;
    private boolean _regulary;
    private boolean _prishaDays;
    private int _periodLength;
    private boolean _countClean;
    private boolean _dailyNotification;
    private int _cleanNotificationID;
    private boolean _ovulationNutification;


    public Definition() {

        this._rowId = -1;
        this._minPeriodLength = 0;
        this._regulary = true;
        this._prishaDays = true;
        this._periodLength = 0;
        this._countClean = false;
        this._dailyNotification = false;
        this._cleanNotificationID = 0;
        this._ovulationNutification = false;


    }

    public Definition(int _rowId, int _minPeriodLength, boolean _regulary, boolean _prishaDays,int _periodLength,
                      boolean _countClean, boolean _dailyNotification, int _cleanNotificationID,boolean _ovulationNutification  ) {
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

    public boolean is_prishaDays() {//true=display prisha date
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

    public boolean get_ovulationNutification() {
        return _ovulationNutification;
    }

    public void set_ovulationNutification(boolean _ovulationNutification) {
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

    public static class AndroidListAdapter extends ArrayAdapter<CalendarCollection> {

        private final Context context;
        private final ArrayList<CalendarCollection> values;
        private ViewHolder viewHolder;
        private final int resourceId;

        public AndroidListAdapter(Context context, int resourceId,ArrayList<CalendarCollection> values) {
            super(context, resourceId, values);
            // TODO Auto-generated constructor stub

            this.context = context;
            this.values = values;
            this.resourceId = resourceId;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resourceId, parent, false);


                viewHolder = new ViewHolder();
                viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                viewHolder.tv_event = (TextView) convertView.findViewById(R.id.tv_event);


                convertView.setTag(viewHolder);


            }else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CalendarCollection list_obj=values.get(position);
            viewHolder.tv_date.setText(list_obj.date);
            viewHolder.tv_event.setText(list_obj.notes);

            return convertView;
        }





        public class ViewHolder {

            TextView tv_event;
            TextView tv_date;

        }

    }
}
