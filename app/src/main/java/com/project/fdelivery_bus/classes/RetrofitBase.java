package com.project.fdelivery_bus.classes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {

    private static Retrofit retrofit;
    public static final String BASE_URL = "http://192.168.1.103:5000";

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
