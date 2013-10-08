package com.example.ThreadEx;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Sasquatch
 * Date: 10/8/13
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBSingleton {
    private static final DBSingleton INSTANCE = new DBSingleton();

    private SQLiteDatabase mDB=null;
    private DBOpenHelper mDBOpenHelper = null;

    //Setup Default Constructor that does nothing
    public DBSingleton(){

    }

    /**
     * @param context context of application being passed
     *
     * */
    public SQLiteDatabase getDatabase(Context context){

        if(mDB == null || mDBOpenHelper == null){ //Check to see if Database if created and the Helper object
            mDBOpenHelper = new DBOpenHelper(context);
            mDB = mDBOpenHelper.getReadableDatabase();
        }

        if(!mDB.isOpen()){
            mDB = mDBOpenHelper.getReadableDatabase();
        }
        return mDB;

    }

    public static DBSingleton getInstance(){
        return INSTANCE;
    }

}
