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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.faresa.githubsearchuser.activity.fragment.FollowerFragment;
import com.faresa.githubsearchuser.activity.fragment.FollowingFragment;
import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.db.DbContract;
import com.faresa.githubsearchuser.db.FavoriteDbHelper;
import com.faresa.githubsearchuser.db.FavoriteHelper;
import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.follower.FollowerResponse;
import com.faresa.githubsearchuser.pojo.search.SearchData;
import com.faresa.githubsearchuser.viewmodel.UserViewModel;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static com.faresa.githubsearchuser.db.DbContract.FavoriteColoumn.TABLE_FAVORITE_NAME;

public class DetailActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    public static String currentuser;

    ImageView imgProfile;
    TextView Name,Username,Location,Email,Website;

    private ViewPager viewpager;
    TabLayout tabLayout;

    private FavoriteHelper favoriteHelper ;
    UserResponse user;
    ArrayList<UserResponse> list = new ArrayList<>();
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
        final MaterialFavoriteButton favBtn = findViewById(R.id.fav_btn);
        favoriteHelper = FavoriteHelper.getInst(getApplicationContext());
        favoriteHelper.open();

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
        currentuser = intent.getStringExtra("ID");
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


                if (Exist(currentuser)){
                    favBtn.setFavorite(true);
                    favBtn.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite){
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteInsert(userResponse);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Ditambahkan Favorite",Toast.LENGTH_SHORT);
                                        toast.show();

                                    }
                                    else {
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteDelete(currentuser);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Dihapus Favorite",Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            });

                }else {
                    favBtn.setOnFavoriteChangeListener(
                            new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                @Override
                                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                    if (favorite){
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteInsert(userResponse);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Ditambahkan Favorite",Toast.LENGTH_SHORT);
                                        toast.show();

                                    }
                                    else {
                                        list = favoriteHelper.getAllFavorites();
                                        favoriteHelper.favoriteDelete(currentuser);
                                        Toast toast = Toast.makeText(getApplicationContext(),"Dihapus Favorite",Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }
                            });
                }

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
    public boolean Exist(String item) {
        String pilih = DbContract.FavoriteColoumn.TITLE+" =?";
        String[] pilihArg = {item};
        String limit = "1";
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();
        FavoriteDbHelper dataBaseHelper = new FavoriteDbHelper(DetailActivity.this);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_FAVORITE_NAME, null, pilih, pilihArg, null, null, null, limit);
        boolean exists;
        exists = (cursor.getCount() > 0);
        cursor.close();

        return exists;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        favoriteHelper.close();
    }
}
