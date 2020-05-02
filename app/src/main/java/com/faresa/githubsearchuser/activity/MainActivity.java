package com.faresa.githubsearchuser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.faresa.githubsearchuser.R;

public class MainActivity extends AppCompatActivity {
    private static final int TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }, TIME);
    }
}
