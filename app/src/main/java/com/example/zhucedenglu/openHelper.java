package com.example.zhucedenglu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class openHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table User("
            +"id integer primary key autoincrement,"
            +"username text,"
            +"userpwd text)";

    public openHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
