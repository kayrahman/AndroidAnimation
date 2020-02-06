package com.nkr.androidanimation.base

import android.app.Application


class App : Application() {

    companion object {
        var postChanging = false



    }

    override fun onCreate() {
        super.onCreate()

    }


}