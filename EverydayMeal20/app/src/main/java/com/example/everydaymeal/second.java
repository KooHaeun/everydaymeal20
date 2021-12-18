package com.example.everydaymeal;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class second extends Fragment {//완성


    EverydayMealDB everydayMealDB;
    SQLiteDatabase db;
    RadioGroup breakfastRg, lunchRg, dinnerRg;
    public static int chk;
    public static String breakfast, lunch, dinner;
    int noMeal = 0, one, two, three;
    public static String[] checking = {" ", " ", " ", " "};

    public static second newInstance() {
        second fragment = new second();
        Bundle args = new Bundle();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        everydayMealDB = new EverydayMealDB(getContext());
        breakfastRg = view.findViewById(R.id.breakfastRg);
        lunchRg = view.findViewById(R.id.lunchRg);
        dinnerRg = view.findViewById(R.id.dinnerRg);

        breakfast = "08:00";
        lunch = "12:00";
        dinner = "18:00";

        breakfastRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (breakfastRg.getCheckedRadioButtonId()){
                    case R.id.breakfastRb1:
                        checking[0] = "check";
                        if(one==1){
                            noMeal--;
                        }
                        one = 0;
                        TimePickerDialog dialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        if(hourOfDay<10&&minute<10){
                                            breakfast="0"+hourOfDay+":0"+minute;
                                        }
                                        else if(hourOfDay<10){
                                            breakfast="0"+hourOfDay+":"+minute;
                                        }else if(minute<10){
                                            breakfast=hourOfDay+":0"+minute;
                                        }else
                                            breakfast = hourOfDay+":"+minute;
                                    }
                                }, 8, 0, false);
                        dialog.setTitle("아침 식사 시간");
                        dialog.setIcon(R.drawable.main);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();
                        break;
                    case R.id.breakfastRb2:
                        breakfast = "08:00";
                        checking[0] = "check";
                        if(one==1){
                            noMeal--;
                        }
                        one = 0;
                        break;
                    case R.id.breakfastRb3:
                        checking[0] = "check";
                        one = 1;
                        dialog(one);
                        breakfast = "-";
                        break;
                }
            }
        });
        lunchRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (lunchRg.getCheckedRadioButtonId()){
                    case R.id.lunchRb1:
                        checking[1] = "check";
                        if(two==1){
                            noMeal--;
                        }
                        two=0;
                        TimePickerDialog dialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        if(hourOfDay<10&&minute<10){
                                            lunch="0"+hourOfDay+":0"+minute;
                                        }
                                        else if(hourOfDay<10){
                                            lunch="0"+hourOfDay+":"+minute;
                                        }else if(minute<10){
                                            lunch=hourOfDay+":0"+minute;
                                        }
                                        else
                                            lunch = hourOfDay+":"+minute;
                                    }
                                }, 12, 0, false);
                        dialog.setTitle("점심 식사 시간");
                        dialog.setIcon(R.drawable.main);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();
                        break;
                    case R.id.lunchRb2:
                        lunch="12:00";
                        checking[1] = "check";
                        if(two==1){
                            noMeal--;
                        }
                        two=0;
                        break;
                    case R.id.lunchRb3:
                        checking[1] = "check";
                        two=1;
                        dialog(two);
                        lunch = "-";
                        break;
                }
            }
        });

        dinnerRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (dinnerRg.getCheckedRadioButtonId()){
                    case R.id.dinnerRb1:
                        checking[2] = "check";
                        if(three==1){
                            noMeal--;
                        }
                        three=0;
                        TimePickerDialog dialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        if(hourOfDay<10&&minute<10){
                                            dinner="0"+hourOfDay+":0"+minute;
                                        }
                                        else if(hourOfDay<10){
                                            dinner="0"+hourOfDay+":"+minute;
                                        }else if(minute<10) {
                                            dinner = hourOfDay + ":0" + minute;
                                        }else
                                            dinner = hourOfDay+":"+minute;
                                    }
                                }, 18, 0, false);
                        dialog.setTitle("저녁 식사 시간");
                        dialog.setIcon(R.drawable.main);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();
                        break;
                    case R.id.dinnerRb2:
                        dinner = "18:00";
                        checking[2] = "check";
                        if(three==1){
                            noMeal--;
                        }
                        three=0;
                        break;
                    case R.id.dinnerRb3:
                        checking[2] = "check";
                        three=1;
                        dialog(three);
                        dinner = "-";
                        break;
                }
            }
        });
        return view;
    }

    public void dialog(int num){
        noMeal+=num;
        if(noMeal>=2){
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setTitle("경고");
            dlg.setMessage("과도한 섭식 제한은 건강을 해칠 수 있습니다");
            dlg.setIcon(R.drawable.main);
            dlg.setPositiveButton("알겠습니다.", null);
            dlg.show();
        }
    }
}
