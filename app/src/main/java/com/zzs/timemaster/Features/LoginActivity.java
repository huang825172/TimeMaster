package com.zzs.timemaster.Features;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zzs.timemaster.MainActivity;
import com.zzs.timemaster.databinding.ActivityLoginBinding;
import com.zzs.timemaster.databinding.FragmentMineBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DataBaseHelper(this);

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.LoginRegisterText.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        binding.LoginBtn.setOnClickListener(v -> {
            boolean var =myDb.checkUser(binding.LoginNameInput.getText().toString(),binding.LoginPasswordInput.getText().toString());
            if (var){
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this , MainActivity.class));
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
            }
        });

//            if (!(binding.LoginEmailInput.getText().length() > 0) || !(binding.LoginPasswordInput.getText().length() > 0)) {
//                Toast.makeText(this, "信息未完整", Toast.LENGTH_SHORT).show();
//            } else {
//                if (binding.LoginEmailInput.getText().toString().equals("admin") && binding.LoginPasswordInput.getText().toString().equals("123")) {
//                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
//            }


    }





}