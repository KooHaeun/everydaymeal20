package com.example.everydaymeal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EverydayMealDB extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_ALARM = "CREATE TABLE IF NOT EXISTS ALARM(inDay TEXT PRIMARY KEY, useCal INTEGER NOT NULL, use INTEGER NOT NULL, breakfast TEXT, lunch TEXT, dinner TEXT)";

    public EverydayMealDB(Context c){
        super(c, "EverydayMealDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALARM);
        db.execSQL("INSERT INTO ALARM(inDay, useCal, use, breakfast, lunch, dinner) values ('2021.09.20.13:52', 0, 1, '8:00', '12:00', '18:00');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ALRAM");
        db.execSQL("DROP TABLE IF EXISTS MEAL");
        db.execSQL("DROP TABLE IF EXISTS MEALCAL");
        onCreate(db);
    }
}
