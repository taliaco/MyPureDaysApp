package com.mypuredays.mypuredays;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.GregorianCalendar;

/**
 * Created by David on 19/11/2015.
 */
public class ClearDayType {

    private int _rowId;
    private int _ID;
    private String _Type;

    public ClearDayType(int rowId, int _ID, String _Type) {

        this._rowId=rowId;
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

    public static class CalenderActivity extends Activity {
        public GregorianCalendar cal_month, cal_month_copy;
        private DayType.CalendarAdapter cal_adapter;
        private TextView tv_month;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calender);


            cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
            cal_month_copy = (GregorianCalendar) cal_month.clone();
            cal_adapter = new DayType.CalendarAdapter(this, cal_month,CalendarCollection.date_collection_arr);



            tv_month = (TextView) findViewById(R.id.tv_month);
            tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));

            ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);

            previous.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setPreviousMonth();
                    refreshCalendar();
                }
            });

            ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
            next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setNextMonth();
                    refreshCalendar();

                }
            });

            GridView gridview = (GridView) findViewById(R.id.gv_calendar);
            gridview.setAdapter(cal_adapter);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    ((DayType.CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                    String selectedGridDate = DayType.CalendarAdapter.day_string
                            .get(position);

                    String[] separatedTime = selectedGridDate.split("-");
                    String gridvalueString = separatedTime[2].replaceFirst("^0*","");
                    int gridvalue = Integer.parseInt(gridvalueString);

                    if ((gridvalue > 10) && (position < 8)) {
                        setPreviousMonth();
                        refreshCalendar();
                    } else if ((gridvalue < 7) && (position > 28)) {
                        setNextMonth();
                        refreshCalendar();
                    }
                    ((DayType.CalendarAdapter) parent.getAdapter()).setSelected(v,position);

                    ((DayType.CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, CalenderActivity.this);
                }

            });


        }


        protected void setNextMonth() {
            if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                    .getActualMaximum(GregorianCalendar.MONTH)) {
                cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                        cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
            } else {
                cal_month.set(GregorianCalendar.MONTH,
                        cal_month.get(GregorianCalendar.MONTH) + 1);
            }

        }

        protected void setPreviousMonth() {
            if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                    .getActualMinimum(GregorianCalendar.MONTH)) {
                cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                        cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
            } else {
                cal_month.set(GregorianCalendar.MONTH,
                        cal_month.get(GregorianCalendar.MONTH) - 1);
            }

        }

        public void refreshCalendar() {
            cal_adapter.refreshDays();
            cal_adapter.notifyDataSetChanged();
            tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
        }

    }
}
