package com.example.everydaymeal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>{

    private ArrayList<Date_Menu> data = new ArrayList<>();
    EverydayMealDB everydayMealDB;
    SQLiteDatabase db;

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView menu_text;
        TextView menu_time;
        TextView menu_what;

        ItemViewHolder(View itemView) {
            super(itemView);

            menu_time = itemView.findViewById(R.id.menu_time);
            menu_text = itemView.findViewById(R.id.menu_text);
            menu_what = itemView.findViewById(R.id.menu_what);
            everydayMealDB = new EverydayMealDB(itemView.getContext());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();

                    String date = data.get(pos).getDate();
                    String time = data.get(pos).getDate_time();
                    AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                    dlg.setTitle("삭제");
                    dlg.setMessage("삭제하시겠습니까?");
                    dlg.setIcon(R.drawable.main);
                    dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db = everydayMealDB.getWritableDatabase();
                            if(MainActivity.useCal==0)
                                db.execSQL("DELETE FROM MEAL WHERE id == '"+MainActivity.id+"' AND day =='"+date+"' AND time =='"+time+"';");
                            else if(MainActivity.useCal==1)
                                db.execSQL("DELETE FROM MEALCAL WHERE id == '"+MainActivity.id+"' AND day =='"+date+"' AND time =='"+time+"';");
                            data.remove(pos);
                            notifyItemRemoved(pos);
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "삭제 완료!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.setNegativeButton("아니요", null);
                    dlg.show();

                    return false;
                }
            });
        }

        void onBind(Date_Menu dt, int position) {
            menu_time.setText(dt.getDate_time());
            menu_what.setText(dt.getWhat());
            menu_text.setText(dt.getDate_menu());
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(Date_Menu dt) {
        data.add(dt);
    }

    public void clearItem() {
        data.clear();
    }
}
