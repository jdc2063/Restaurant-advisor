package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

public class MainActivity2 extends AppCompatActivity {

    private final String TAG = "MainActivity2";

    private static RestaurantApi restaurantApi;
    private Retrofit retrofit;

    private Button deleteRestaurantBtn;
    private Button changeRestaurantBtn;
    private EditText editText;
    private ListView listView;
    private MyListViewAdapter myListViewAdapter;

    private List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Integer restaurantId = Integer.parseInt(getIntent().getStringExtra("restName"));

        this.changeRestaurantBtn = (Button) findViewById(R.id.buttonchange);
        this.deleteRestaurantBtn = (Button) findViewById(R.id.buttondelete);

        changeRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityChange(String.valueOf(restaurantId));
            }
        });

        deleteRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRestaurant(restaurantId);
            }
        });
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

    private void startActivityChange(String restaurantId) {
        Intent intent = new Intent(this, Change_restaurant.class);
        intent.putExtra("restName", restaurantId);
        startActivity(intent);
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

    private void deleteRestaurant(int id) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);

        restaurantApi.deleteRestaurant(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "Code: " + response.code());//Log.d(TAG, "onResponse: " + response.body().toString());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}