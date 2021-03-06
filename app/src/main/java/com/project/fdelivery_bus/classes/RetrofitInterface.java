package com.project.fdelivery_bus.classes;

import com.project.fdelivery_bus.classes.Business;
import com.project.fdelivery_bus.classes.Delivery;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface
{
    @POST("/api/v1/auth/login/") //return array [token,id]->user
    Call<String[]> connect(@Body HashMap<String, String> map);

    @POST("/api/v1/auth/register/") //return userid
    Call<String> register(@Body Business b);

    @GET("/api/v1/users/{user_id}") //return gson string (user)
    Call<String> getUser(@Path("user_id") String id);

    @PUT("/api/v1/users/{user_id}") //return void
    Call<Void> updateUser(@Path("user_id") String id,@Body HashMap<String, String> map);

    @GET("/api/v1/deliveriesRef/{delivery_id}/") ////return gson string (Delivery)
    Call<String> getDelivery(@Path("delivery_id") String id);

    @GET("/api/v1/deliveriesRef/") ////return gson string (Delivery)
    Call<List<String>> getDeliveries(@Query("AddedBy") String id);


    @POST("/api/v1/deliveriesRef/") //return deliveryID
    Call<String> insertNewDelivery(@Header("Authorization") String token, @Body Delivery d);


    @PUT("/api/v1/deliveriesRef/{delivery_id}/") //update delivery
    Call<Void> updateDelivery(@Header("Authorization") String token, @Path("delivery_id") String id, @Body Delivery d);


    @DELETE("/api/v1/deliveriesRef/{delivery_id}/") //we put deliveryId and delete it
    Call<Void> deleteDelivery(@Header("Authorization") String token, @Path("delivery_id") String id);

    @GET("/api/v1/deliveriesRef/") ////return gson string all Deliveries that delivered
    Call<List<String>> getDeliveriesHistory(@Query("status") String status ,@Query("AddedBy") String addBY);
}
