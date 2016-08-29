package com.example.vi.binding.api;

//import android.database.Observable;

import com.example.vi.binding.DataGithub;
import com.example.vi.binding.DataRequest;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by taufiqotulfaidah on 8/26/16.
 */
public interface ApiService {

    @GET("users/{user}/repos")
    Observable<List<DataGithub>> getUserRepo(@Path("user") String user);

}
