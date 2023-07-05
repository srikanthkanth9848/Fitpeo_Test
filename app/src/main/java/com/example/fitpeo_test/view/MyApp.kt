package com.example.fitpeo_test

import android.app.Application
import com.example.fitpeo_test.di.component.AppComponent
import com.example.fitpeo_test.di.component.DaggerAppComponent
import com.example.fitpeo_test.di.module.NetworkModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
            private set
    }

    lateinit var appComponent: AppComponent
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this
        initComponent()
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
//        appComponent = DaggerAppComponent.builder()
//            .build()
        appComponent.inject(instance)
    }
}