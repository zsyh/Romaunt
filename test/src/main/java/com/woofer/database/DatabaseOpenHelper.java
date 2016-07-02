package com.woofer.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    private static String dbName = "Note";
    private static int dbversion = 8;

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, null, version);
    }

    public DatabaseOpenHelper(Context context){
        super(context,dbName, null, dbversion );
    }
    @Override
    /*ststus 公开 私密状态
    */
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE Note("
                    +"ID integer PRIMARY KEY AUTOINCREMENT,"
                    +"DateTime text,"
                    +"Note text,"
                    +"Theme text,"
                    +"Lable text,"
                    +"Status integer,"
                    +"uploaded integer)";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Note");
        onCreate(db);
    }
}