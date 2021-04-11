package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

public class createusers extends AppCompatActivity {

    private final String TAG = "createusers";

    private static RestaurantApi restaurantApi;
    private Retrofit retrofit;

    private Button visitorBtn;
    private Button addUserBtn;
    private Button connectUserBtn;
    private EditText NameUser;
    private EditText EmailUser;
    private EditText LoginUser;
    private EditText FirstnameUser;
    private EditText AgeUser;
    private EditText PasswordUser;


    private MyListViewAdapter myListViewAdapter;

    private List<Users> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createusers);
        users = new ArrayList<>();

        this.LoginUser = (EditText) findViewById(R.id.TextUsername);
        this.EmailUser = (EditText) findViewById(R.id.Textemail);
        this.NameUser = (EditText) findViewById(R.id.TextName);
        this.FirstnameUser = (EditText) findViewById(R.id.TextFirstname);
        this.AgeUser = (EditText) findViewById(R.id.TextAge);
        this.PasswordUser = (EditText) findViewById(R.id.TextPassword);

        this.addUserBtn = (Button) findViewById(R.id.buttonregister);
        this.connectUserBtn = (Button) findViewById(R.id.buttonconnect);
        this.visitorBtn = (Button) findViewById(R.id.buttonvisiteur);

        connectUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityConnect();
            }
        });
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = LoginUser.getText().toString().trim();
                String email = EmailUser.getText().toString().trim();

                String name = NameUser.getText().toString().trim();
                String firstname = FirstnameUser.getText().toString().trim();
                Integer age = Integer.valueOf(AgeUser.getText().toString().trim());
                String password =  PasswordUser.getText().toString().trim();
                Users user1 = new Users(name, login, email, firstname, age, password);
                addUser(user1);
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

    private void StartActivityConnect() {
        Intent intent = new Intent(this, authentication.class);
        startActivity(intent);
    }

    private void addUser(Users user) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);

        restaurantApi.postUsers(user).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                Log.d(TAG, "Code: " + response.code());
                Log.d(TAG, "Message: " + response.message());
                Log.d(TAG, "Message: " + response.errorBody());
                if (response.code() == 400) {
                    StartReset();
                } else {
                    StartactivityCreated();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }

        });


    }
    private void StartReset() {
        Intent intent = new Intent(this, createusers.class);
        startActivity(intent);
    }

    private void StartactivityCreated() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}