package com.zzs.timemaster.Features;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zzs.timemaster.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.RegisterLoginText.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.RegisterBtn.setOnClickListener(v -> {
            if (!(binding.RegisterNameInput.getText().length() > 0) || !(binding.RegisterEmailInput.getText().length() > 0) || !(binding.RegisterPasswordInput.getText().length() > 0)) {
                Toast.makeText(this, "信息未完整", Toast.LENGTH_SHORT).show();
            } else finish();
        });
    }
}