package com.example.edwinb.topmoviesdemoapp.topmovies;

import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.Result;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class TopMoviesModel implements TopMoviesActivityMVP.Model {

    private Repository mRepository;

    public TopMoviesModel(Repository repository){
        this.mRepository = repository;
    }

    // Extract the name of the movie from the result
    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(mRepository.getResultData(), mRepository.getCountryData(),
                new BiFunction<Result, String, ViewModel>() {
                    @Override
                    public ViewModel apply(Result result, String s) {
                        return new ViewModel(result.getTitle(), s);
                    }
                });
    }

}
