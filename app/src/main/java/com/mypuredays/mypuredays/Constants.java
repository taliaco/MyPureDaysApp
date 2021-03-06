package com.mypuredays.mypuredays;

import android.graphics.Color;
import android.provider.BaseColumns;

public class Constants implements BaseColumns {

    public static final String PREFS_NAME = "MyPrefsFile";

    public static final int DEFAULT_SPINNER = 0;
    public static final int DEFAULT_SPINNER_COUNTER_PURE_DAY = 4;
    public static final int DEFAULT_SPINNER_TYPE_ONA = 1;

    public static final boolean EXIST_REMINDER = false;

    //name of table
    public static final String TABLE_DAY = "dayTable";
    public static final String TABLE_DAY_TYPE = "dayTypeTable";
    public static final String TABLE_CLEAR_DAY_TYPE = "clearDayTypeTable";
    public static final String TABLE_DEFINITION = "definitionTable";

    //Column for day table
    public static final String COL_DATE = "dateColumn";
    public static final String COL_DAY_TYPE = "datTypeColumn";
    public static final String COL_NOTES = "notesColumn";
    public static final String COL_ONA = "onaColumn";
    public static final String[] TABLE_DAY_COLUMNS = {Constants._ID, Constants.COL_DATE, Constants.COL_DAY_TYPE, Constants.COL_NOTES, Constants.COL_ONA};

    //Column for dayType table
    public static final String COL_ID_DAY_TYPE = "idDayTypeColumn";
    public static final String COL_TYPE_DAY_TYPE = "typeDayTypeColumn";
    public static final String[] TABLE_DAY_TYPE_COLUMNS = {Constants._ID, Constants.COL_ID_DAY_TYPE, Constants.COL_TYPE_DAY_TYPE};

    //Column for clearDayType table
    public static final String COL_ID_CLEAR_DAY_TYPE = "idClearDayTypeColumn";
    public static final String COL_TYPE_CLEAR_DAY_TYPE = "typeClearDayTypeColumn";
    public static final String[] TABLE_CLEAR_DAY_TYPE_COLUMNS = {Constants._ID, Constants.COL_ID_DAY_TYPE, Constants.COL_TYPE_DAY_TYPE};

    //Column for notification table
    public static final String COL_MIN_PERIOD_LENGTH = "minPeriodLengthColumn";
    public static final String COL_REGULAR = "regularColumn";
    public static final String COL_PRISHA_DAYS = "prishaDaysColumn";
    public static final String COL_PERIOD_LENGTH = "periodLengthColumn";
    public static final String COL_MIKVE_NOTIFICATION = "mikveNotificationColumn";
    public static final String COL_CLEAN_NOTIFICATION = "cleanNotificationColumn";
    public static final String COL_COUNT_CLEAN = "countCleanColumn";
    public static final String COL_DAILY_NOTIFICATION = "dailyNotificationColumn";
    public static final String COL_TYPE_PERIOD = "typePeriodColumn";
    public static final String[] TABLE_DEFINITION_COLUMNS = {Constants._ID, Constants.COL_MIN_PERIOD_LENGTH, Constants.COL_REGULAR, Constants.COL_PRISHA_DAYS,
            Constants.COL_PERIOD_LENGTH, Constants.COL_COUNT_CLEAN,
            Constants.COL_CLEAN_NOTIFICATION, Constants.COL_MIKVE_NOTIFICATION, Constants.COL_TYPE_PERIOD};
    public static final String MONTH_TITLE_FORMAT = "MM/yyyy";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DISPLAY = "dd/MM/yyyy";
    public static final String DATE_SPLITTER = "-";
    public static final int MONTH_POSITION = 1;
    public static final int DAY_POSITION = 2;
    public static final int YEAR_POSITION = 0;

    //ENUM
    public enum DEF_TYPE {
        INTEGER, BOOLEAN, STRING, DATE
    }

    public enum DAY_TYPE {
        DEFAULT, START_LOOKING, END_LOOKING, PERIOD, PRISHA, MIKVEH, CLEAR_DAY, NEXT_PERIOD
    }

    public enum ONA_TYPE {
        DEFAULT, UNKNOWN, DAY, NIGHT
    }

    //calendar day design
    public static int PERIOD_CIRCLE = R.drawable.period_circle;
    public static int CLEAR_CIRCLE = R.drawable.clear_circle;
    public static int PRISHA_CIRCLE = R.drawable.prisha_circle;
    public static int OTHER_CIRCLE = R.drawable.other_circle;
    public static int CURRENT_CIRCLE = R.drawable.current_circle;
    public static int DEFAULT_CIRCLE = R.drawable.default_circle;

    public static int CLEAR_DAYS_LENGTH = 7;

    //drop down list for definition
    public static final String[] MIN_PERIOD_LENGTH_SPINNER = new String[]{"4", "5"};
    public static final String[] PERIOD_LENGTH_SPINNER = new String[]{ "4", "5", "6", "7"};
    public static final String[] CLEAR_DAY_NOTIFICATION_SPINNER = new String[]{"ראשון ואחרון", "3 ימים ראשונים", "פעם ביום", "פעמיים ביום", "לא רלוונטי"};
    public static final String[] TYPE_ONA_SPINNER = new String[]{"לא רלוונטי", "לא ידוע", "יום", "לילה"};

}
