package com.faresa.githubsearchuser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.faresa.githubsearchuser.R;

public class DetailActivity extends AppCompatActivity {
    public static String currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
