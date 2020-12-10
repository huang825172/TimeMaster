package com.zzs.timemaster.Tabs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zzs.timemaster.Models.Arrangement;
import com.zzs.timemaster.R;
import com.zzs.timemaster.databinding.FragmentTodayBinding;

import java.util.ArrayList;

public class TodayFragment extends Fragment {

    FragmentTodayBinding binding;

    ArrayList<Arrangement> schedules = new ArrayList<>();

    public TodayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentTodayBinding.inflate(LayoutInflater.from(getContext()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        schedules.add(new Arrangement("10:10 - 11:30", "口译（1）", "北六教A311"));
        schedules.add(new Arrangement("10:10 - 11:30", "口译（1）", "北六教A311"));

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
}