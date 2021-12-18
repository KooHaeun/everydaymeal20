package com.example.everydaymeal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class infoActivity extends AppCompatActivity {
        EverydayMealDB everydayMealDB;
        SQLiteDatabase db;
        TextView alarm, breakfast, lunch, dinner;
        int alaramTime;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.info_activity);
            alarm = findViewById(R.id.alarm);
            breakfast = findViewById(R.id.breakTime);
            lunch = findViewById(R.id.lunchTime);
            dinner = findViewById(R.id.dinnerTime);

            everydayMealDB = new EverydayMealDB(infoActivity.this);
            db = everydayMealDB.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM ALARM WHERE id == '"+MainActivity.id+"';", null);
            c.moveToFirst();
            alaramTime = c.getInt(2);
            switch (alaramTime){
                case 1:
                    alarm.setText("알람을 이용하지 않습니다.");
                    break;
                case -15:
                    alarm.setText("식사 시간 15분 전에 알람이 울립니다.");
                    break;
                case -5:
                    alarm.setText("식사 시간 5분 전에 알람이 울립니다.");
                    break;
                case 0:
                    alarm.setText("식사 시간 정각에 알람이 울립니다.");
                    break;
                case 10:
                    alarm.setText("식사 시간 10분 후에 알람이 울립니다.");
                    break;
            }
            breakfast.setText(c.getString(3));
            lunch.setText(c.getString(4));
            dinner.setText(c.getString(5));
        }
}
