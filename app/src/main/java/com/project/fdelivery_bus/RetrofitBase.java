package com.project.fdelivery_bus;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {

    private static Retrofit retrofit;
    public static final String BASE_URL = "http://10.100.102.121:5000";

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
