package com.faresa.githubsearchuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.koneksi.Client;
import com.faresa.githubsearchuser.koneksi.Service;
import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.search.SearchData;
import com.faresa.githubsearchuser.pojo.search.SearchResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserResponse> Userdata;
    public void loadEvent(String username) {
        try {
            Service service = Client.getClient().create(Service.class);
            Call<UserResponse> eventCall = service.detailUser("token 1bb5e29e5bbf784ff05c6853434bcbc43a9a6c58",username);
            eventCall.enqueue(new Callback<UserResponse>() {

                private Response<UserResponse> response;

                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    this.response = response;
                    Userdata.setValue(response.body());
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<UserResponse> getData() {
        if (Userdata == null) {

            Userdata = new MutableLiveData<>();

        }
        return Userdata;
    }
}
