package com.indooreappdev.amezingwallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class WallpaperFullScreen extends AppCompatActivity {
    String originalUrl ="";
    PhotoView photoView;
    Button download_image;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_full_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#66000000")));

        Intent intent = getIntent();
        originalUrl = intent.getStringExtra("originalUrl");
        photoView = findViewById(R.id.photo);
        download_image = findViewById(R.id.download_image);
        CustomProgressDialog dialog = new CustomProgressDialog(WallpaperFullScreen.this);
        dialog.show();

        Glide.with(this).load(originalUrl).into(photoView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },1500);

        mDialog = new Dialog(this);

        download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(originalUrl);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(getApplicationContext()," Start Downloading...",Toast.LENGTH_LONG).show();

            }
        });
    }
    public  void ShowPopup(View view){
        TextView tv,homeScreen,lockScren,shareScreen;
        mDialog.setContentView(R.layout.custompopup);
        tv = mDialog.findViewById(R.id.text);
        homeScreen = mDialog.findViewById(R.id.home_screen);
        lockScren = mDialog.findViewById(R.id.lock_screen);
        shareScreen = mDialog.findViewById(R.id.share_screen);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();

        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).asBitmap().load(originalUrl).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                        try {
                            wallpaperManager.setBitmap(bitmap);
                            Toast.makeText(getApplicationContext(),"Wallpaper set",Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Wallpaper not set",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });

            }
        });

        lockScren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).asBitmap().load(originalUrl).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK);
                            }
                            Toast.makeText(getApplicationContext(),"Wallpaper set",Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Wallpaper not set",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });
            }
        });






        shareScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Glide.with(getApplicationContext()).asBitmap().load(originalUrl).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                        try {
                            wallpaperManager.setBitmap(bitmap);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK);
                            }
                            Toast.makeText(getApplicationContext(),"Wallpaper set",Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"wallpaper not set",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()  == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}