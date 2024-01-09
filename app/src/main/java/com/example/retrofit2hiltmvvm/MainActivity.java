package com.example.retrofit2hiltmvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit2hiltmvvm.btn01.ui.activity.Test01Activity;
import com.example.retrofit2hiltmvvm.btn02.ui.activity.Test02Activity;
import com.example.retrofit2hiltmvvm.btn03.ui.activity.Test03Activity;
import com.example.retrofit2hiltmvvm.btn04.ui.activity.Test04Activity;
import com.example.retrofit2hiltmvvm.databinding.AtyMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AtyMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AtyMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn01.setOnClickListener(this);
        binding.btn02.setOnClickListener(this);
        binding.btn03.setOnClickListener(this);
        binding.btn04.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btn01) {
            Intent intent = new Intent(this, Test01Activity.class);
            startActivity(intent);
        } else if (v == binding.btn02) {
            Intent intent = new Intent(this, Test02Activity.class);
            startActivity(intent);
        } else if (v == binding.btn03) {
            Intent intent = new Intent(this, Test03Activity.class);
            startActivity(intent);
        } else if (v == binding.btn04) {
            Intent intent = new Intent(this, Test04Activity.class);
            startActivity(intent);
        }
    }
}