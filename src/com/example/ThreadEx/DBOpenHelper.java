package com.example.ThreadEx;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Sasquatch
 * Date: 10/7/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private final SharedPreferences mPrefs;

    public DBOpenHelper(Context context, SharedPreferences prefs){
        super(context, DBSchema.DATABASE_NAME, null, DBSchema.DATABASE_VERSION);
        mPrefs = prefs;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create tables in database below
        sqLiteDatabase.execSQL(DBSchema.ScanSchema.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBSchema.UserSchema.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
