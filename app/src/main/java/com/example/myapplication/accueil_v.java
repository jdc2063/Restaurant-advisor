package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class accueil_v extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private static RestaurantApi restaurantApi;
    private Retrofit retrofit;

    private Button addRestaurantBtn;
    private EditText editText;
    private ListView listView;
    private MyListViewAdapter myListViewAdapter;

    private List<Restaurant> restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_v);
        restaurants = new ArrayList<>();

        this.listView = (ListView) findViewById(R.id.listView);

        this.myListViewAdapter = new  MyListViewAdapter(getApplicationContext(), restaurants);
        this.listView.setAdapter(myListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Restaurant restaurant1 = restaurants.get(position);

                startActivity(String.valueOf(restaurant1.getId()));
            }
        });

        this.configureRetrofit();

        getRestaurantsViaAPI();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void configureRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);
    }

    private void startActivity(String restaurantId) {
        Intent intent = new Intent(this, restaurant_visitor.class);

        intent.putExtra("restName", restaurantId);
        startActivity(intent);
    }

    private void getRestaurantsViaAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);

        restaurantApi.getRestaurants().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d(TAG, "onResponse");

                List<Restaurant> restaurantList = response.body();
                if (restaurantList != null) {
                    for(Restaurant restaurant: restaurantList) {
                        Log.d(TAG, "restaurant reçu: " + restaurant.getId() + " " + restaurant.getName());
                        restaurants.add(restaurant);
                    }
                    myListViewAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "onResponse: restaurants is empty: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}