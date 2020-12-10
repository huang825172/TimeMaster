package com.zzs.timemaster.Features;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zzs.timemaster.R;
import com.zzs.timemaster.databinding.ActivityScheduleBinding;

public class ScheduleActivity extends AppCompatActivity {

    ActivityScheduleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.importance_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.ScheduleImportanceSpinner.setAdapter(adapter);

        binding.ScheduleBack.setOnClickListener(v -> finish());

        binding.ScheduleImportBtn.setOnClickListener(v -> Toast.makeText(this, "功能未实现", Toast.LENGTH_SHORT).show());

        binding.ScheduleFinishBtn.setOnClickListener(v -> {
            if (!(binding.ScheduleTitleInput.getText().length() > 0) || !(binding.ScheduleSubtitleInput.getText().length() > 0)) {
                Toast.makeText(this, "信息未完整", Toast.LENGTH_SHORT).show();
            } else finish();
        });
    }
}