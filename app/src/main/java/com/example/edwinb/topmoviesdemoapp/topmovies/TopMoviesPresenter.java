package com.example.edwinb.topmoviesdemoapp.topmovies;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TopMoviesPresenter implements TopMoviesActivityMVP.Presenter {
    private TopMoviesActivityMVP.View mView;
    private Disposable subscription = null;
    private TopMoviesActivityMVP.Model mModel;

    public TopMoviesPresenter(TopMoviesActivityMVP.Model model){
        this.mModel = model;
    }


    @Override
    public void loadData() {
        subscription = mModel
                .result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ViewModel>() {
                    @Override
                    public void onNext(ViewModel viewModel) {
                        if (mView != null){
                            mView.updateData(viewModel);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (mView != null){
                            mView.showSnackbar("Error getting movies");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null){
            if (!subscription.isDisposed()){
                subscription.dispose();
            }
        }
    }

    @Override
    public void setView(TopMoviesActivityMVP.View view) {
        this.mView = view;
    }
}
