package com.faresa.githubsearchuser.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faresa.githubsearchuser.FollowerFragment;
import com.faresa.githubsearchuser.FollowingFragment;
import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.search.SearchData;
import com.faresa.githubsearchuser.viewmodel.SearchViewModel;
import com.faresa.githubsearchuser.viewmodel.UserViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public static String currentuser;
    ImageView imgProfile;
    TextView Name,Username,Location,Email,Website;
    UserResponse userResponse;
    UserViewModel userViewModel;
    UserResponse tc;
    private ViewPager viewpager;
    TabLayout tabLayout;
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
        tabLayout = findViewById(R.id.navtab);
        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new viewpageradapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        currentuser =intent.getStringExtra("ID");
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
    public class viewpageradapter extends FragmentStatePagerAdapter {
        int mNumofTabs;
        @SuppressWarnings("deprecation")
        viewpageradapter(FragmentManager fragmentManager, int mNumOfTabs) {
            super(fragmentManager);
            this.mNumofTabs = mNumOfTabs;
        }

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FollowerFragment();
                case 1:
                    return new FollowingFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumofTabs;
        }
    }
}
