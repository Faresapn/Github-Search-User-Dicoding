package com.faresa.githubsearchuser.activity.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.activity.DetailActivity;
import com.faresa.githubsearchuser.adapter.AdapterFollower;
import com.faresa.githubsearchuser.pojo.follower.FollowerResponse;
import com.faresa.githubsearchuser.viewmodel.FollowerViewModel;

import java.util.ArrayList;


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
