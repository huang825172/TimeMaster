package com.zzs.timemaster.Features;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zzs.timemaster.MyOpenHelper;
import com.zzs.timemaster.R;
import com.zzs.timemaster.databinding.ActivityScheduleBinding;


public class ScheduleActivity extends AppCompatActivity {
    ActivityScheduleBinding binding;

    //将代办事项添加到数据库
    public long add_event(String name, String location, int time, boolean importance, boolean urgency) {
        MyOpenHelper myHelper = new MyOpenHelper(this, MyOpenHelper.DB_NAME, null, 1);
        SQLiteDatabase db = myHelper.getWritableDatabase();
        //清空表内数据
//        db.execSQL("delete from event");
        ContentValues values = new ContentValues();
        values.put(MyOpenHelper.EVENT_NAME, name);
        values.put(MyOpenHelper.EVENT_LOCATION, location);
        values.put(MyOpenHelper.TIME, time);
        values.put(MyOpenHelper.IMPORTANT, importance);
        values.put(MyOpenHelper.URGENT, urgency);
        long check = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.EVENT_NAME, values);
        db.close();
        return check;
    }

    //判断输入的日期是否有效
    public boolean isNum(String str) {
        boolean strIsNum = false;
        if (str == "") {
            strIsNum = false;
        } else {
            if (str.matches("\\d+") && str.length() == 8) {
                strIsNum = true;
            }
        }
        return strIsNum;
    }

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
            if (!(binding.ScheduleTitleInput.getText().length() > 0) || !(binding.ScheduleSubtitleInput.getText().length() > 0) ||
                    !(isNum(binding.ScheduleTimeInput.getText().toString()))) {
                Toast.makeText(this, "信息未完整", Toast.LENGTH_SHORT).show();

            } else {
                String name = binding.ScheduleTitleInput.getText().toString();
                String location = binding.ScheduleSubtitleInput.getText().toString();
                int time = Integer.valueOf(binding.ScheduleTimeInput.getText().toString());
                boolean importance;
                boolean urgency;
                String im_ur = (String) binding.ScheduleImportanceSpinner.getSelectedItem();
//                Toast.makeText(this, im_ur, Toast.LENGTH_SHORT).show();
                if (im_ur == "紧急但不重要") {
                    importance = false;
                    urgency = true;
                } else if (im_ur == "重要但不紧急") {
                    importance = true;
                    urgency = false;
                } else if (im_ur == "不重要不紧急") {
                    importance = true;
                    urgency = true;
                } else {
                    importance = true;
                    urgency = true;
                }
                long number = add_event(name, location, time, importance, urgency);
                Toast.makeText(this, "插入事项成功，若存在显示错误请重启app", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.putExtra("result", binding.ScheduleTimeInput.getText().toString());
                setResult(0, i);
                finish();
            }
        });
    }
}
