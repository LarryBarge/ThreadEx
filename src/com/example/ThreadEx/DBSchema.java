package com.example.ThreadEx;

import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: Larry
 * Date: 10/7/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */

/*Tip:
    It is good practice to create a separate class per table. This
    class defines static onCreate() and onUpgrade() methods. These
    methods are called in the corresponding methods of SQLiteOpenHelper.
    This way your implementation of SQLiteOpenHelper stays readable, even
    if you have several tables.*/
public class DBSchema {

    /*Specifying Various components of the SQLite DB*/

    public static final String DATABASE_NAME = "codes";
    public static final int DATABASE_VERSION = 1;
    public static final String SORT_ASC = " ASC";
    public static final String SORT_DESC = " DESC";
    public static final String[] ORDERS = {SORT_ASC,SORT_DESC};
    public static final int OFF = 0;
    public static final int ON = 1;

     /*Creates the database table for codes scanned into db*/
    public static final class ScanSchema implements BaseColumns {
        public static final String TABLE_NAME = "scancodes";
        public static final String COLUMN_VALUE = "scan_value";
        public static final String COLUMN_DATE = "date";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUE + " TEXT, " +
                COLUMN_DATE + " LONG NOT NULL);";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class UserSchema implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER = "user_id";
        public static final String COLUMN_PASS = "pass_value";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " TEXT NOT NULL, " +
                COLUMN_PASS + " TEXT" + ");";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
