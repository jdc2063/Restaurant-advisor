package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class restaurant_visitor extends AppCompatActivity {
    private final String TAG = "restaurant_visitor";

    private static RestaurantApi restaurantApi;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_visitor);
        Integer restaurantId = Integer.parseInt(getIntent().getStringExtra("restName"));

        this.configureRetrofit();
        getRestaurantId(restaurantId);
    }

    private void getRestaurantId(int id){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);

        TextView textName = (TextView) findViewById(R.id.NameRest);
        TextView textId = (TextView) findViewById(R.id.idrest);
        TextView textDescription = (TextView) findViewById(R.id.descriptionrest);
        TextView textGrade = (TextView) findViewById(R.id.graderest);
        TextView textLocalization = (TextView) findViewById(R.id.localizationrest);
        TextView textPhone = (TextView) findViewById(R.id.phonerest);
        TextView textWeb = (TextView) findViewById(R.id.webrest);
        TextView textHours = (TextView) findViewById(R.id.hoursrest);

        restaurantApi.getRestaurantId(id).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response.body() != null) {
                    Restaurant restaurant = response.body();
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    Log.d(TAG, "info: " + restaurant.getGrade());
                    textName.setText(restaurant.getName());
                    textId.setText(String.valueOf(restaurant.getId()));
                    textDescription.setText(restaurant.getDescription());
                    textGrade.setText(Float.toString(restaurant.getGrade()));
                    textLocalization.setText(restaurant.getLocalization());
                    textPhone.setText(restaurant.getPhone_number());
                    textWeb.setText(restaurant.getWebsite());
                    textHours.setText(restaurant.getHours());
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });
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
}