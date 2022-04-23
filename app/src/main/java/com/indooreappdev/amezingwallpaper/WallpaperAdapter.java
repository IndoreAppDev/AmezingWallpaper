package com.indooreappdev.amezingwallpaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolde> {
    private Context context;
    private List<WallpaperModel> wallpaperModelList;

    public WallpaperAdapter(Context context, List<WallpaperModel> wallpaperModelList) {
        this.context = context;
        this.wallpaperModelList = wallpaperModelList;
    }
    @NonNull
    @Override
    public WallpaperViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item,parent,false);

        return new WallpaperViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolde holder, int position) {
        Glide.with(context).load(wallpaperModelList.get(position).getMediumUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,WallpaperFullScreen.class)
                .putExtra("originalUrl",wallpaperModelList.get(position).getOriginalUrl()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return wallpaperModelList.size();
    }

    public  class  WallpaperViewHolde extends RecyclerView.ViewHolder{
        ImageView imageView;

        public WallpaperViewHolde(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
