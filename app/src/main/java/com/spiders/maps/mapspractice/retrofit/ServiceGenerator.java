package com.spiders.maps.mapspractice.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fotl on 05-07-2016.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL="http://maps.googleapis.com/";

    private  static OkHttpClient.Builder httpclient= new OkHttpClient.Builder();

    private static Retrofit.Builder builder=new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass)
    {
        Retrofit retrofit = builder.client(httpclient.connectTimeout(200, TimeUnit.SECONDS).readTimeout(200,TimeUnit.SECONDS).build()).build();
        return retrofit.create(serviceClass);
    }
}
