package com.example.everydaymeal.ui.calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.everydaymeal.CalendarAdapter;
import com.example.everydaymeal.Date_Menu;
import com.example.everydaymeal.EventDecorator;
import com.example.everydaymeal.EverydayMealDB;
import com.example.everydaymeal.MainActivity;
import com.example.everydaymeal.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

public class CalendarFragment extends Fragment {//완료

    EverydayMealDB everydayMealDB;
    SQLiteDatabase db;
    MaterialCalendarView cal;
    RecyclerView menuRecycler;
    private CalendarAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        everydayMealDB = new EverydayMealDB(getContext());
        db = everydayMealDB.getWritableDatabase();
        cal = view.findViewById(R.id.cal);
        menuRecycler = view.findViewById(R.id.menuRecycler);
        menuRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        menuRecycler.setLayoutManager(linearLayoutManager);
        adapter = new CalendarAdapter();
        menuRecycler.setAdapter(adapter);


        cal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull @NotNull MaterialCalendarView widget, @NonNull @NotNull CalendarDay date, boolean selected) {

                final Calendar selectCal = date.getCalendar();
                adapter.clearItem();
                adapter.notifyDataSetChanged();

                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy.MM.dd");
                String findDay = yearFormat.format(date.getDate());
                Cursor c = null;
                try {
                    selectCal.setTime(yearFormat.parse(yearFormat.format(selectCal.getTime())));

                    if(MainActivity.useCal==1){
                        c = db.rawQuery("SELECT * FROM MEALCAL WHERE id== '" + MainActivity.id + "' AND day=='"+findDay+"';", null);}
                    else if(MainActivity.useCal==0){
                        c = db.rawQuery("SELECT * FROM MEAL WHERE id== '" + MainActivity.id + "' AND day=='"+findDay+"';", null);
                    }

                    if(c.getCount()==0){
                        Date_Menu item = new Date_Menu();
                        item.setDate_menu("아직 등록된 식탁이 없습니다!");
                        item.setWhat(" ");
                        item.setDate_time(" ");
                        item.setDate(" ");
                        adapter.addItem(item);
                        adapter.notifyDataSetChanged();

                    }else{
                        c.moveToFirst();
                        while(!c.isAfterLast()){
                            Date_Menu item = new Date_Menu();
                            item.setDate_menu(c.getString(4));
                            item.setDate_time(c.getString(2));
                            item.setWhat(c.getString(3));
                            item.setDate(c.getString(1));
                            c.moveToNext();
                            adapter.addItem(item);
                        }
                    }
                    c.close();
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        });

        cal.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.today())));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}