package com.etcmobileapps.evcilhayvansahiplenme.api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;
    private static Retrofit retrofit2=null;
    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://patisahiplen.etcmobileapps.com:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        return retrofit;
    }

    public static Retrofit getClientVersion() {

        retrofit2 = new Retrofit.Builder()
                .baseUrl("http://patisahiplen.etcmobileapps.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        return retrofit2;
    }

}

