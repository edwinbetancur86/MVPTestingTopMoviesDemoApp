package com.example.edwinb.topmoviesdemoapp.topmovies;

import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.OmbdApi;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.Result;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.TopRated;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    Observable<Result> getResultsFromMemory();
    Observable<Result> getCachedResults(List<Result> results);
    Observable<TopRated> getTopRatedObservableFromApiService();
    Observable<Result> getResultsFromNetwork();
    Observable<String> getCountriesFromMemory();
    Observable<String> getCachedCountries(List<String> countries);
    Observable<OmbdApi> getOmbdFromApiService(Result result);
    Observable<String> getCountriesFromNetwork();
    Observable<String> getCountryData();
    Observable<Result> getResultData();

}
