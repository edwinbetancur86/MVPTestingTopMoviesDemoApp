package com.example.edwinb.topmoviesdemoapp.topmovies;

import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.OmbdApi;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.Result;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.TopRated;

import java.util.List;

import io.reactivex.Observable;

public interface Repository {
    Observable<TopRated> getTopRatedObservableFromApiService();

    Observable<String> getCountriesFromMemory();
    Observable<String> getCountriesFromNetwork();
    Observable<String> getCountryData();
    Observable<String> getCachedCountries(List<String> countries);

    Observable<OmbdApi> getOmbdFromApiService(Result result);

    Observable<Result> getResultsFromNetwork();
    Observable<Result> getResultsFromMemory();
    Observable<Result> getCachedResults(List<Result> results);
    Observable<Result> getResultData();

}
