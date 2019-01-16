package com.example.edwinb.topmoviesdemoapp.topmovies;

import com.example.edwinb.topmoviesdemoapp.http.http.MoreInfoApiService;
import com.example.edwinb.topmoviesdemoapp.http.http.MovieApiService;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.OmbdApi;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.Result;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.TopRated;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class TopMoviesRepository implements Repository {

    private MovieApiService mMovieApiService;
    private MoreInfoApiService mMoreInfoApiService;
    private List<String> countries;
    private List<Result> mResultList;
    private long timestamp;

    private static final long STALE_MS = 20 * 1000; // Data is stale after 20 seconds

    public TopMoviesRepository(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService){
        this.mMoreInfoApiService = moreInfoApiService;
        this.mMovieApiService = movieApiService;
        // For caching
        this.timestamp = System.currentTimeMillis();
        countries = new ArrayList<>();
        mResultList = new ArrayList<>();
    }

    public boolean isUpToDate(){
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

    @Override
    public Observable<Result> getResultsFromMemory() {
        if (isUpToDate()){
            return getCachedResults(mResultList);
        }
        else{
            timestamp = System.currentTimeMillis();
            mResultList.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getCachedResults(List<Result> resultsList) {
        return Observable.fromIterable(resultsList);
    }

    @Override
    public Observable<TopRated> getTopRatedObservableFromApiService() {
        return mMovieApiService.getTopRatedMovies(1);
    }

    @Override
    public Observable<Result> getResultsFromNetwork() {
        // Commented two lines to avoid a an error with second API call
        Observable<TopRated> topRatedObservable = getTopRatedObservableFromApiService();
                /*.concatWith(mMovieApiService.getTopRatedMovies(2))
                .concatWith(mMovieApiService.getTopRatedMovies(3))*/;

        /*Observable<TopRated> topRatedObservable = mMovieApiService.getTopRatedMovies(1)
                .concatWith(mMovieApiService.getTopRatedMovies(2))
                .concatWith(mMovieApiService.getTopRatedMovies(3));*/

        return topRatedObservable.concatMap(new Function<TopRated, Observable<Result>>() {
            @Override
            public Observable<Result> apply(TopRated topRated) {
                return Observable.fromIterable(topRated.getResults());
            }
        }).doOnNext(new Consumer<Result>() {
            @Override
            public void accept(Result results) {
                mResultList.add(results);
            }
        });
    }

    @Override
    public Observable<String> getCountriesFromMemory() {
        if (isUpToDate()){
            return getCachedCountries(countries);
        }
        else{
            timestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCachedCountries(List<String> countriesList) {
        return Observable.fromIterable(countriesList);
    }

    @Override
    public Observable<OmbdApi> getOmbdFromApiService(Result result) {
        return mMoreInfoApiService.getCountry(result.getTitle());
    }

    @Override
    public Observable<String> getCountriesFromNetwork() {
        return getResultsFromNetwork().concatMap(new Function<Result, Observable<OmbdApi>>() {
            @Override
            public Observable<OmbdApi> apply(Result results) {
                return getOmbdFromApiService(results);
            }
        }).concatMap(new Function<OmbdApi, Observable<String>>() {
            @Override
            public Observable<String> apply(OmbdApi omdbApi)  {
                return Observable.just(omdbApi.getCountry());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) {
                countries.add(s);
            }
        });
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountriesFromMemory().switchIfEmpty(getCountriesFromNetwork());
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultsFromMemory().switchIfEmpty(getResultsFromNetwork());
    }
}
