package com.example.everydaymeal;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.everydaymeal.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static Integer useCal;
    public static String id;
    EverydayMealDB everydayMealDB;
    SQLiteDatabase db;


    NotificationManager manager;
    NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {//완료
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("2017081001 임기범");
        Intent i = getIntent();
        id = i.getStringExtra("id");
        useCal = i.getIntExtra("useCal", 0);
        everydayMealDB = new EverydayMealDB(MainActivity.this);
        db = everydayMealDB.getWritableDatabase();;

        /*Cursor c = db.rawQuery("SELECT * FROM ALARM WHERE id =='" + id + "';", null);
        c.moveToFirst();
        int alaramTime = c.getInt(2);
        String morning = c.getString(3);
        String breakfast[] = morning.split(":");

        switch(alaramTime){//firebase에서 알람시간 가져오기
            case 0:
                createNotificationChannel("아침 식사시간입니다! 오늘 하루도 열심히 식사기록을 해주세요");
                alarmBroadcastReceiver(Integer.parseInt(breakfast[0]), Integer.parseInt(breakfast[1]));
                break;
            case -15:
                createNotificationChannel("아침 식사시간 15분전입니다! 오늘 하루도 열심히 식사기록을 해주세요");
                if(!second.breakfast.equals("-")){
                    if(Integer.parseInt(breakfast[1])<15)
                        alarmBroadcastReceiver(Integer.parseInt(breakfast[0])-1,(60-15-Integer.parseInt(breakfast[1])));
                    else
                        alarmBroadcastReceiver(Integer.parseInt(breakfast[0]),(Integer.parseInt(breakfast[1])-15));}
                break;
            case -5:
                createNotificationChannel("아침 식사시간 5분전입니다! 오늘 하루도 열심히 식사기록을 해주세요");
                if(!breakfast.equals("-")) {
                    if (Integer.parseInt(breakfast[1]) < 5)
                        alarmBroadcastReceiver(Integer.parseInt(breakfast[0]) - 1 , (60 - 5 - Integer.parseInt(breakfast[1])));
                    else
                        alarmBroadcastReceiver(Integer.parseInt(breakfast[0]) , (Integer.parseInt(breakfast[1]) - 5));
                }
                break;
            case 1:
                break;
            case 10:
                createNotificationChannel("아침 식사시간 10분 후입니다! 오늘 하루도 열심히 식사기록을 해주세요");
                if(!breakfast.equals("-")) {
                    if (Integer.parseInt(breakfast[1]) > 50)
                        alarmBroadcastReceiver(Integer.parseInt(breakfast[0]) + 1 , (Integer.parseInt(breakfast[1]) + 10 - 60));
                    else
                        alarmBroadcastReceiver(Integer.parseInt(breakfast[0]) , (Integer.parseInt(breakfast[1]) - 15));
                }
                break;
        }*/


        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_graph, R.id.navigation_setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void showNoti() {
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //버전 오레오 이상일 경우
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel
                    (new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            // 하위 버전일 경우
        } else {
            builder = new NotificationCompat.Builder(this);
        }
        // 알림창 제목
        builder.setContentTitle("식사시간");
        // 알림창 메시지
        builder.setContentText("식사시간 알림입니다!");
        Notification notification = builder.build();
        // 알림창 실행
        manager.notify(1, notification);
    }



}