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
import com.faresa.githubsearchuser.pojo.follower.FollowerResponse;

import java.util.ArrayList;

public class AdapterFollower extends RecyclerView.Adapter<AdapterFollower.ViewHolder> {
    private ArrayList<FollowerResponse> data = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;
    public void setData(ArrayList<FollowerResponse> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public AdapterFollower.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent,false);
        return new AdapterFollower.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFollower.ViewHolder holder, int position) {
        FollowerResponse item = data.get(position);

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
        void onItemClicked(FollowerResponse followerResponse);
    }
}
