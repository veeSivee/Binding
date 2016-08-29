package com.example.vi.binding.api;

//import android.database.Observable;

import com.example.vi.binding.DataGithub;
import com.example.vi.binding.DataRequest;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.List;

//import okhttp3.OkHttpClient;
//import okhttp.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Observable;


//=========

/**
 * Created by taufiqotulfaidah on 8/26/16.
 */
public class ApiRequest {

    private ApiService apiService;

    public ApiRequest(){
        apiService = getRetrofit().create(ApiService.class);
    }

    private Retrofit getRetrofit(){
        OkHttpClient okhttp = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp.interceptors().add(interceptor);

        return new Retrofit.Builder().baseUrl(DataRequest.BASE_URL)
                .client(okhttp)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public Observable<List<DataGithub>> getUserRepo(String username){
        return apiService.getUserRepo(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
