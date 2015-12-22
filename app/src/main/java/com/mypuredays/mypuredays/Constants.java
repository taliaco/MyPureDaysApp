package com.mypuredays.mypuredays;

import android.provider.BaseColumns;

/**
 * Created by yael on 23/11/2015.
 */
public class Constants implements BaseColumns {

    public static final String PREFS_NAME = "MyPrefsFile";

    //name of table
    public static final String TABLE_DAY = "dayTable";
    public static final String TABLE_DAY_TYPE = "dayTypeTable";
    public static final String TABLE_CLEAR_DAY_TYPE = "clearDayTypeTable";
    public static final String TABLE_DEFINITION = "definitionTable";

    //Column for day table
    public static final String COL_DATE = "dateColumn";
    public static final String COL_DAY_TYPE = "datTypeColumn";
    public static final String COL_NOTES = "notesColumn";
    public static final String[] TABLE_DAY_COLUMNS = {Constants._ID, Constants.COL_DATE, Constants.COL_DAY_TYPE, Constants.COL_NOTES};

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
    public static final String COL_OVULATION_NOTIFICATION = "ovulationNotificationColumn";
    public static final String COL_CLEAN_NOTIFICATION = "cleanNotificationColumn";
    public static final String COL_COUNT_CLEAN = "countCleanColumn";
    public static final String COL_DAILY_NOTIFICATION = "dailyNotificationColumn";
    public static final String COL_TYPE_PERIOD = "typePeriodColumn";
    public static final String[] TABLE_DEFINITION_COLUMNS = {Constants._ID, Constants.COL_MIN_PERIOD_LENGTH, Constants.COL_REGULAR, Constants.COL_PRISHA_DAYS,
            Constants.COL_PERIOD_LENGTH, Constants.COL_COUNT_CLEAN, Constants.COL_DAILY_NOTIFICATION,
            Constants.COL_CLEAN_NOTIFICATION, Constants.COL_OVULATION_NOTIFICATION, Constants.COL_TYPE_PERIOD};

    public static final String DATE_FORMAT = "dd-MM-yyyy";



    public enum DEF_TYPE {
        INTEGER, BOOLEAN, STRING, DATE
    }


    public enum DAY_TYPE{
        DEFAULT, START_LOOKING, END_LOOKING, START_LOOKING_DAY, START_LOOKING_NIGHT, END_LOOKING_DAY, END_LOOKING_NIGHT,
    }

}
