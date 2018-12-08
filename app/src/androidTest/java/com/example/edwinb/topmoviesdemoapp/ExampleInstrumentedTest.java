package com.example.edwinb.topmoviesdemoapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivity;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivityMVP;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesModel;
import com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.edwinb.topmoviesdemoapp", appContext.getPackageName());
    }
}
