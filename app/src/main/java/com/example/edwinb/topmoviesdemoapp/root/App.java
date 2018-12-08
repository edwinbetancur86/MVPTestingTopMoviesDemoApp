package com.example.edwinb.topmoviesdemoapp.root;

import android.app.Application;

import com.example.edwinb.topmoviesdemoapp.http.http.ApiModuleForInfo;
import com.example.edwinb.topmoviesdemoapp.http.http.ApiModuleForName;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesModule;

public class App extends Application {
    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModuleForName(new ApiModuleForName())
                .topMoviesModule(new TopMoviesModule())
                .apiModuleForInfo(new ApiModuleForInfo())
                .build();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }
}
