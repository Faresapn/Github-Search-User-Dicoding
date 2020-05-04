package com.faresa.githubsearchuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.faresa.githubsearchuser.BuildConfig;
import com.faresa.githubsearchuser.koneksi.Client;
import com.faresa.githubsearchuser.koneksi.Service;
import com.faresa.githubsearchuser.pojo.follower.FollowerResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FollowerResponse>> Userdata;
    public void loadEvent(String username) {
        try {
            Service service = Client.getClient().create(Service.class);
            String apiKey = BuildConfig.TOKEN;
            Call<ArrayList<FollowerResponse>> eventCall = service.Follower(apiKey,username);
            eventCall.enqueue(new Callback<ArrayList<FollowerResponse>>() {

                private Response<ArrayList<FollowerResponse>>response;

                @Override
                public void onResponse(Call<ArrayList<FollowerResponse>> call, Response<ArrayList<FollowerResponse>> response) {
                    this.response = response;
                    Userdata.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<FollowerResponse>> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<ArrayList<FollowerResponse>> getData() {
        if (Userdata == null) {

            Userdata = new MutableLiveData<>();

        }
        return Userdata;
    }
}
