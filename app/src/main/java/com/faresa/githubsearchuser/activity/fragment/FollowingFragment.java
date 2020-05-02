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
import com.faresa.githubsearchuser.adapter.AdapterFollowing;
import com.faresa.githubsearchuser.pojo.following.FollowingResponse;
import com.faresa.githubsearchuser.viewmodel.FollowingViewModel;

import java.util.ArrayList;


public class FollowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private FollowingViewModel followingViewModel;
    private AdapterFollowing adapterItem;
    FollowingFragment followingFragment;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    ArrayList<FollowingResponse> followingResponsess = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_following, container, false);
        recyclerView = v.findViewById(R.id.rv_following);
        followingViewModel = ViewModelProviders.of(this).get(FollowingViewModel.class);
        adapterItem = new AdapterFollowing();
        adapterItem.setOnItemClickCallback(new AdapterFollowing.OnItemClickCallback() {
            @Override
            public void onItemClicked(FollowingResponse followingResponse) {
                showSelectedItem(followingResponse);
            }
        });

        initRV();
        getData();

        return v;
    }

    private void showSelectedItem(FollowingResponse item) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("ID", item.getLogin());
        startActivity(intent);
    }

    private void getData() {
        followingViewModel.loadEvent(DetailActivity.currentuser);
        followingViewModel.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FollowingResponse>>() {
            @Override
            public void onChanged(ArrayList<FollowingResponse> followingResponses) {

                adapterItem.setData(followingResponsess);
                followingResponsess.addAll(followingResponses);
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
        followingViewModel.loadEvent(DetailActivity.currentuser);

    }
}
