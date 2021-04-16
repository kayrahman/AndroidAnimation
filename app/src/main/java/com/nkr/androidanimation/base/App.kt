package com.nkr.androidanimation.base

import android.app.Application
import com.nkr.androidanimation.BuildConfig
import timber.log.Timber

class App : Application() {

    companion object {
        var postChanging = false



    }

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }


    }


}