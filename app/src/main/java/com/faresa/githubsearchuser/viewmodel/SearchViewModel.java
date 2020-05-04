package com.faresa.githubsearchuser.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.faresa.githubsearchuser.BuildConfig;
import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.koneksi.Client;
import com.faresa.githubsearchuser.koneksi.Service;
import com.faresa.githubsearchuser.pojo.search.SearchData;
import com.faresa.githubsearchuser.pojo.search.SearchResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<SearchData>> Data = new MutableLiveData<>();

    public void loadEvent(String query) {
        try {
            String apiKey = BuildConfig.TOKEN;
            Service service = Client.getClient().create(Service.class);
            Call<SearchResponse> eventCall = service.searchUser(apiKey,query);
            eventCall.enqueue(new Callback<SearchResponse>() {

                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                    if (!response.isSuccessful()) {
                        Log.d("On Response", response.message());
                    }
                    else if (response.body() != null) {
                        ArrayList<SearchData> items = new ArrayList<>(response.body().getItems());
                        Data.postValue(items);
                    }

                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<ArrayList<SearchData>> getData() {
        return Data;
    }
}
