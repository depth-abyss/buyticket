package com.example.zhucedenglu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sqliteDB {

    public static final String DB_NAME ="sqlite_dbname";

    public static final int VERSION = 1;

    private static sqliteDB sqliteDB;

    private SQLiteDatabase db;

    private sqliteDB(Context context){
        openHelper dbHelper = new openHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static sqliteDB getInstance(Context context){
        if (sqliteDB == null){
            sqliteDB = new sqliteDB(context);
        }
        return sqliteDB;
    }

    public int savaUser(User user){
        if (user != null){
            Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{user.getUsername().toString()});
            if (cursor.getCount()>0){
                return -1;
            }else {
                try{
                    db.execSQL("insert into User(username,userpwd) values(?,?)",new String[]{user.getUsername().toString(),user.getUserpwd().toString()});
                }catch (Exception e){
                    Log.d("错误",e.getMessage().toString());
                }
                return 1;
            }
        }
        else {
            return 0;
        }
    }

    public List<User> loadUser(){
        List<User> list = new ArrayList<User>();
        Cursor cursor = db
                .query("User",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUsername(cursor.getString(cursor
                        .getColumnIndex("username")));
                user.setUserpwd(cursor.getString(cursor
                        .getColumnIndex("userpwd")));
                list.add(user);
            }while(cursor.moveToNext());
        }
        return list;
    }

    public int Quer(String pwd,String name)
    {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        Cursor cursor = db.rawQuery("select * from User where userpwd=? and username=?",new String[]{pwd,name});

        if (cursor.getCount()>0)
        {
            Cursor pwdcursor =db.rawQuery("select * from User where userpwd=? and username=?",new String[]{pwd,name});
            if (pwdcursor.getCount()>0)
            {
                return 1;
            }
            else{
                return -1;
            }
        }
        else {
            return 0;
        }
    }
}

