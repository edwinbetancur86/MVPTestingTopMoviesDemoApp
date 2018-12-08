package com.example.edwinb.topmoviesdemoapp.topmovies;

import com.example.edwinb.topmoviesdemoapp.http.http.MoreInfoApiService;
import com.example.edwinb.topmoviesdemoapp.http.http.MovieApiService;
import com.example.edwinb.topmoviesdemoapp.topmovies.Repository;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivityMVP;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesModel;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesPresenter;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TopMoviesModule {
    @Provides
    public TopMoviesActivityMVP.Presenter provideTopMoviesActivityPresenter(TopMoviesActivityMVP.Model topMoviesModel){
        return new TopMoviesPresenter(topMoviesModel);
    }

    @Provides
    public TopMoviesActivityMVP.Model provideTopMoviesActivityModel(Repository repository){
        return new TopMoviesModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideRepo(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService){
        return new TopMoviesRepository(movieApiService, moreInfoApiService);
    }
}
