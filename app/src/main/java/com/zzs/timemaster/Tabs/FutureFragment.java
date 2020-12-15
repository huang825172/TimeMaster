package com.zzs.timemaster.Tabs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqllite.MyOpenHelper;
import com.zzs.timemaster.Features.ScheduleActivity;
import com.zzs.timemaster.Models.Arrangement;
import com.zzs.timemaster.R;
import com.zzs.timemaster.databinding.FragmentFutureBinding;
import com.zzs.timemaster.Models.Event;
import com.zzs.timemaster.Tabs.TodayFragment;

import java.util.ArrayList;
import java.util.Collections;

public class FutureFragment extends Fragment {
    public  static  boolean isChanged=false;
    FragmentFutureBinding binding;
    ArrayList<Event> events;
    private Context mContext;
    TextView time;

    ArrayList<Arrangement> schedules = new ArrayList<>();

    public FutureFragment() {
    }
    //将数据库内数据读出，存储到数组列表中
    public ArrayList<Event> getData() {
        ArrayList<Event> events=new ArrayList<>();
        MyOpenHelper myHelper = new MyOpenHelper(getContext(), MyOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor c = db.query(MyOpenHelper.TABLE_NAME, new String[] {
                        MyOpenHelper.EVENT_NAME, MyOpenHelper.EVENT_LOCATION,MyOpenHelper.TIME, }, null,null,
                null, null, null);
        int nameIndex=c.getColumnIndex(MyOpenHelper.EVENT_NAME);
        int locationIndex=c.getColumnIndex(MyOpenHelper.EVENT_LOCATION);
        int timeIndex=c.getColumnIndex(MyOpenHelper.TIME);

        while(c.moveToNext()){
            String name = c.getString(nameIndex);
            String location = c.getString(locationIndex);
            String time=c.getString(timeIndex);
            Event event=new Event(time,name,location);
            events.add(event);
        }
        db.close();
        return events;
    }
    //添加到事件Fragment中
    public void addSchedules(){
        events=getData();
        Collections.sort(events);
        for(Event event:events){
            int event_time=Integer.valueOf(event.getEvent_time());
            int month=event_time%10000/100;
            int day= event_time%100;
            schedules.add(new Arrangement(month+" 月"+day+" 日", event.getEvent_name(), event.getEvent_location()));}
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.mContext=getActivity();
        super.onCreate(savedInstanceState);
        binding = FragmentFutureBinding.inflate(LayoutInflater.from(getContext()));

        //获取数据
        addSchedules();
        binding.FutureAddBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ScheduleActivity.class);
            startActivityForResult(intent,0);
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        time=getView().findViewById(R.id.FutureItemTitle);
        time.setText(time.getText().toString());
        schedules.clear();
        addSchedules();
        isChanged=true;

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding.FutureList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.FutureList.setAdapter(new FutureListAdapter(getContext(), schedules));

        return binding.getRoot();
    }

    private static class FutureListAdapter extends RecyclerView.Adapter<FutureListAdapter.FutureListVIewHolder> {

        private final Context mContext;
        private final ArrayList<Arrangement> schedules;

        public FutureListAdapter(Context ctx, ArrayList<Arrangement> data) {
            this.mContext = ctx;
            this.schedules = data;
        }

        @NonNull
        @Override
        public FutureListVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_future_item_layout, parent, false);
            return new FutureListVIewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FutureListVIewHolder holder, int position) {
            holder.time.setText(schedules.get(position).getTime());
            holder.title.setText(schedules.get(position).getTitle());
            holder.subtitle.setText(schedules.get(position).getSubtitle());
        }

        @Override
        public int getItemCount() {
            return schedules.size();
        }

        public static class FutureListVIewHolder extends RecyclerView.ViewHolder {
            public final TextView time;
            public final TextView title;
            public final TextView subtitle;

            public FutureListVIewHolder(@NonNull View itemView) {
                super(itemView);
                time = itemView.findViewById(R.id.FutureItemTime);
                title = itemView.findViewById(R.id.FutureItemTitle);
                subtitle = itemView.findViewById(R.id.FutureItemSubtitle);
            }
        }
    }
}
