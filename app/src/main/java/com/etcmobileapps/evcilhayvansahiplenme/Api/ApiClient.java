package com.etcmobileapps.evcilhayvansahiplenme.Api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;
    private static Retrofit retrofit2=null;
    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("..")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        return retrofit;
    }

    public static Retrofit getClientVersion() {

        retrofit2 = new Retrofit.Builder()
                .baseUrl("...")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        return retrofit2;
    }

}

