package com.example.everydaymeal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class third extends Fragment {//완성


    EverydayMealDB everydayMealDB;
    SQLiteDatabase db;
    RadioGroup kcalRg;
    Spinner alarmSpin;
    Button complete;
    Integer  cnt=0;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String alaramTime, useCal;
    DatabaseReference alarmDB =  FirebaseDatabase.getInstance().getReference();


    public static third newInstance() {
        third fragment = new third();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        kcalRg = view.findViewById(R.id.kcalRg);
        alarmSpin = view.findViewById(R.id.alarmSpin);
        complete = view.findViewById(R.id.complete);



        alarmSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getSelectedItemPosition()){
                    case 0:
                        alaramTime = "1";
                        break;
                    case 1:
                        alaramTime="-15";
                        break;
                    case 2:
                        alaramTime = "-5";
                        break;
                    case 3:
                        alaramTime = "0";
                        break;
                    case 4:
                        alaramTime = "10";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                alaramTime = "0";
            }
        });
        kcalRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (kcalRg.getCheckedRadioButtonId()){
                    case R.id.kcalRb1:
                        second.checking[3]="check";
                        useCal="1";
                        break;
                    case R.id.kcalRb2:
                        second.checking[3]="check";
                        useCal="0";
                        break;
                }
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int j = 0; j<second.checking.length;j++){
                    if(second.checking[j]=="check"){
                        cnt++;
                    }
                }
                if(!second.breakfast.equals("")&&!second.lunch.equals("")&&!second.dinner.equals("")){
                    if(alaramTime.equals("1"))
                        ;//makeNoUse(getTime(), useCal, alaramTime);
                    else{
                        String inDay = getTime();
                        Log.d("finish", inDay);
                        Alarm alarm = new Alarm(inDay, second.breakfast, second.lunch, second.dinner, useCal, alaramTime);
                        alarmDB.child("AlarmDB").child(alarm.inDay).setValue(alarm);

                    }
                    Toast.makeText(getActivity(),"가입되었습니다.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();}
                else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setTitle("경고");
                    dlg.setIcon(R.drawable.main);
                    dlg.setMessage("아직 선택하지 않은 항목이 있습니다. 모두 선택해주세요");
                    dlg.setPositiveButton("네", null);
                    dlg.show();
                }
            }
        });
        return view;
    }

    /*public void makeUse(String inDay, String breakfast, String lunch, String dinner, String useCal, String use) {
        result = new HashMap<>();
        result.put("inDay", inDay);
        result.put("breakfast", breakfast);
        result.put("lunch", lunch);
        result.put("dinner", dinner);
        result.put("useCal", useCal);
        result.put("use", use);
    }
    public void makeNoUse(String inDay, String useCal, String use) {
        result = new HashMap<>();
        result.put("inDay", inDay);
        result.put("useCal", useCal);
        result.put("use", use);

        getNumber();//회원 번호 부여
    }


    public void getNumber() {
        Log.d("finish", "getNumber");

        ValueEventListener generalListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                Log.d("finish", String.valueOf(i));
                for (DataSnapshot column : snapshot.getChildren()) {

                    if (Integer.parseInt(column.getKey()) != i) {
                        break;
                    } else {
                        i++;
                    }

                }
                result.put("num", Integer.toString(i));
                setDatabase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //없는 경우
            }
        };
        alarmDB.addListenerForSingleValueEvent(generalListener); //콜백 한 번만 호출이 이뤄지는 경우
    }//회원 번호 부여

    public void setDatabase() {
        Log.d("finish", "cold");

    }//데이터베이스 값 입력*/


    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

}
