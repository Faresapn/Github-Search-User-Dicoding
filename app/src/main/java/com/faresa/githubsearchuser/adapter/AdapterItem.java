package com.faresa.githubsearchuser.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.faresa.githubsearchuser.R;
import com.faresa.githubsearchuser.activity.DetailActivity;
import com.faresa.githubsearchuser.activity.HomeActivity;
import com.faresa.githubsearchuser.pojo.search.SearchData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolder> {
    private ArrayList<SearchData> data = new ArrayList<>();
    private HomeActivity homeActivity;


    public void setData(ArrayList<SearchData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public AdapterItem(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;

    }



    @NonNull
    @Override
    public AdapterItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterItem.ViewHolder holder, final int position) {
        SearchData item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imageView);

        holder.nama.setText(item.getLogin());
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        SearchData searchData = data.get(position);
                        Intent intent = new Intent(homeActivity, DetailActivity.class);
                        intent.putExtra("ID", searchData.getId());
                        Objects.requireNonNull(homeActivity).startActivity(intent);
                    }
                }
            });


        }


    }
}
