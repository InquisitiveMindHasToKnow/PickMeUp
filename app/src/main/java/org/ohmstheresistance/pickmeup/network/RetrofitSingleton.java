package org.ohmstheresistance.pickmeup.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static Retrofit retrofitInstance;

    private RetrofitSingleton(){}


    public static Retrofit getRetrofitInstance() {
        if (retrofitInstance != null) {
            return retrofitInstance;
        }

        retrofitInstance = new Retrofit
                .Builder()
                .baseUrl("https://gist.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofitInstance;
    }
}