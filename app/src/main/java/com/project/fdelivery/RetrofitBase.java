package com.project.fdelivery;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private final String BASE_URL = "http://192.168.1.30:8080";

    RetrofitBase() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }
    public RetrofitInterface getRetrofitInterface() { return retrofitInterface; }



}
