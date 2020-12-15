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
import com.zzs.timemaster.Models.Arrangement;
import com.zzs.timemaster.Models.Event;
import com.zzs.timemaster.R;
import com.zzs.timemaster.databinding.FragmentTodayBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodayFragment extends Fragment {

    FragmentTodayBinding binding;

    ArrayList<Arrangement> schedules = new ArrayList<>();
    private Context nContext;
    TextView time2;

    public TodayFragment() {
    }
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

    public void loadArrangement(){
        ArrayList<Event>events=getData();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String date=simpleDateFormat.format(new Date());
        String []Words={"一","二","三","四","五","六","七","八","九","十"};
        int count=0;
        for(Event event: events){
            if(event.getEvent_time().equals(date)){
                schedules.add(new Arrangement("今日事项"+Words[count]+": ", event.getEvent_name(), event.getEvent_location()));
                count++;}

        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentTodayBinding.inflate(LayoutInflater.from(getContext()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取数据
        loadArrangement();

        binding.TodayList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.TodayList.setAdapter(new TodayFragment.TodayListAdapter(getContext(), schedules));

        return binding.getRoot();
    }

    private static class TodayListAdapter extends RecyclerView.Adapter<TodayFragment.TodayListAdapter.TodayListVIewHolder> {

        private final Context mContext;
        private final ArrayList<Arrangement> schedules;

        public TodayListAdapter(Context ctx, ArrayList<Arrangement> data) {
            this.mContext = ctx;
            this.schedules = data;
        }

        @NonNull
        @Override
        public TodayFragment.TodayListAdapter.TodayListVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_today_item_layout, parent, false);
            return new TodayFragment.TodayListAdapter.TodayListVIewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TodayFragment.TodayListAdapter.TodayListVIewHolder holder, int position) {
            holder.time.setText(schedules.get(position).getTime());
            holder.title.setText(schedules.get(position).getTitle());
            holder.subtitle.setText(schedules.get(position).getSubtitle());
        }

        @Override
        public int getItemCount() {
            return schedules.size();
        }

        public static class TodayListVIewHolder extends RecyclerView.ViewHolder {
            public final TextView time;
            public final TextView title;
            public final TextView subtitle;

            public TodayListVIewHolder(@NonNull View itemView) {
                super(itemView);
                time = itemView.findViewById(R.id.TodayItemTime);
                title = itemView.findViewById(R.id.TodayItemTitle);
                subtitle = itemView.findViewById(R.id.TodayItemSubtitle);
            }
        }
    }

    @Override
    public void onResume() {
        this.nContext=getActivity();
        super.onResume();
        FutureFragment futureFragment=new FutureFragment();
        boolean eventChange=futureFragment.isChanged;
        futureFragment.isChanged=false;
        if(eventChange){
            schedules.clear();
            loadArrangement();
            time2=getView().findViewById(R.id.TodayItemTitle);
            time2.setText(time2.getText().toString());
//        Toast.makeText(nContext,"已更新",Toast.LENGTH_SHORT).show();
            }
    }
}
