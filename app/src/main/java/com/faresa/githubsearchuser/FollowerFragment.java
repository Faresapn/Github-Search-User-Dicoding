package com.faresa.githubsearchuser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.faresa.githubsearchuser.activity.DetailActivity;
import com.faresa.githubsearchuser.activity.HomeActivity;
import com.faresa.githubsearchuser.adapter.AdapterFollower;
import com.faresa.githubsearchuser.adapter.AdapterItem;
import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.follower.FollowerResponse;
import com.faresa.githubsearchuser.pojo.search.SearchData;
import com.faresa.githubsearchuser.viewmodel.FollowerViewModel;
import com.faresa.githubsearchuser.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;


public class FollowerFragment extends Fragment {
    private RecyclerView recyclerView;
    private FollowerViewModel followerViewModel;
    private AdapterFollower adapterItem;
    FollowerFragment followerFragment;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    ArrayList<FollowerResponse> followerResponsess = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_follower, container, false);
        recyclerView = v.findViewById(R.id.rv_follower);
        followerViewModel = ViewModelProviders.of(this).get(FollowerViewModel.class);
        adapterItem = new AdapterFollower();
        adapterItem.setOnItemClickCallback(new AdapterFollower.OnItemClickCallback() {
            @Override
            public void onItemClicked(FollowerResponse followerResponse) {
                showSelectedItem(followerResponse);
            }
        });
        initRV();
        getData();

        return v;
    }



    private void getData() {
        followerViewModel.loadEvent(DetailActivity.currentuser);
        followerViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FollowerResponse>>() {
            @Override
            public void onChanged(ArrayList<FollowerResponse> followerResponses) {
                    adapterItem.setData(followerResponsess);
                    followerResponsess.addAll(followerResponses);
                    recyclerView.setAdapter(adapterItem);
                    adapterItem.notifyDataSetChanged();
            }
        });
    }
    private void initRV() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public void onResume() {
        super.onResume();
        followerViewModel.loadEvent(DetailActivity.currentuser);

    }
    private void showSelectedItem(FollowerResponse item) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", item.getLogin());
        startActivity(intent);
    }
}
