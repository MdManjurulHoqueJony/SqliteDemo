package com.wordpress.jonyonandroidcraftsmanship.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertData(String name, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // INSERT INTO JONYZTABLE (Name) VALUES('Jony');
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME, name);
        contentValues.put(DBHelper.PASSWORD, password);
        long id = db.insert(DBHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //SELECT * from JONYZTABLE
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int uId = cursor.getInt(cursor.getColumnIndex(DBHelper.UID));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
            String password = cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD));
            stringBuffer.append(uId + " " + name + " " + password + "\n");
        }
        return stringBuffer.toString();
    }

    public String getData(String name, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //SELECT _id from JONYZTABLE WHERE Name=? AND Password=? ;
        String[] columns = {DBHelper.UID};
        String[] selectionArgs = {name, password};
        Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, DBHelper.NAME + " =? AND " + DBHelper.PASSWORD + " =?", selectionArgs, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int uId = cursor.getInt(cursor.getColumnIndex(DBHelper.UID));
            stringBuffer.append(uId + "\n");
        }
        return stringBuffer.toString();
    }

    public int updateName(String oldName,String newName){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //UPDATE JONYZTABLE SET Name='Jony' WHERE Name=? 'TEST'
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME, newName);
        String[] whereArgs={oldName};
        int count=db.update(DBHelper.TABLE_NAME,contentValues,DBHelper.NAME+" =?",whereArgs);
        return count;
    }

    public int deleteRow(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //DELETE * FROM JONYZTABLE WHERE Name=? 'Jony'
        String[] whereArgs={"Jony"};
        int count=db.delete(DBHelper.TABLE_NAME,DBHelper.NAME+" =?",whereArgs);
        return count;
    }

    private static class DBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "jonyzdatabase";
        private static final String TABLE_NAME = "JONYZTABLE";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String PASSWORD = "Password";
        //CREATE TABLE JONYZTABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255));
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), "
                + PASSWORD + " VARCHAR(255));";
        //DROP TABLE IF EXISTS JONYZTABLE ;
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Logger.log("Constructor Call");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Logger.log("Table Create");
            } catch (SQLException e) {
                Logger.log(e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
                Logger.log("Table Upgrade");
            } catch (SQLException e) {
                Logger.log(e.toString());
            }
        }
    }
}