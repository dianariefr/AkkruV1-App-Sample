package com.akkru.user.akkru.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Outlet {
    @SerializedName("outlets")
    public List<CatBarModel> items;

    public Outlet(List<CatBarModel> items2) {
        this.items = items2;
    }

    public List<CatBarModel> getItems() {
        return items;
    }

    public void setItems(List<CatBarModel> items) {
        this.items = items;
    }
}