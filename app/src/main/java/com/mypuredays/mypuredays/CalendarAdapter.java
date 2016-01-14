package com.mypuredays.mypuredays;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class CalendarAdapter extends BaseAdapter {
    private Context context;

    private java.util.Calendar month;
    public GregorianCalendar pmonth;
    private BL bl;
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;

    private ArrayList<String> items;
    public static List<String> day_string;
    private View previousView;
    public ArrayList<CalendarCollection> date_collection_arr;

    public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<CalendarCollection> date_collection_arr) {
        this.date_collection_arr = date_collection_arr;
        CalendarAdapter.day_string = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        df = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        refreshDays();

    }

    public void setItems(ArrayList<String> items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    public int getCount() {
        return day_string.size();
    }

    public Object getItem(int position) {
        return day_string.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        bl = new BL(this.context);
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.cal_item, null);

        }


        dayView = (TextView) v.findViewById(R.id.date);
        String[] separatedTime = day_string.get(position).split(Constants.DATE_SPLITTER);


        String gridvalue = separatedTime[Constants.DAY_POSITION].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            dayView.setTextColor(Color.WHITE);
        }


        if (day_string.get(position).equals(curentDateString)) {

            v.setBackgroundColor(Color.CYAN);
        }
        else
         {
            v.setBackgroundColor(Color.parseColor("#343434"));

        }


        dayView.setText(gridvalue);

        // create date string for comparison
        String date = day_string.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        // show icon if date is not empty and it exists in the items array
        /*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.GONE);
        }
        */

        setEventView(v, position, dayView);

        return v;
    }

    public View setSelected(View view, final int pos) {

        bl = new BL(this.context);
        final DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog_calendar);
        dialog.setTitle(day_string.get(pos));

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);

        Button dialogButtonStart = (Button) dialog.findViewById(R.id.dialogButtonStart);
        Button dialogButtonEnd = (Button) dialog.findViewById(R.id.dialogButtonEnd);
        Button dialogButtonSaveNote = (Button) dialog.findViewById(R.id.dialogButtonSaveNote);
        Button dialogButtonClearDay = (Button) dialog.findViewById(R.id.dialogButtonClearDay);
        // if button is clicked, close the custom dialog
        dialogButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                try {
                    date = sdf.parse(day_string.get(pos));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING,Constants.ONA_TYPE.DEFAULT);

                CalendarCollection tmpCalendarCollection = new CalendarCollection(date,"",Constants.DAY_TYPE.START_LOOKING.ordinal());
                CalendarCollection.date_collection_arr.add(tmpCalendarCollection);


                Toast toast = Toast.makeText(context, "התחלת ראיה ביום" +  sdf.format(date), Toast.LENGTH_SHORT);
                toast.show();
                dialog.dismiss();
            }
        });
        dialogButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                try {
                    date = sdf.parse(day_string.get(pos));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bl.setStartEndLooking(date, Constants.DAY_TYPE.END_LOOKING,Constants.ONA_TYPE.DEFAULT);
                CalendarCollection tmpCalendarCollection = new CalendarCollection(date,"",Constants.DAY_TYPE.END_LOOKING.ordinal());
                CalendarCollection.date_collection_arr.add(tmpCalendarCollection);


                Toast toast = Toast.makeText(context, "הפסק ראיה ביום" + sdf.format(date), Toast.LENGTH_SHORT);
                toast.show();
                dialog.dismiss();
            }
        });
        dialogButtonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButtonClearDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();

        if (previousView != null) {
            previousView.setBackgroundColor(Color.parseColor("#343434"));
        }

        view.setBackgroundColor(Color.CYAN);

        int len = day_string.size();
        if (len > pos) {
            if (day_string.get(pos).equals(curentDateString)) {
            } else {
                previousView = view;
            }
        }
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }


    public void setEventView(View v, int pos, TextView txt) {

        int len = CalendarCollection.date_collection_arr.size();
        for (int i = 0; i < len; i++) {
            CalendarCollection cal_obj = CalendarCollection.date_collection_arr.get(i);
            String date = cal_obj.date;
            int len1 = day_string.size();
            if (len1 > pos) {

                if (day_string.get(pos).equals(date)) {
                    v.setBackgroundColor(Color.GREEN);
                  //RippleDrawable  v.setBackgroundResource(R.drawable.rounded_calender_item);

                    txt.setTextColor(Color.WHITE);
                }
                if(getDayType(day_string.get(pos)) == Constants.DAY_TYPE.START_LOOKING.ordinal()) {
                    v.setBackgroundColor(Color.RED);
                }
                if(getDayType(day_string.get(pos)) == Constants.DAY_TYPE.END_LOOKING.ordinal()) {
                    v.setBackgroundColor(Color.BLUE);
                }
            }
        }


    }
    private int getDayType(String DateString){
        for (int i = 0; i< date_collection_arr.size(); i++){
            if (date_collection_arr.get(i).date.equals(DateString))
                return date_collection_arr.get(i)._dateTypeId;
        }
        return Constants.DAY_TYPE.DEFAULT.ordinal();
    }
}