package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantApi {

    @GET("restaurants")
    Call<List<Restaurant>> getRestaurants();

    @POST("restaurants")
    Call<Restaurant> postRestaurant(@Body Restaurant restaurant);

    @GET("restaurants/{id}")
    Call<Restaurant> getRestaurantId(@Path("id") int restId);

    @PUT("restaurants/{id}")
    Call<Restaurant> putRestaurantId(@Path("id") int id, @Body Restaurant restaurant);

    @DELETE("restaurants/{id}")
    Call<Void> deleteRestaurant(@Path("id") int id);

    @POST("register")
    Call<Users> postUsers(@Body Users users);

    @POST("auth")
    Call<Users> AuthUsers(@Body Users users);
}
