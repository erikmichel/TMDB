package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.Model.Film.FavCollection;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.List.List;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {
    boolean isFav = false;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getArguments();
        Film film = (Film) bundle.getSerializable("Film");

        TextView txtDetailTitle = view.findViewById(R.id.txtDetailTitle);
        TextView txtDetailDesc = view.findViewById(R.id.txtDetailDesc);
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        ImageButton btnFav = view.findViewById(R.id.btnFav);
        ImageButton btnAddtoList = view.findViewById(R.id.btnAddtoList);


        txtDetailTitle.setText(film.getOriginal_title());
        txtDetailDesc.setText(film.getOverview());

        GlideApp.with(getContext())
                .load(BASE_IMG_URL + film.getPoster_path())
                .centerCrop()
                .into(imgDetail);

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<FavCollection> call = apiCall.getFavData(API_KEY, SESSION_ID);
        call.enqueue(new Callback<FavCollection>(){
            @Override
            public void onResponse(Call<FavCollection> call, Response<FavCollection> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    ArrayList<Film> arraySearch = new ArrayList<>();
                    arraySearch = response.body().getResults();
                    for(Film favFilm: arraySearch) {
                        if(film.getId() == favFilm.getId()) {
                            btnFav.setImageResource(R.drawable.ic_fav_on);
                            isFav = true;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FavCollection> call, Throwable t) {

            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFav) {
                    btnFav.setImageResource(R.drawable.ic_fav_off);
                    ApiCall apiCall = retrofit.create(ApiCall.class);
                    FavFilmRequest favFilmRequest = new FavFilmRequest("movie", film.getId(), false);
                    Call<FavFilmResponse> call = apiCall.postFavMovie(API_KEY, SESSION_ID, favFilmRequest);
                    call.enqueue(new Callback<FavFilmResponse>() {
                        @Override
                        public void onResponse(Call<FavFilmResponse> call, Response<FavFilmResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<FavFilmResponse> call, Throwable t) {

                        }
                    });
                } else {
                    btnFav.setImageResource(R.drawable.ic_fav_on);
                    ApiCall apiCall = retrofit.create(ApiCall.class);
                    FavFilmRequest favFilmRequest = new FavFilmRequest("movie", film.getId(), true);
                    Call<FavFilmResponse> call = apiCall.postFavMovie(API_KEY, SESSION_ID, favFilmRequest);
                    call.enqueue(new Callback<FavFilmResponse>() {
                        @Override
                        public void onResponse(Call<FavFilmResponse> call, Response<FavFilmResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<FavFilmResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        btnAddtoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;

    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_movie_to_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        ArrayList<List> arrayList = new ArrayList<List>();
        arrayList.add(new List("Comedia", 8));
        arrayList.add(new List("Ciència", 8));
        arrayList.add(new List("Terror", 8));
        arrayList.add(new List("Comedia", 8));
        arrayList.add(new List("Ciència", 8));
        arrayList.add(new List("Terror", 8));
        arrayList.add(new List("Comedia", 8));
        arrayList.add(new List("Ciència", 8));
        arrayList.add(new List("Terror", 8));


        RecyclerView recyclerView = alertCustomdialog.findViewById(R.id.recyclerList);
        AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}