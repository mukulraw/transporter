package com.app.onnwaytransporter.otp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SharedData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userinfo.db";
    public static final String TABLE_NAME = "userinfo_table";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "MOBILE";
    public static final String COL_3 = "TYPE";
    public static final String COL_4 = "CITY";
    public static final String COL_5 = "TRANSPORT";



    public SharedData(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (NAME TEXT, MOBILE TEXT, TYPE TEXT, CITY TEXT, TRANSPORT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String mobile, String type, String city, String transport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, mobile);
        contentValues.put(COL_3, type);
        contentValues.put(COL_4, city);
        contentValues.put(COL_5, transport);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String name, String mobile, String type, String city, String transport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, mobile);
        contentValues.put(COL_3, type);
        contentValues.put(COL_4, city);
        contentValues.put(COL_5, transport);
        db.update(TABLE_NAME, contentValues, "MOBILE = ?", new String[] { mobile });
        return true;
    }

    public Integer deleteData (String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "MOBILE = ?", new String[] { mobile });
    }
}
