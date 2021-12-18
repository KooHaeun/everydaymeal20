package com.example.everydaymeal.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.everydaymeal.EverydayMealDB;
import com.example.everydaymeal.MainActivity;
import com.example.everydaymeal.R;
import com.example.everydaymeal.third;

public class HomeCalFragment extends Fragment {//완료

    EverydayMealDB everydayMealDB;
    SQLiteDatabase db;
    TextView caltv, text_home, fulltv;
    EditText calEdit, menuEdit;
    Button mealTimebtn, saveBtn;
    RadioGroup sortRg, fullRg;
    DatePicker date;
    TimePicker time;
    String day="", dayTime="", menu="", sort="", beforeday;
    int calorie=0, full=0, h, m;

    public static third newInstance() {
        third fragment = new third();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_cal, container, false);
        everydayMealDB = new EverydayMealDB(getContext());
        db = everydayMealDB.getReadableDatabase();
        caltv = view.findViewById(R.id.caltv);
        text_home = view.findViewById(R.id.text_home);
        calEdit = view.findViewById(R.id.calEdit);
        menuEdit = view.findViewById(R.id.menuEdit);
        sortRg = view.findViewById(R.id.sortRg);
        fullRg = view.findViewById(R.id.fullRg);
        mealTimebtn = view.findViewById(R.id.mealTimebtn);
        saveBtn = view.findViewById(R.id.saveBtn);
        fulltv = view.findViewById(R.id.fullTv);

        if(MainActivity.useCal==1){
            text_home.setText("-오늘도 건강한 하루-");
            caltv.setVisibility(View.VISIBLE);
            calEdit.setVisibility(View.VISIBLE);
            fulltv.setVisibility(View.GONE);
            fullRg.setVisibility(View.GONE);
        }else{
            caltv.setVisibility(View.GONE);
            calEdit.setVisibility(View.GONE);
            fulltv.setVisibility(View.VISIBLE);
            fullRg.setVisibility(View.VISIBLE);
        }
        mealTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder[] dlg = {new AlertDialog.Builder(getContext())};
                View mealTimeDlg = View.inflate(getContext(), R.layout.mealtime_dialog,null);

                date = mealTimeDlg.findViewById(R.id.date);
                time = mealTimeDlg.findViewById(R.id.time);

                dlg[0].setTitle("식사시간 선택");
                dlg[0].setView(mealTimeDlg);
                dlg[0].setIcon(R.drawable.main);
                date.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if((monthOfYear+1)<9){
                            if(dayOfMonth<9)
                                day =String.valueOf(year)+".0"+String.valueOf(monthOfYear+1)+".0"+String.valueOf(dayOfMonth);
                            else
                                day =String.valueOf(year)+".0"+String.valueOf(monthOfYear+1)+"."+String.valueOf(dayOfMonth);

                        }
                        else{
                            if(dayOfMonth<9)
                                day =String.valueOf(year)+".0"+String.valueOf(monthOfYear+1)+".0"+String.valueOf(dayOfMonth);
                            else
                                day =String.valueOf(year)+".0"+String.valueOf(monthOfYear+1)+"."+String.valueOf(dayOfMonth);
                        }


                        if(monthOfYear+1==5||monthOfYear+1==7||monthOfYear+1==10||monthOfYear+1==12){
                                if(dayOfMonth==1){
                                    dayOfMonth = 30;
                                }
                                else{
                                    monthOfYear+=1;
                                    dayOfMonth-=1;
                                }

                                if(monthOfYear<9){
                                    if(dayOfMonth<9)
                                        beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+".0"+String.valueOf(dayOfMonth);
                                    else
                                        beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+"."+String.valueOf(dayOfMonth);
                                }
                                else{
                                    if(dayOfMonth<9)
                                        beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+".0"+String.valueOf(dayOfMonth);
                                    else
                                        beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+"."+String.valueOf(dayOfMonth);
                                }
                        }else if(monthOfYear+1==1||monthOfYear+1==2||monthOfYear+1==4||monthOfYear+1==6||monthOfYear+1==8||monthOfYear+1==9||monthOfYear+1==11){
                            if(day==(String.valueOf(year)+"01.01"))
                                beforeday = String.valueOf(year-1)+"12.31";
                            else if(dayOfMonth==1){
                                dayOfMonth = 30;
                            }
                            else{
                                monthOfYear+=1;
                                dayOfMonth-=1;
                            }
                            if(monthOfYear<9){
                                if(dayOfMonth<9)
                                    beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+".0"+String.valueOf(dayOfMonth);
                                else
                                    beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+"."+String.valueOf(dayOfMonth);

                                Log.d("month", beforeday);
                            }
                            else{
                                if(dayOfMonth<9)
                                    beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+".0"+String.valueOf(dayOfMonth);
                                else
                                    beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+"."+String.valueOf(dayOfMonth);
                            }
                        }else if(monthOfYear+1==3){
                            if(dayOfMonth==1){
                                dayOfMonth =28;
                            }
                            else{
                                monthOfYear+=1;
                                dayOfMonth-=1;
                            }
                            if(dayOfMonth<9)
                                beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+".0"+String.valueOf(dayOfMonth);
                            else
                                beforeday =String.valueOf(year)+".0"+String.valueOf(monthOfYear)+"."+String.valueOf(dayOfMonth);
                        }
                    }
                });

                time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay<10&&minute<10)
                            dayTime = "0"+hourOfDay+":0"+minute;
                        else if(hourOfDay<10)
                            dayTime = "0"+hourOfDay+":"+minute;
                        else if (minute<10)
                            dayTime = hourOfDay+":0"+minute;
                        else
                            dayTime = hourOfDay+":"+minute;
                        h=hourOfDay; m= minute;
                    }
                });
                dlg[0].setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mealTimebtn.setText(day+"/"+dayTime);
                    }
                });
                dlg[0].show();
            }
        });
        sortRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (sortRg.getCheckedRadioButtonId()){
                    case R.id.sortRb1:
                        sort="식사";
                        break;
                    case R.id.sortRb2:
                        sort="간식";
                        break;
                    case R.id.sortRb3:
                        sort="야식";
                        break;
                }

            }
        });

        fullRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (fullRg.getCheckedRadioButtonId()){
                    case R.id.fullRb1:
                        full=1;
                        break;
                    case R.id.fullRb2:
                        full=2;
                        break;
                    case R.id.fullRb3:
                        full=3;
                        break;
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = everydayMealDB.getWritableDatabase();
                menu = menuEdit.getText().toString();
                if(dayTime.equals("")||day.equals("")||menu.equals("")||sort.equals("")){
                        androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                        dlg.setTitle("경고");
                    dlg.setIcon(R.drawable.main);
                        dlg.setMessage("아직 선택하지 않은 항목이 있습니다. 모두 선택해주세요");
                        dlg.setPositiveButton("네", null);
                        dlg.show();
                }
                else{
                    if((calEdit.getVisibility()==View.VISIBLE && calEdit.getText().toString().equals(""))||(fullRg.getVisibility()==View.VISIBLE && full==0)){
                        Log.d("menu", String.valueOf(caltv.getVisibility()));
                        androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                        dlg.setTitle("경고");
                        dlg.setIcon(R.drawable.main);
                        dlg.setMessage("아직 선택하지 않은 항목이 있습니다. 모두 선택해주세요");
                        dlg.setPositiveButton("네", null);
                        dlg.show();
                    }
                    else {
                        if(calEdit.getVisibility()==View.VISIBLE) {
                            calorie = Integer.parseInt(calEdit.getText().toString());
                            db.execSQL("CREATE TABLE IF NOT EXISTS MEALCAL(id TEXT NOT NULL, day TEXT NOT NULL, time TEXT NOT NULL, sort TEXT, menu TEXT, calories INTEGER, PRIMARY KEY(id, day, time));");
                            if(sort=="야식"&&(h>=0&&h<=5)){
                                if(h==5&&m>0)
                                    db.execSQL("INSERT INTO MEALCAL(id, day, time, sort, menu, calories) VALUES ('" + MainActivity.id + "', '" + day + "', '" + dayTime + "', '" + sort + "', '" + menu + "', '" + calorie + "');");
                                else
                                    db.execSQL("INSERT INTO MEALCAL(id, day, time, sort, menu, calories) VALUES ('" + MainActivity.id + "', '" + beforeday + "', '" + dayTime + "', '" + sort + "', '" + menu + "', '" + calorie + "');");
                            }
                            else
                                db.execSQL("INSERT INTO MEALCAL(id, day, time, sort, menu, calories) VALUES ('" + MainActivity.id + "', '" + day + "', '" + dayTime + "', '" + sort + "', '" + menu + "', '" + calorie + "');");

                        }
                        else{
                            db.execSQL("CREATE TABLE IF NOT EXISTS MEAL(id TEXT NOT NULL, day TEXT NOT NULL, time TEXT NOT NULL, sort TEXT, menu TEXT, satiety INTEGER, PRIMARY KEY(id, day, time));");
                            if(sort=="야식"&&(h>=0&&h<=5)){
                                if(h==5&&m>0)
                                    db.execSQL("INSERT INTO MEAL(id, day, time, sort, menu, satiety) VALUES ('" + MainActivity.id + "', '" + day + "', '" + dayTime + "', '" + sort + "', '" + menu + "', '" + full + "');");
                                else
                                    db.execSQL("INSERT INTO MEAL(id, day, time, sort, menu, satiety) VALUES ('" + MainActivity.id + "', '" + beforeday + "', '" + dayTime + "', '" + sort + "', '" + menu + "', '" + full + "');");
                            }
                            else
                                db.execSQL("INSERT INTO MEAL(id, day, time, sort, menu, satiety) VALUES ('" + MainActivity.id + "', '" + day + "', '" + dayTime + "', '" + sort + "', '" + menu + "', '" + full + "');");
                        }
                        
                        Toast.makeText(getActivity(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                        db.close();
                        mealTimebtn.setText("선택");
                        sortRg.clearCheck();
                        menuEdit.setText("");
                        if(calEdit.getVisibility()==View.VISIBLE)
                            calEdit.setText("");
                        else
                            fullRg.clearCheck();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}