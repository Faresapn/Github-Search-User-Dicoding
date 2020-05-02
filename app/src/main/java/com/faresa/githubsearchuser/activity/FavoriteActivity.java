package com.faresa.githubsearchuser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.adapter.AdapterFollowing;
import com.faresa.githubsearchuser.adapter.FavoriteAdapter;
import com.faresa.githubsearchuser.db.FavoriteHelper;
import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.following.FollowingResponse;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    ArrayList<UserResponse> list = new ArrayList<>();
    private FavoriteHelper favoriteHelper;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteHelper = FavoriteHelper.getInst(FavoriteActivity.this);
        favoriteHelper.open();
        RecyclerView rv = findViewById(R.id.rv_favorite);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FavoriteAdapter();
        adapter.setOnItemClickCallback(new FavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(UserResponse userResponse) {
                showSelectedItem(userResponse);
            }
        });
        rv.setAdapter(adapter);
    }

    private void showSelectedItem(UserResponse userResponse) {
        Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
        intent.putExtra("ID", userResponse.getLogin());
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        list = favoriteHelper.getAllFavorites();
        adapter.setData(list);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        movieHelper.close();
    }
}
