package com.project.fdelivery;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {

    private static Retrofit retrofit;
    private static RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://192.168.1.30:8080";

    private RetrofitBase() { }
    public static RetrofitInterface getRetrofitInterface()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitInterface.class);
    }



}
