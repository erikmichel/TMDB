package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.FavCollection;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListModelResponse;
import com.example.fragments.Model.List.Lists;
import com.example.fragments.Recyclers.ListRecyclerView;
import com.example.fragments.Recyclers.SearchMovieRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {
    EditText name;
    EditText description;
    RecyclerView recyclerView;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //Properties
        FloatingActionButton btnAdd = view.findViewById(R.id.btnAddList);
        recyclerView = view.findViewById(R.id.recyclerLists);

        //Get all lists
        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<Lists> call = apiCall.getLists(API_KEY, SESSION_ID);
        call.enqueue(new Callback<Lists>() {
            @Override
            public void onResponse(Call<Lists> call, Response<Lists> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    ArrayList<ListModel> lists = response.body().getResults();
                    callRecycler(lists);
                }
            }

            @Override
            public void onFailure(Call<Lists> call, Throwable t) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_add_list, null);

        // Properties
        name = alertCustomdialog.findViewById(R.id.txtList);
        description = alertCustomdialog.findViewById(R.id.txtDescription);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Button btnSaveList = alertCustomdialog.findViewById(R.id.btnSaveList);

        btnSaveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Properties
                name = alertCustomdialog.findViewById(R.id.txtList);
                description = alertCustomdialog.findViewById(R.id.txtDescription);
                ApiCall apiCall = retrofit.create(ApiCall.class);
                ListModel listModel = new ListModel(name.getText().toString(), description.getText().toString());
                Call<ListModelResponse> call = apiCall.postList(API_KEY, SESSION_ID, listModel);
                call.enqueue(new Callback<ListModelResponse>() {
                    @Override
                    public void onResponse(Call<ListModelResponse> call, Response<ListModelResponse> response) {
                        Log.i("respuesta", response.message());
                        //Log.i("respuesta", response.body().getStatus_message());
                        Log.i("respuesta", response.code() + "");
                    }

                    @Override
                    public void onFailure(Call<ListModelResponse> call, Throwable t) {
                        //Log.i("respuesta", )
                    }
                });
                dialog.dismiss();
            }
        });
    }

    public void callRecycler(ArrayList<ListModel> arrayList){
        ListRecyclerView adapter = new ListRecyclerView(arrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}