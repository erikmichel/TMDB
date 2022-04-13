package com.example.fragments.Model.List;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lists {
    public ArrayList<ListModel> results;

    public Lists(ArrayList<ListModel> results) {
        this.results = results;
    }

    public ArrayList<ListModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<ListModel> lists) {
        this.results = lists;
    }
}
