package com.example.fragments.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultConstants {

    public static final String API_KEY = "bf0d5e07471e8bb24f63bde7838f892a";
    public static final String SESSION_ID = "3959ddefb24dd4134fb5033289aa323bbf208cfd";
    public static final String ACCOUNT_ID = "ErikMichel";

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500/";

    public static final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.themoviedb.org/3/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

}
