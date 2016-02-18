package com.mypuredays.mypuredays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CalenderActivity extends Activity {
    public  GregorianCalendar cal_month;
    public GregorianCalendar cal_month_copy;
    private CalendarAdapter cal_adapter;
    private TextView tv_month;
    private TextView TextView_Note;
    BL bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        bl= new BL(this);

        //get all days from DB
        ArrayList<Day> arrayListDays = bl.getAllDays();
        CalendarCollection.date_collection_arr=new ArrayList<>();

        //convert Day Type to CalendarCollection list
        for(int i=0; i< arrayListDays.size(); i++){
            CalendarCollection tmpCalendarCollection = new CalendarCollection(arrayListDays.get(i));
            CalendarCollection.date_collection_arr.add(tmpCalendarCollection);
        }
        TextView_Note = (TextView) findViewById(R.id.TextView_Note);
        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        cal_adapter = new CalendarAdapter(this, cal_month,CalendarCollection.date_collection_arr);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format(Constants.MONTH_TITLE_FORMAT, cal_month));

        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(cal_adapter);
        gridview.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                String selectedGridDate = cal_adapter.day_string.get(position);
                String[] separatedTime = selectedGridDate.split(Constants.DATE_SPLITTER);
                String gridvalueString = separatedTime[Constants.DAY_POSITION].replaceFirst("^0*", "");
                int gridvalue = Integer.parseInt(gridvalueString);
                if ((gridvalue > 25) && (position < 8)) {
                    return true;
                } else if ((gridvalue < 7) && (position > 28)) {
                    return true;
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v, position);
                refreshCalendar();
                return true;
            }

        });

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = cal_adapter.day_string.get(position);
                String[] separatedTime = selectedGridDate.split(Constants.DATE_SPLITTER);
                String gridvalueString = separatedTime[Constants.DAY_POSITION].replaceFirst("^0*", "");
                int gridvalue = Integer.parseInt(gridvalueString);
                if ((gridvalue > 25) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                else{
                    String note = getResources().getString((R.string.defaultNote));;
                    int len = CalendarCollection.date_collection_arr.size();
                    String dateStr = cal_adapter.getPosDate(position);
                    int dayType = 0;
                    for (int i = 0; i < len; i++) {
                        CalendarCollection cal_obj = CalendarCollection.date_collection_arr.get(i);
                        if(cal_obj.date.equals(dateStr)) {
                            if((cal_obj.notes != null || !cal_obj.notes.isEmpty())) {
                                note = cal_obj.notes;
                            }
                            else{
                                note = getResources().getString((R.string.defaultNote));
                            }

                            dayType = cal_obj._dateTypeId;

                            break;
                        }
                    }
                    String dayNote = "";
                    if(dayType == 0) {
                        dayType = Utils.getDayType(bl, dateStr);
                    }

                    if(dayType == Constants.DAY_TYPE.PERIOD.ordinal()){

                        dayNote =getResources().getString((R.string.periodNote));
                    }
                    else if(dayType == Constants.DAY_TYPE.START_LOOKING.ordinal()){

                        dayNote = getResources().getString((R.string.startLookingNote));
                    }
                    else if(dayType == Constants.DAY_TYPE.END_LOOKING.ordinal()){

                        dayNote = getResources().getString((R.string.endLookingNote));
                    }
                    else if(dayType == Constants.DAY_TYPE.NEXT_PERIOD.ordinal()){

                        dayNote = getResources().getString((R.string.nextPeriodNote));
                    }
                    else if(dayType == Constants.DAY_TYPE.CLEAR_DAY.ordinal()){

                        dayNote = getResources().getString((R.string.clearDayNote));
                    }
                    else if(dayType == Constants.DAY_TYPE.MIKVEH.ordinal()){

                        dayNote = getResources().getString((R.string.mikveNote));
                    }
                    else if(dayType == Constants.DAY_TYPE.PRISHA.ordinal()){

                        dayNote = getResources().getString((R.string.prishaNote));
                    }
                    if(!dayNote.isEmpty())
                    {
                        dayNote += "\n";
                    }
                    if(!note.isEmpty())
                    {
                        note = "\t- " + note;
                    }
                    TextView_Note.setText(dayNote + note);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format(Constants.MONTH_TITLE_FORMAT, cal_month));

    }
}