package com.example.abiraami.rideshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.datatype.Duration;

/**
 * Created by Abiraami on 11/30/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    //private static final String DB_NAME = "SaiBaba.db";
    private static final String DB_NAME = "RideShare.db";
    private static final int DB_VERSION = 1;
    private static DbHelper myDb = null;
    private SQLiteDatabase db;

    /*private static String dbTableName =null;
    private static String createSessionTable=null ;*/
    public static final String DB_SESSION_TABLE = "Session";
    private static final String CreateSessionTable ="create table "
            + DB_SESSION_TABLE
            + "(ID integer primary key autoincrement, StartPoint text,EndPoint text, NoSpot integer,TravelDate integer); ";
    RideDetails[] dbSessions;

    //Context c;
    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //this.c=context;
        //db=this.getWritableDatabase();
        open();
    }

    public static DbHelper getInstance(Context context) {
        if (myDb == null) {

            myDb = new DbHelper(context);
        }

        return myDb;
    }

    /*public void onDbInitialize(String tableName,String createQuery)
    {
        dbTableName=tableName;
        createSessionTable=createQuery;

    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateSessionTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DB_SESSION_TABLE);
        onCreate(db);
    }
//this method does not work. becuase this uses only "getWritableDatabase" for both reading and writing.
    //alt method: use readable and writableDb accordingly where ever needed.
    private void open() throws SQLiteException {
         db = getWritableDatabase();
    }

    public long addData(RideDetails details) {
        //SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("StartPoint", details.getStartPt());
        values.put("EndPoint", details.getEndPt());
        //Date d = details.getTravelDate();
        //String dateWithoutTime = d.toString().substring(0, 10);

        //values.put("TravelDate",dateWithoutTime);

        values.put("TravelDate",details.getTravelDate().getTime());
        values.put("NoSpot",details.getNoSpot());

        long rowId = db.insert(DB_SESSION_TABLE, null, values);

        return rowId;

    }

    public RideDetails[] getData() {
        //SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RideDetails> arrayData = new ArrayList<>();

        Cursor cursor = db.query(DB_SESSION_TABLE, null, null, null, null,
                null, null);
        /*"create table "
                + dbTableName
                + "(ID integer primary key autoincrement, StartPoint text,EndPoint text, NoSpot integer,TravelDate integer); "*/
        if (cursor.moveToFirst()) {
            while (true) {
                RideDetails sessionData = new RideDetails();

                sessionData.setStartPt(cursor.getString(cursor.getColumnIndex("StartPoint")));

                sessionData.setEndPt( cursor.getString(cursor.getColumnIndex("EndPoint")));

                /*Date d=new Date(cursor.getLong(cursor.getColumnIndex("TravelDate")));
                String dateWithoutTime = d.toString().substring(0, 10);

                sessionData.setTravelDate("TravelDate",dateWithoutTime);*/

                sessionData.setTravelDate(new Date(cursor.getLong(cursor.getColumnIndex("TravelDate"))));


                sessionData.setNoSpot(cursor.getInt(cursor.getColumnIndex("NoSpot")));

                arrayData.add(sessionData);
                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }

        cursor.close();

        dbSessions = new RideDetails[arrayData.size()];

        arrayData.toArray(dbSessions);

        return dbSessions;
    }

    public RideDetails[] searchData(String startPt, String endPt) {
        getData();
        ArrayList<RideDetails> arrayData = new ArrayList<>();
        for (int i = 0; i < dbSessions.length; i++)
        {
            if((startPt.equalsIgnoreCase(dbSessions[i].getStartPt()))&&(endPt.equalsIgnoreCase(dbSessions[i].getEndPt())))
            {
                arrayData.add(dbSessions[i]);
            }

        }
        RideDetails[] searchResult=new RideDetails[arrayData.size()];
        arrayData.toArray(searchResult);
        return searchResult;
    }

    /*public void updateSession(long id, String name, Date date) {
        ContentValues values = new ContentValues();

        values.put("Name", name);
        values.put("StartDate", date.getTime());

        db.update(dbTableName, values, "ID=" + id, null);
    }

    public void deleteSession(long id) {
        db.delete(dbTableName, "ID=" + id, null);
    }*/

    public void deleteSessions() {
        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_SESSION_TABLE, null, null);
    }
}
