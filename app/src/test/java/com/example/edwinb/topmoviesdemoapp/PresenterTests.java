package com.example.edwinb.topmoviesdemoapp;

import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.OmbdApi;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.Result;
import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.TopRated;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivityMVP;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesPresenter;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesRepository;
import com.example.edwinb.topmoviesdemoapp.topmovies.ViewModel;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/***********************************
 * A test suite example for testing
 * MVP presenter logic
 ***********************************/
@RunWith (MockitoJUnitRunner.class)
public class PresenterTests  {

    @Mock
    TopMoviesActivityMVP.Model mockTopMoviesModel;
    @Mock
    TopMoviesActivityMVP.View mockTopMoviesView;

    TopMoviesPresenter mTopMoviesPresenter;

    // Set up our mocked object chain from Movie API calls
    TopRated mTopRated;
    List<Result> mResultList;
    Result mResult;
    OmbdApi mOmbdApi;
    // For returning cached countries
    List<String> mCountriesList;
    ViewModel mViewModel;

    @BeforeClass
    public static void setupClass() {
        // Override the default "out of the box" AndroidSchedulers.mainThread() Scheduler
        //
        // This is necessary here because otherwise if the static initialization block in AndroidSchedulers
        // is executed before this, then the Android SDK dependent version will be provided instead.
        //
        // This would cause a java.lang.ExceptionInInitializerError when running the test as a
        // Java JUnit test as any attempt to resolve the default underlying implementation of the
        // AndroidSchedulers.mainThread() will fail as it relies on unavailable Android dependencies.

        // Comment out this line to see the java.lang.ExceptionInInitializerError
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        mResult = new Result("The Godfather");

        mResultList = new ArrayList<Result>();

        mResultList.add(mResult);

        mTopRated = new TopRated(mResultList);

        mOmbdApi = new OmbdApi("USA");

        mCountriesList = new ArrayList<>();

        mCountriesList.add("India");

        mViewModel = new ViewModel("The Godfather", "USA");

        mTopMoviesPresenter = new TopMoviesPresenter(mockTopMoviesModel);

        mTopMoviesPresenter.setView(mockTopMoviesView);

    }

    @AfterClass
    public static void tearDownClass() {
        // Not strictly necessary because we can't reset the value set by setInitMainThreadSchedulerHandler,
        // but it doesn't hurt to clean up anyway.
        RxAndroidPlugins.reset();
    }

    @Test
    public void verifyThatTopMoviesResultIsCalled() {

        when(mockTopMoviesModel.result()).thenReturn(Observable.just(mViewModel));

        mTopMoviesPresenter.loadData();

        // Verify model interactions
        verify(mockTopMoviesModel, times(1)).result();

    }

    @Test
    public void errorMessageShouldNotBeShownWhenTopMoviesAreLoaded(){
        when(mockTopMoviesModel.result()).thenReturn(Observable.just(mViewModel));
        mTopMoviesPresenter.loadData();
        verify(mockTopMoviesModel, times(1)).result();
        verify(mockTopMoviesView, times(1)).updateData(mViewModel);
        verify(mockTopMoviesView, never()).showSnackbar("Error getting movies");
    }

    @Test (expected = NullPointerException.class)
    public void resultShouldThrowAnExceptionIfViewModelIsNull() throws Exception {
        when(mockTopMoviesModel.result()).thenReturn(null);
        mTopMoviesPresenter.loadData();
        verify(mockTopMoviesModel, never()).result();
    }


}
