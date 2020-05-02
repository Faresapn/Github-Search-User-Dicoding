package com.faresa.githubsearchuser.adapter;

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
import com.faresa.githubsearchuser.pojo.following.FollowingResponse;

import java.util.ArrayList;

public class AdapterFollowing extends RecyclerView.Adapter<AdapterFollowing.ViewHolder> {
    private ArrayList<FollowingResponse> data = new ArrayList<>();
    private AdapterFollowing.OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<FollowingResponse> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(AdapterFollowing.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public AdapterFollowing.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent,false);
        return new AdapterFollowing.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFollowing.ViewHolder holder, int position) {
        FollowingResponse item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(new RequestOptions().override(100, 100))
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

        public ViewHolder(@NonNull View v) {
            super(v);
            nama = v.findViewById(R.id.textView);
            imageView = v.findViewById(R.id.imageView);

        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(FollowingResponse followingResponse);
    }
}
