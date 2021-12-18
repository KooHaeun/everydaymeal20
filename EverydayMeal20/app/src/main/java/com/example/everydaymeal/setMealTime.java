package com.example.everydaymeal;

import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class setMealTime extends AppCompatActivity {
    EverydayMealDB everydayMealDB;
    SQLiteDatabase db;
    Button save;
    RadioGroup breakfastRg, lunchRg, dinnerRg;
    public static String breakfast="", lunch="", dinner="", id;
    int noMeal = 0, one, two, three;
    Spinner alaramSpin;
    int alaramTime, spin=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_mealtime);

        save = findViewById(R.id.saveBtn);
        everydayMealDB = new EverydayMealDB(setMealTime.this);
        breakfastRg = findViewById(R.id.breakfastRg1);
        lunchRg = findViewById(R.id.lunchRg1);
        dinnerRg = findViewById(R.id.dinnerRg1);
        alaramSpin = findViewById(R.id.alarmSpin);


        breakfastRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (breakfastRg.getCheckedRadioButtonId()){
                    case R.id.breakfastRd11:
                        if(one==1){
                            noMeal--;
                        }
                        one = 0;
                        TimePickerDialog dialog = new TimePickerDialog(setMealTime.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
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
                    case R.id.breakfastRd21:
                        breakfast = "08:00";
                        if(one==1){
                            noMeal--;
                        }
                        one = 0;
                        break;
                    case R.id.breakfastRd31:
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
                    case R.id.lunchRd11:
                        if(two==1){
                            noMeal--;
                        }
                        two=0;
                        TimePickerDialog dialog = new TimePickerDialog(setMealTime.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
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
                    case R.id.lunchRd21:
                        lunch = "12:00";
                        if(two==1){
                            noMeal--;
                        }
                        two=0;
                        break;
                    case R.id.lunchRd31:
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
                    case R.id.dinnerRd11:
                        if(three==1){
                            noMeal--;
                        }
                        three=0;
                        TimePickerDialog dialog = new TimePickerDialog(setMealTime.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
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
                    case R.id.dinnerRd21:
                        dinner = "18:00";
                        if(three==1){
                            noMeal--;
                        }
                        three=0;
                        break;
                    case R.id.dinnerRd31:
                        three=1;
                        dialog(three);
                        dinner = "-";
                        break;
                }
            }
        });
        alaramSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getSelectedItemPosition()){
                    case 0:
                        spin =1;
                        alaramTime = 1;
                        break;
                    case 1:
                        spin =1;
                        alaramTime = -15;
                        break;
                    case 2:
                        spin =1;
                        alaramTime = -5;
                        break;
                    case 3:
                        spin =1;
                        alaramTime = 0;
                        break;
                    case 4:
                        spin =1;
                        alaramTime = 10;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin = 0;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = everydayMealDB.getWritableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM ALARM WHERE id =='"+MainActivity.id+"';", null);
                c.moveToFirst();
                int beforeAl = c.getInt(2);
                if(!breakfast.equals(""))
                    db.execSQL("UPDATE ALARM SET breakfast = '"+breakfast+"' WHERE id == '"+MainActivity.id+"';");
                if(!lunch.equals(""))
                    db.execSQL("UPDATE ALARM SET lunch = '"+lunch+"' WHERE id == '"+MainActivity.id+"';");
                if (!dinner.equals(""))
                    db.execSQL("UPDATE ALARM SET dinner = '"+dinner+"' WHERE id == '"+MainActivity.id+"';");
                if(beforeAl!=alaramTime)
                    db.execSQL("UPDATE ALARM SET use = '"+alaramTime+"' WHERE id == '"+MainActivity.id+"';");
                Toast.makeText(setMealTime.this,"변경되었습니다.",Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }
        });
    }
    public void dialog(int num){
        noMeal+=num;
        if(noMeal>=2){
            AlertDialog.Builder dlg = new AlertDialog.Builder(setMealTime.this);
            dlg.setTitle("경고");
            dlg.setIcon(R.drawable.main);
            dlg.setMessage("과도한 섭식 제한은 건강을 해칠 수 있습니다");
            dlg.setPositiveButton("알겠습니다.", null);
            dlg.show();
        }
    }
}

