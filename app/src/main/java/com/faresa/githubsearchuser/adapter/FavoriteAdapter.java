package com.faresa.githubsearchuser.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.activity.DetailActivity;
import com.faresa.githubsearchuser.activity.FavoriteActivity;
import com.faresa.githubsearchuser.activity.HomeActivity;
import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.following.FollowingResponse;
import com.faresa.githubsearchuser.pojo.search.SearchData;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<UserResponse> data = new ArrayList<>();
    private FavoriteAdapter.OnItemClickCallback onItemClickCallback;


    public void setData(ArrayList<UserResponse> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickCallback(FavoriteAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent,false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        UserResponse item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imageView);

        holder.nama.setText(item.getLogin());
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(data.get(holder.getAdapterPosition())));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);



        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(UserResponse userResponse);
    }
}
