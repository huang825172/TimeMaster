package com.zzs.timemaster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper{
    public static final String DB_NAME= "event_management"; // 数据库文件名称
    public static final String TABLE_NAME= "event";
    public static final String EVENT_NAME= "event_name";
    public static final String EVENT_LOCATION= "event_location";
    public static final String TIME="time";
    public static final String IMPORTANT= "important";
    public static final String URGENT= "urgent";
    public MyOpenHelper(@Nullable Context context, @Nullable String name, @Nullable CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("drop TABLE "+TABLE_NAME);
        sqLiteDatabase.execSQL("create table if not exists "+TABLE_NAME
                +" ("+EVENT_NAME+" varchar primary key,"
                //primary key主键
                + EVENT_LOCATION+" varchar,"
                + TIME+" int,"
                +IMPORTANT+" boolean,"
                +URGENT+" boolean)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
