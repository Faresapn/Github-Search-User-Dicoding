package com.faresa.favoritegithub.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.faresa.favoritegithub.R;
import com.faresa.favoritegithub.adapter.FavoriteAdapter;

import java.util.Objects;

import static com.faresa.favoritegithub.db.DbContract.FavoriteColoumn.FAVORITE_URI;

public class HomeActivity extends AppCompatActivity {
    FavoriteAdapter adapter;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv = findViewById(R.id.rv_favorite);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));



        new FAV().execute();
    }
    private class FAV extends AsyncTask<Void,Void, Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return HomeActivity.this.getContentResolver().query(FAVORITE_URI,null,null,null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
//            Log.d("cursor", String.valueOf(cursor));
            adapter = new FavoriteAdapter(getApplicationContext());
            adapter.setCursor(cursor);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);

        }
    }
}
