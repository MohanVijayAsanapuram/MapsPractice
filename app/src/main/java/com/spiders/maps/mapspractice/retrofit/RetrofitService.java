package com.spiders.maps.mapspractice.retrofit;

import com.spiders.maps.mapspractice.pojo.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by fotl on 05-07-2016.
 */
public interface RetrofitService
{

    @GET
    Call<Retrofit>  getDestinationLocationData(@Url String url);


}
