package com.indooreappdev.amezingwallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.imaginativeworld.oopsnointernet.NoInternetSnackbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    WallpaperAdapter wallpaperAdapter;
    List<WallpaperModel>wallpaperModelslist ;
    int pageNumber = 1;
    Boolean isScrolling = false;
    int currentItem,totalItem,scrollAllItem;
    Button all,ocean,tigers,pears,nature,animal,bike,flower,phone,cute,love;

    // No Internet Dialog
    NoInternetDialog noInternetDialog;

    // No Internet Snackbar
    NoInternetSnackbar noInternetSnackbar;

    CustomProgressDialog ProgressDialog;
    String url = "https://api.pexels.com/v1/curated/?page=\"+pageNumber+\"&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        ProgressDialog = new CustomProgressDialog(MainActivity.this);

        all = findViewById(R.id.all);
        ocean = findViewById(R.id.ocean);
        tigers = findViewById(R.id.tigers);
        pears = findViewById(R.id.pears);
        nature = findViewById(R.id.nature);
        animal = findViewById(R.id.animal);
        bike = findViewById(R.id.bike);
        flower = findViewById(R.id.flower);
        phone = findViewById(R.id.phone);
        cute = findViewById(R.id.cute);
        love = findViewById(R.id.love);

        wallpaperModelslist = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(MainActivity.this,wallpaperModelslist);
        recyclerView.setAdapter(wallpaperAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling =true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItem = gridLayoutManager.getChildCount();
                totalItem = gridLayoutManager.getItemCount();
                scrollAllItem = gridLayoutManager.findFirstVisibleItemPosition();
                if(isScrolling && (currentItem+scrollAllItem == totalItem)){
                    isScrolling = false;
                    wallpaperFetch();
                }
            }
        });


        wallpaperFetch();

    }

    private void wallpaperFetch() {

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProgressDialog.show();
                        try {
                            JSONObject jsonObject  = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("photos");
                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                int id = object.getInt("id");

                                JSONObject imageurl = object.getJSONObject("src");
                                String originalUrl = imageurl.getString("large2x");
                                String mediumUrl = imageurl.getString("medium");

                                WallpaperModel wallpaperModel = new WallpaperModel(id,originalUrl,mediumUrl);
                                wallpaperModelslist.add(wallpaperModel);

                            }
                            wallpaperAdapter.notifyDataSetChanged();
                            pageNumber++;
                          new Handler().postDelayed(new Runnable() {
                              @Override
                              public void run() {
                                  ProgressDialog.dismiss();
                              }
                          },2500);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","563492ad6f917000010000017beab27850924562998afc3f166a3169 ");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    public  void all( View view){
        url =  "https://api.pexels.com/v1/curated/?page=\"+pageNumber+\"&per_page=80";
         wallpaperModelslist.clear();
         wallpaperFetch();
    }

    public void ocean(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Ocean&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    public void tigers(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Tigers&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    public void pears(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Pears&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();

    }

    public void nature(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Nature&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();

    }

    public void animal(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Animal&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    public void bike(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Bikes&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    public void flower(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Flower&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    public void phone(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Phone&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    public void love(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Love&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    public void cute(View view) {
        url =  "https://api.pexels.com/v1/search?page="+pageNumber+"&query=Cute&per_page=80";
        wallpaperModelslist.clear();
        wallpaperFetch();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // No Internet Dialog
        NoInternetDialog.Builder builder1 = new NoInternetDialog.Builder(this);

        builder1.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });
        builder1.setCancelable(false); // Optional
        builder1.setNoInternetConnectionTitle("No Internet"); // Optional
        builder1.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        builder1.setShowInternetOnButtons(true); // Optional
        builder1.setPleaseTurnOnText("Please turn on"); // Optional
        builder1.setWifiOnButtonText("Wifi"); // Optional
        builder1.setMobileDataOnButtonText("Mobile data"); // Optional

        builder1.setOnAirplaneModeTitle("No Internet"); // Optional
        builder1.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        builder1.setPleaseTurnOffText("Please turn off"); // Optional
        builder1.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        builder1.setShowAirplaneModeOffButtons(true); // Optional

        noInternetDialog = builder1.build();


        // No Internet Snackbar
        NoInternetSnackbar.Builder builder2 = new NoInternetSnackbar.Builder(this, (ViewGroup) findViewById(android.R.id.content));

        builder2.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });
        builder2.setIndefinite(true); // Optional
        builder2.setNoInternetConnectionMessage("No active Internet connection!"); // Optional
        builder2.setOnAirplaneModeMessage("You have turned on the airplane mode!"); // Optional
        builder2.setSnackbarActionText("Settings");
        builder2.setShowActionToDismiss(false);
        builder2.setSnackbarDismissActionText("OK");

        noInternetSnackbar = builder2.build();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // No Internet Dialog
        if (noInternetDialog != null) {
            noInternetDialog.destroy();
        }

        // No Internet Snackbar
        if (noInternetSnackbar != null) {
            noInternetSnackbar.destroy();
        }
    }

}
