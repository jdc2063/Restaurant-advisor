package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class authentication extends AppCompatActivity {
    private final String TAG = "createusers";

    private static RestaurantApi restaurantApi;
    private Retrofit retrofit;

    private Button visitorBtn;
    private Button authUserBtn;
    private Button createBtn;
    private EditText EmailUser;
    private EditText PasswordUser;


    private MyListViewAdapter myListViewAdapter;

    private List<Users> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        users = new ArrayList<>();
        this.EmailUser = (EditText) findViewById(R.id.AuthEmail);
        this.PasswordUser = (EditText) findViewById(R.id.AuthPassword);

        this.authUserBtn = (Button) findViewById(R.id.buttonAuth);
        this.visitorBtn = (Button) findViewById(R.id.buttonVisitor);
        this.createBtn = (Button) findViewById(R.id.buttonNocompte);

        authUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailUser.getText().toString().trim();
                String password =  PasswordUser.getText().toString().trim();
                Users user1 = new Users("", "", email, "", 0, password);
                authUser(user1);
            }
        });

        this.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityCreate();
            }
        });
        visitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityVisitor();
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

    private void StartActivityVisitor() {
        Intent intent = new Intent(this, accueil_v.class);
        startActivity(intent);
    }

    private void StartActivityCreate() {
        Intent intent = new Intent(this, createusers.class);
        startActivity(intent);
    }

    private void authUser(Users user) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);

        restaurantApi.AuthUsers(user).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                Log.d(TAG, "Code: " + response.code());
                if (response.code() == 400) {
                    StartReset();
                } else {
                    StartActivityAuth();
                }
                Log.d(TAG, "Message: " + response.errorBody());
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }


        });


    }

    private void StartActivityAuth() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void StartReset() {
        Intent intent = new Intent(this, authentication.class);
        startActivity(intent);
    }
}