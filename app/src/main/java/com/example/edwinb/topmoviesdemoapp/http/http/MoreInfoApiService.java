package com.example.edwinb.topmoviesdemoapp.http.http;

import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.OmbdApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoreInfoApiService {
    @GET("/")
    Observable<OmbdApi> getCountry(@Query("t") String title);
}
