package com.faresa.githubsearchuser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.adapter.AdapterSearch;
import com.faresa.githubsearchuser.pojo.search.SearchData;
import com.faresa.githubsearchuser.viewmodel.SearchViewModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    SearchView svSearch;
    private SearchViewModel searchViewModel;
    private AdapterSearch adapterSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        svSearch = findViewById(R.id.search);
        recyclerView = findViewById(R.id.rv_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterSearch = new AdapterSearch(this);
        adapterSearch.notifyDataSetChanged();

        recyclerView.setAdapter(adapterSearch);
        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchViewModel.loadEvent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchViewModel.loadEvent(newText);
                return false;
            }
        });

        searchViewModel.getData().observe(this, new Observer<ArrayList<SearchData>>() {
            @Override
            public void onChanged(ArrayList<SearchData> searchUserItems) {
                if (searchUserItems.size() != 0) {
                    adapterSearch.setData(searchUserItems);
                } else {
                    Toast.makeText(HomeActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.Fav){
            startActivity(new Intent(this,FavoriteActivity.class));
        }else if (item.getItemId() == R.id.set){
            startActivity(new Intent(this, FavoriteActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



}
