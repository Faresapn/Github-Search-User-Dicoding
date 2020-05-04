package com.faresa.githubsearchuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.faresa.githubsearchuser.BuildConfig;
import com.faresa.githubsearchuser.koneksi.Client;
import com.faresa.githubsearchuser.koneksi.Service;
import com.faresa.githubsearchuser.pojo.follower.FollowerResponse;
import com.faresa.githubsearchuser.pojo.following.FollowingResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewModel extends ViewModel {
    private MutableLiveData<ArrayList<FollowingResponse>> Userdata;
    public void loadEvent(String username) {
        try {
            String apiKey = BuildConfig.TOKEN;
            Service service = Client.getClient().create(Service.class);
            Call<ArrayList<FollowingResponse>> eventCall = service.Following(apiKey,username);
            eventCall.enqueue(new Callback<ArrayList<FollowingResponse>>() {

                private Response<ArrayList<FollowingResponse>> response;

                @Override
                public void onResponse(Call<ArrayList<FollowingResponse>> call, Response<ArrayList<FollowingResponse>> response) {
                    this.response = response;
                    Userdata.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<FollowingResponse>> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<ArrayList<FollowingResponse>> getData() {
        if (Userdata == null) {

            Userdata = new MutableLiveData<>();

        }
        return Userdata;
    }
}
