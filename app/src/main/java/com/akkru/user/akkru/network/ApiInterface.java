package com.akkru.user.akkru.network;

import com.akkru.user.akkru.model.Outlet;
import com.akkru.user.akkru.model.Response2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("api/v1/outlet/{outletnya}/")
    Call<Outlet> getAllBar(@Path("outletnya") String outletnya);
}