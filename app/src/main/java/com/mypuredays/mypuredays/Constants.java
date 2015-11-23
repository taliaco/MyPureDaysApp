package com.mypuredays.mypuredays;

import android.provider.BaseColumns;

/**
 * Created by yael on 23/11/2015.
 */
public class Constants implements BaseColumns {

    public static final String PREFS_NAME = "MyPrefsFile";

    //name of table
    public static final String TABLE_NAME_DAY = "dayTable";
    public static final String TABLE_NAME_DAY_TYPE = "dayTypeTable";
    public static final String TABLE_NAME_CLEAR_DAY_TYPE = "clearDayTypeTable";
    public static final String TABLE_NAME_DEFINITION = "definitionTable";

    //Column for day table
    public static final String COLUMN_NAME_DATE = "dateColumn";
    public static final String COLUMN_NAME_DAY_TYPE = "datTypeColumn";
    public static final String COLUMN_NAME_NOTES = "notesColumn";

    //Column for dayType table
    public static final String COLUMN_NAME_ID_DAY_TYPE = "idDayTypeColumn";
    public static final String COLUMN_NAME_TYPE_DAY_TYPE = "typeDayTypeColumn";

    //Column for clearDayType table
    public static final String COLUMN_NAME_ID_CLEAR_DAY_TYPE = "idClearDayTypeColumn";
    public static final String COLUMN_NAME_TYPE_CLEAR_DAY_TYPE = "typeClearDayTypeColumn";

    //Column for notification table
    public static final String COLUMN_NAME_MIN_PERIOD_LENGTH = "minPeriodLengthColumn";
    public static final String COLUMN_NAME_REGULAR = "regularColumn";
    public static final String COLUMN_NAME_PRISHA_DAYS = "prishaDaysColumn";
    public static final String COLUMN_NAME_PERIOD_LENGTH = "periodLengthColumn";
    public static final String COLUMN_NAME_OVULATION_NOTIFICATION = "ovulationNotificationColumn";
    public static final String COLUMN_NAME_CLEAN_NOTIFICATION = "cleanNotificationColumn";
    public static final String COLUMN_NAME_COUNT_CLEAN = "countCleanColumn";
    public static final String COLUMN_NAME_DAILY_NOTIFICATION = "dailyNotificationColumn";



}
