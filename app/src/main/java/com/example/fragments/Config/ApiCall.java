package com.example.fragments.Config;

import com.example.fragments.Model.Film.FavCollection;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListModelResponse;
import com.example.fragments.Model.List.Lists;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCall {

    @GET("search/movie?")
    Call<searchFilmModel> getData(@Query("api_key") String api_key, @Query("query") String query);

    @GET("account/ErikMichel/favorite/movies?")
    Call<FavCollection> getFavData(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @GET("account/ErikMichel/lists?")
    Call<Lists> getLists(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @POST("account/ErikMichel/favorite?")
    Call<FavFilmResponse> postFavMovie(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body FavFilmRequest favFilmRequest);

    @POST("list?")
    Call<ListModelResponse> postList(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body ListModel listModel);

}
