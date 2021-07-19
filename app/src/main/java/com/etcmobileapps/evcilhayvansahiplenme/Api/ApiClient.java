package com.etcmobileapps.evcilhayvansahiplenme.Api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(".....")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        return retrofit;
    }

}

