package com.mypuredays.mypuredays;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
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
    public GregorianCalendar pMonthMaxSet;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;
    private int avgPeriondLength;
    private ArrayList<String> items;
    public List<String> day_string;
    private View previousView;
    public ArrayList<CalendarCollection> date_collection_arr;
    public Date[] prishaDateArr;

    public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<CalendarCollection> date_collection_arr) {

        this.date_collection_arr = date_collection_arr;

        day_string = new ArrayList<>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        bl = new BL(this.context);
        prishaDateArr = Utils.getPrishaDateArr(bl);
        avgPeriondLength = bl.getDefinition().get_periodLengthID();
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<>();
        df = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        refreshDays();

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
            v.findViewById(R.id.date_icon).setBackgroundResource(Constants.CURRENT_CIRCLE);
        } else {
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

//        bl = new BL(this.context);
        final DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_dialog_calendar);
        dialog.setTitle(Utils.StrToDateDisplay(day_string.get(pos)));


        // set the custom dialog components - text, image and button
        final TextView dialogNote = (TextView) dialog.findViewById(R.id.dialogNote);

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
                bl.setStartEndLooking(date, Constants.DAY_TYPE.START_LOOKING, Constants.ONA_TYPE.DEFAULT);

                CalendarCollection tmpCalendarCollection = new CalendarCollection(date, "", Constants.DAY_TYPE.START_LOOKING.ordinal());
                CalendarCollection.date_collection_arr.add(tmpCalendarCollection);
                notifyDataSetChanged();

                Toast toast = Toast.makeText(context, "התחלת ראיה ביום" + sdf.format(date), Toast.LENGTH_SHORT);
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
                bl.setStartEndLooking(date, Constants.DAY_TYPE.END_LOOKING, Constants.ONA_TYPE.DEFAULT);
                CalendarCollection tmpCalendarCollection = new CalendarCollection(date, "", Constants.DAY_TYPE.END_LOOKING.ordinal());
                CalendarCollection.date_collection_arr.add(tmpCalendarCollection);
                notifyDataSetChanged();

                Toast toast = Toast.makeText(context, "הפסק ראיה ביום" + sdf.format(date), Toast.LENGTH_SHORT);
                toast.show();
                dialog.dismiss();
            }
        });
        dialogButtonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                try {
                    date = sdf.parse(day_string.get(pos));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bl.saveNote(date, dialogNote.getText().toString());

                CalendarCollection tmpCalendarCollection = new CalendarCollection(date, dialogNote.getText().toString(), Constants.DAY_TYPE.DEFAULT.ordinal());
                int i = CalendarCollection.date_collection_arr.indexOf(tmpCalendarCollection);
                if (i > 0) {
                    CalendarCollection.date_collection_arr.set(CalendarCollection.date_collection_arr.indexOf(tmpCalendarCollection), tmpCalendarCollection);
                } else {
                    CalendarCollection.date_collection_arr.add(tmpCalendarCollection);
                }
                notifyDataSetChanged();
                Toast toast = Toast.makeText(context, context.getResources().getText(R.string.NoteSaved), Toast.LENGTH_SHORT);
                toast.show();
                dialog.dismiss();
            }
        });
        dialogButtonClearDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bl.deleteDay(day_string.get(pos));
                CalendarCollection calendarCollectiontmp = new CalendarCollection(day_string.get(pos), "");
                CalendarCollection.date_collection_arr.remove(calendarCollectiontmp);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();

        if (previousView != null) {
            previousView.setBackgroundColor(Color.parseColor("#343434"));
        }
        view.findViewById(R.id.date_icon).setBackgroundResource(Constants.CURRENT_CIRCLE);

        int len = day_string.size();
        if (len > pos) {
            if (!(day_string.get(pos).equals(curentDateString))) {
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
        pMonthMaxSet = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pMonthMaxSet.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);
        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {
            itemvalue = df.format(pMonthMaxSet.getTime());
            pMonthMaxSet.add(GregorianCalendar.DATE, 1);
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
        String dateStr = day_string.get(pos);
        int dayType = Utils.getDayType(bl, dateStr);
        boolean haveNote = bl.dayHaveNote(dateStr);
        View view = v.findViewById(R.id.date_icon);
        if (dayType == Constants.DAY_TYPE.START_LOOKING.ordinal()) {
            view.setBackgroundResource(Constants.PERIOD_CIRCLE);
        } else if (dayType == Constants.DAY_TYPE.END_LOOKING.ordinal()) {
            view.setBackgroundResource(Constants.PERIOD_CIRCLE);
        } else if (dayType == Constants.DAY_TYPE.PERIOD.ordinal()) {
            view.setBackgroundResource(Constants.PERIOD_CIRCLE);
        } else if (dayType == Constants.DAY_TYPE.CLEAR_DAY.ordinal()) {
            view.setBackgroundResource(Constants.CLEAR_CIRCLE);
            txt.setTextColor(Color.parseColor("#ff4d6a"));
        } else if (dayType == Constants.DAY_TYPE.PRISHA.ordinal()) {
            view.setBackgroundResource(Constants.PRISHA_CIRCLE);
        } else if (dayType == Constants.DAY_TYPE.DEFAULT.ordinal() && haveNote) {
            view.setBackgroundResource(Constants.OTHER_CIRCLE);
        } else if (dateStr.equals(Utils.DateToStr(new Date()))) {
            view.setBackgroundResource(Constants.CURRENT_CIRCLE);
        } else if (dayType == Constants.DAY_TYPE.DEFAULT.ordinal()) {

            view.setBackgroundResource(Constants.DEFAULT_CIRCLE);
            //v.setBackgroundResource(Constants.DEFAULT_CIRCLE);
        }

        if (prishaDateArr != null) {
            if ((prishaDateArr[0] != null && dateStr.equals(Utils.DateToStr(prishaDateArr[0]))) ||
                    (prishaDateArr[1] != null && dateStr.equals(Utils.DateToStr(prishaDateArr[1]))) ||
                    (prishaDateArr[2] != null && dateStr.equals(Utils.DateToStr(prishaDateArr[2])))) {
                view.setBackgroundResource(Constants.PRISHA_CIRCLE);
            }
        }
        if (dateStr.equals(Utils.getNextPeriodDate(bl))){
            view.setBackgroundResource(Constants.PERIOD_CIRCLE);
        }
        if (dayType != Constants.DAY_TYPE.DEFAULT.ordinal() && haveNote) {
            txt.setTextColor(Color.parseColor("#8dc63f"));
        }
    }






    public String getPosDate(int position) {
        return day_string.get(position);
    }
}

