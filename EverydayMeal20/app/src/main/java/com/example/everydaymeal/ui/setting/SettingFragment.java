package com.example.everydaymeal.ui.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.everydaymeal.EverydayMealDB;
import com.example.everydaymeal.MainActivity;
import com.example.everydaymeal.R;
import com.example.everydaymeal.StartActivity;
import com.example.everydaymeal.infoActivity;
import com.example.everydaymeal.setMealTime;

public class SettingFragment extends Fragment {//완성

    TextView settingMealTime, friend, programmer, out, logout, info;
    SQLiteDatabase db;
    EverydayMealDB everydayMealDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        logout = view.findViewById(R.id.logout);
        settingMealTime = view.findViewById(R.id.settingMealtime);
        friend = view.findViewById(R.id.friend);
        programmer = view.findViewById(R.id.programmer);
        out = view.findViewById(R.id.out);
        info = view.findViewById(R.id.info);
        everydayMealDB = new EverydayMealDB(getContext());

        settingMealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), setMealTime.class);
                startActivity(i);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), infoActivity.class);
                startActivity(i);
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"구글 앱스토어 업로드 시 다운로드 링크를 QR코드로 팝업할 예정입니다",Toast.LENGTH_SHORT).show();
            }
        });
        programmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                dlg.setTitle("개발자");
                dlg.setMessage("개발자: 임기범\n 이메일: nainforet100@gmail.com\n 전화번호: 010-5294-3526");
                dlg.setPositiveButton("확인", null);
                dlg.show();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = everydayMealDB.getWritableDatabase();
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                dlg.setTitle("탈퇴하기");
                dlg.setMessage("정말 탈퇴하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.execSQL("Delete from ALARM where id =='"+ MainActivity.id+"';");
                        if(MainActivity.useCal==1)
                            db.execSQL("Delete from MEALCAL where id =='"+ MainActivity.id+"';");
                        else if(MainActivity.useCal==0)
                            db.execSQL("Delete from MEAL where id =='"+ MainActivity.id+"';");
                        db.close();
                        Intent i = new Intent(getActivity(), StartActivity.class);
                        startActivity(i);
                        Toast.makeText(getContext(), "탈퇴되셨습니다.", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), StartActivity.class);
                startActivity(i);
                Toast.makeText(getContext(), "로그아웃!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}