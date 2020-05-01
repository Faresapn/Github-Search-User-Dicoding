package com.faresa.githubsearchuser.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.search.SearchData;
import com.faresa.githubsearchuser.viewmodel.SearchViewModel;
import com.faresa.githubsearchuser.viewmodel.UserViewModel;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public static String currentuser;
    ImageView imgProfile;
    TextView Name,Username,Location,Email,Website;
    UserResponse userResponse;
    UserViewModel userViewModel;
    UserResponse tc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Name = findViewById(R.id.name_detail);
        Username = findViewById(R.id.username);
        Location = findViewById(R.id.location);
        Email = findViewById(R.id.email);
        Website = findViewById(R.id.website);
        imgProfile = findViewById(R.id.img_profile);
        currentuser =intent.getStringExtra("ID");
        Log.d("usercek",currentuser);
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.loadEvent(currentuser);
        userViewModel.getData().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(@Nullable UserResponse userResponse) {
                Name.setText(userResponse.getLogin());
                Username.setText(userResponse.getName());
                Location.setText(userResponse.getLocation());
                Email.setText(userResponse.getEmail());
                Website.setText(userResponse.getBlog());
                Glide.with(getApplicationContext())
                        .load(userResponse.getAvatarUrl())
                        .into(imgProfile);
            }
        });
    }
}
