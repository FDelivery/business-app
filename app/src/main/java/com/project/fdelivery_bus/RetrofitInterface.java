package com.project.fdelivery_bus;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface
{
    @POST("/api/v1/auth/login/") //return token
    Call<String> connect(@Body HashMap<String, String> map);

    @POST("/api/v1/auth/register/") //return userid
    Call<String> register(@Body Business b);

    @GET("/api/v1/users/{user_id}") //return gson string (user)
    Call<String> getUser(@Path("user_id") String id);

    @PUT("/api/v1/users/{user_id}") //return void
    Call<Void> updateUser(@Path("user_id") String id);

    @GET("/api/v1/deliveriesRef/{delivery_id}") ////return gson string (Delivery)
    Call<String> getDelivery(@Path("delivery_id") String id);


    @POST("/api/v1/deliveriesRef/") //return deliveryID
    Call<String> insertNewDelivery(@Body Delivery d);



    @PUT("/api/v1/deliveriesRef/<string:delivery_id>/") //return deliveryID
    Call<String> updateDelivery(@Path("user_id") String id );
}
