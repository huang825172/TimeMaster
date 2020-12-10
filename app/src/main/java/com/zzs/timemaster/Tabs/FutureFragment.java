package com.zzs.timemaster.Tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zzs.timemaster.Features.ScheduleActivity;
import com.zzs.timemaster.Models.Arrangement;
import com.zzs.timemaster.R;
import com.zzs.timemaster.databinding.FragmentFutureBinding;

import java.util.ArrayList;

public class FutureFragment extends Fragment {

    FragmentFutureBinding binding;

    ArrayList<Arrangement> schedules = new ArrayList<>();

    public FutureFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentFutureBinding.inflate(LayoutInflater.from(getContext()));
        binding.FutureAddBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ScheduleActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        schedules.add(new Arrangement("10:10 - 11:30", "口译（1）", "北六教A311"));
        schedules.add(new Arrangement("10:10 - 11:30", "口译（1）", "北六教A311"));

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