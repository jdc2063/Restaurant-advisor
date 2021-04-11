package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {

    private final String TAG = "MainActivity3";

    private static RestaurantApi restaurantApi;
    private Retrofit retrofit;

    private Button addRestaurantBtn;
    private EditText NameRest;
    private EditText GradeRest;
    private EditText DescriptionRest;
    private EditText LocalizationRest;
    private EditText PhoneRest;
    private EditText WebsiteRest;
    private EditText HoursRest;


    private MyListViewAdapter myListViewAdapter;

    private List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        restaurants = new ArrayList<>();

        this.NameRest = (EditText) findViewById(R.id.et_restname2);
        this.GradeRest = (EditText) findViewById(R.id.et_restgrade);
        this.DescriptionRest = (EditText) findViewById(R.id.et_restdescription);
        this.LocalizationRest = (EditText) findViewById(R.id.et_restlocalization);
        this.PhoneRest = (EditText) findViewById(R.id.et_restphone);
        this.WebsiteRest = (EditText) findViewById(R.id.et_restwebsite);
        this.HoursRest = (EditText) findViewById(R.id.et_resthours);

        this.addRestaurantBtn = (Button) findViewById(R.id.ButtonAddRestaurant1);

        addRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = NameRest.getText().toString().trim();
                String description = DescriptionRest.getText().toString().trim();

                Float grade = Float.valueOf(GradeRest.getText().toString().trim());
                String localization = LocalizationRest.getText().toString().trim();
                String phone = PhoneRest.getText().toString().trim();
                String website =  WebsiteRest.getText().toString().trim();
                String hours = HoursRest.getText().toString().trim();
                Restaurant restaurant1 = new Restaurant(name, description, grade, localization, phone, website, hours);
                addRestaurant(restaurant1);
            }
        });
        this.configureRetrofit();
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

    private void addRestaurant(Restaurant restaurant) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);

        restaurantApi.postRestaurant(restaurant).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                Log.d(TAG, "envoie: " + restaurant.getGrade());
                Log.d(TAG, "Code: " + response.message());
                GradeRest.setTextColor(Color.GREEN);
                //Log.d(TAG, "onResponse: " + response.body().toString());

            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}