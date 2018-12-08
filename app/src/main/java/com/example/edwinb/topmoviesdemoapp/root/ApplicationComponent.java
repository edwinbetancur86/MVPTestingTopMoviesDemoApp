package com.example.edwinb.topmoviesdemoapp.root;

import com.example.edwinb.topmoviesdemoapp.http.http.ApiModuleForInfo;
import com.example.edwinb.topmoviesdemoapp.http.http.ApiModuleForName;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivity;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModuleForName.class, ApiModuleForInfo.class, TopMoviesModule.class})
public interface ApplicationComponent {
    void inject(TopMoviesActivity target);
}
