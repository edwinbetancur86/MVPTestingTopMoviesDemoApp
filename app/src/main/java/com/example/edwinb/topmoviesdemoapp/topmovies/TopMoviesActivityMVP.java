package com.example.edwinb.topmoviesdemoapp.topmovies;

import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.Result;

import io.reactivex.Observable;

public interface TopMoviesActivityMVP {
    interface View {
        void updateData(ViewModel viewModel);
        void showSnackbar(String s);
    }

    interface Presenter{
        void loadData();
        void rxUnsubscribe();
        void setView(TopMoviesActivityMVP.View view);
    }

    interface Model{
        Observable<ViewModel> result();
        //Observable<Result> result2();
    }
}
