package com.nkr.androidanimation.base

import android.app.Application
import timber.log.Timber


class App : Application() {

    companion object {
        var postChanging = false



    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())


    }


}