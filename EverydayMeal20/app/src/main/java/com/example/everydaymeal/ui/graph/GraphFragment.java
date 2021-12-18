package com.example.everydaymeal.ui.graph;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.everydaymeal.EverydayMealDB;
import com.example.everydaymeal.MainActivity;
import com.example.everydaymeal.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Boolean.TRUE;

public class GraphFragment extends Fragment {
    Button showBtn;
    String sDate, eDate;
    int sMonth, sDay, eMonth, eDay, l=0;
    LineChart chart;
    TextView startDay, endDay;
    String days[], sort;
    Integer dayScore[];
    SQLiteDatabase db;
    EverydayMealDB everydayMealDB;
    ArrayList<Entry> all, meal, snack, night;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        everydayMealDB = new EverydayMealDB(getContext());
        db = everydayMealDB.getReadableDatabase();
        chart = view.findViewById(R.id.chart);
        startDay = view.findViewById(R.id.startDay);
        endDay = view.findViewById(R.id.endDay);
        showBtn = view.findViewById(R.id.showBtn);
        all = new ArrayList<>();
        meal = new ArrayList<>();
        snack = new ArrayList<>();
        night = new ArrayList<>();

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        sMonth = eMonth = Integer.parseInt(month);
        sDay = eDay = Integer.parseInt(day);
        sDate = "2021.05.23";
        eDate = "2021.05.24";
        sort = "식수총합";
        chart.setNoDataText("날짜를 선택해주세요");

        startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dlg = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sDate = year + "." + (month + 1) + "." + dayOfMonth;
                        sMonth = month+1;
                        sDay = dayOfMonth;
                        startDay.setText(sDate);
                    }
                }, 2021, 4, 24);
                dlg.show();
            }
        });
        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dlg = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        eDate = year + "." + (month + 1) + "." + dayOfMonth;
                        eMonth = month+1;
                        eDay = dayOfMonth;
                        endDay.setText(eDate);
                    }
                }, 2021, 4, 24);
                dlg.show();
            }
        });
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart.clear();
                if (!eDate.equals("") && !sDate.equals("")) {
                    if (eMonth <= Integer.parseInt(month)) {
                        if (eMonth == sMonth) {
                            if (eDay >= sDay) {
                                l = eDay - sDay + 1;
                                days = new String[l];
                                for (int i = 0; i < l; i++) {
                                    if (sMonth < 10)
                                        days[i] = "0" + String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                    else
                                        days[i] = String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                }
                            } else {
                                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                                dlg.setTitle("경고");
                                dlg.setMessage("마지막 날짜가 시작 날짜보다 커야합니다. 다시 선택하세요");
                                dlg.setIcon(R.drawable.main);
                                dlg.setPositiveButton("네", null);
                                dlg.show();
                                startDay.setText("시작 날짜");
                                endDay.setText("마지막 날짜");
                            }

                        } else if (eMonth < sMonth) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                            dlg.setTitle("경고");
                            dlg.setMessage("마지막 날짜가 시작 날짜보다 커야합니다. 다시 선택하세요");
                            dlg.setIcon(R.drawable.main);
                            dlg.setPositiveButton("네", null);
                            dlg.show();
                            startDay.setText("시작 날짜");
                            endDay.setText("마지막 날짜");
                        } else if (eMonth > sMonth) {
                            if (eMonth == 1 | eMonth == 2 || eMonth == 4 || eMonth == 6 || eMonth == 8 || eMonth == 9 || eMonth == 11) {
                                l = 31 - sDay + 1 + eDay;
                                days = new String[l];
                                for (int i = 0; i < l; i++) {
                                    if (sDay + i <= 31) {
                                        if (sMonth < 10)
                                            days[i] = "0" + String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                        else
                                            days[i] = String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                    } else {
                                        for (int j = 1; j <= eDay; j++) {
                                            if (eMonth < 10)
                                                days[i] = "0" + String.valueOf(eMonth) + "." + String.valueOf(j);
                                            else
                                                days[i] = String.valueOf(eMonth) + "." + String.valueOf(i);
                                        }
                                    }
                                }
                            } else if (eMonth == 5 || eMonth == 7 || eMonth == 10 || eMonth == 12) {
                                l = 30 - sDay + 1 + eDay;
                                for (int i = 0; i < l; i++) {
                                    if (sDay + i <= 30) {
                                        if (sMonth < 10)
                                            days[i] = "0" + String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                        else
                                            days[i] = String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                    } else {
                                        for (int j = 1; j <= eDay; j++) {
                                            if (eMonth < 10)
                                                days[i] = "0" + String.valueOf(eMonth) + "." + String.valueOf(j);
                                            else
                                                days[i] = String.valueOf(eMonth) + "." + String.valueOf(i);
                                        }
                                    }
                                }
                            } else {
                                l = 28 - sDay + 1 + eDay;
                                for (int i = 0; i < l; i++) {
                                    if (sDay + i <= 28) {
                                        if (sMonth < 10)
                                            days[i] = "0" + String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                        else
                                            days[i] = String.valueOf(sMonth) + "." + String.valueOf(sDay + i);
                                    } else {
                                        for (int j = 1; j <= eDay; j++) {
                                            if (eMonth < 10)
                                                days[i] = "0" + String.valueOf(eMonth) + "." + String.valueOf(j);
                                            else
                                                days[i] = String.valueOf(eMonth) + "." + String.valueOf(i);
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                        dlg.setTitle("경고");
                        dlg.setIcon(R.drawable.main);
                        dlg.setMessage("마지막 날짜는 오늘까지만 가능합니다. 다시 선택하세요");
                        dlg.setPositiveButton("네", null);
                        dlg.show();
                        startDay.setText("시작 날짜");
                        endDay.setText("마지막 날짜");
                    }
                }
                setChart(l);
            }
        });


            return view;
    }


    public void setChart(int len){
        String sort="";
        for(int j=0;j <4; j++) {
            if(j==1)
                sort = "식사";
            else if(j==2)
                sort = "간식";
            else if(j==3)
                sort = "야식";

            for (int i = 0; i < len; i++) {
                dayScore = new Integer[len];
                dayScore[i] = 0;
                everydayMealDB = new EverydayMealDB(getContext());
                db = everydayMealDB.getReadableDatabase();
                String[] date = days[i].split(".");
                final Cursor[] c = {null};
                if(j==0){
                    if(checkTable(db, "MEAL")==TRUE){
                        c[0] = db.rawQuery("SELECT * FROM MEAL WHERE id =='" + MainActivity.id + "'", null);
                    }
                    else if(checkTable(db, "MEALCAL")==TRUE)
                        c[0] = db.rawQuery("SELECT * FROM MEALCAL WHERE id =='" + MainActivity.id + "'", null);
                }
                else{
                    if(checkTable(db, "MEAL")==TRUE)
                        c[0] = db.rawQuery("SELECT * FROM MEAL WHERE id =='" + MainActivity.id + "' AND sort =='" + sort + "'", null);
                    else if(checkTable(db, "MEALCAL")==TRUE)
                        c[0] = db.rawQuery("SELECT * FROM MEALCAL WHERE id =='" + MainActivity.id + "' AND sort =='" + sort + "'", null);
                }
                c[0].moveToFirst();
                while (!c[0].isAfterLast()) {
                    if (c[0].getString(1).substring(5).equals(days[i]))
                        dayScore[i] += c[0].getInt(5);

                    c[0].moveToNext();
                }
                c[0].close();
                db.close();
                if(j==0)
                    all.add(new Entry(Integer.parseInt(days[i].substring(3)), dayScore[i]));
                else if(j==1)
                    meal.add(new Entry(Integer.parseInt(days[i].substring(3)), dayScore[i]));
                else if (j==2)
                    snack.add(new Entry(Integer.parseInt(days[i].substring(3)), dayScore[i]));
                else if(j==3)
                    night.add(new Entry(Integer.parseInt(days[i].substring(3)), dayScore[i]));
            }
        }

        LineDataSet set1 = new LineDataSet(all, "전체");
        set1.setColor(ContextCompat.getColor(getContext(), R.color.purple));
        set1.setCircleColor(ContextCompat.getColor(getContext(), R.color.purple));
        set1.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.purple));

        LineDataSet set2 = new LineDataSet(meal, "식사");
        set2.setColor(ContextCompat.getColor(getContext(), R.color.purple1));
        set2.setCircleColor(ContextCompat.getColor(getContext(), R.color.purple1));
        set2.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.purple1));

        LineDataSet set3 = new LineDataSet(snack, "간식");
        set3.setColor(ContextCompat.getColor(getContext(), R.color.purple2));
        set3.setCircleColor(ContextCompat.getColor(getContext(), R.color.purple2));
        set3.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.purple2));

        LineDataSet set4 = new LineDataSet(night, "야식");
        set4.setColor(ContextCompat.getColor(getContext(), R.color.purple3));
        set4.setCircleColor(ContextCompat.getColor(getContext(), R.color.purple3));
        set4.setCircleHoleColor(ContextCompat.getColor(getContext(), R.color.purple3));

        LineData lineData = new LineData();
        lineData.addDataSet(set1);
        lineData.addDataSet(set2);
        lineData.addDataSet(set3);
        lineData.addDataSet(set4);

        lineData.setValueTextColor(ContextCompat.getColor(getContext(), R.color.black));
        lineData.setValueTextSize(10);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(len, true);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        xAxis.setGridColor(ContextCompat.getColor(getContext(), R.color.black));

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        yAxisLeft.setGridColor(ContextCompat.getColor(getContext(), R.color.black));

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);
        //y축의 활성화를 제거함

        chart.setVisibleXRangeMinimum(60 * 60 * 24 * 1000 * 5);
        chart.setDescription(null);

        Legend legend = chart.getLegend();
        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        chart.animateY(2000);
        chart.setData(lineData);

    }
    private boolean checkTable(SQLiteDatabase db, String tableName){

        try{
            db.rawQuery("SELECT * FROM "+tableName+" limit 1;" , null);
        }catch(SQLiteException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}