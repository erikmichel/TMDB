package com.example.fragments.Model.List;

public class ListModel {
    String name;
    String description;
    int item_count;

    public ListModel(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public ListModel(String name, String description, int item_count) {
        this.name = name;
        this.description = description;
        this.item_count = item_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }
}
