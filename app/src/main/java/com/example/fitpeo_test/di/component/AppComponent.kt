package com.example.fitpeo_test.di.component

import com.example.fitpeo_test.view.MyApp
import com.example.fitpeo_test.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MyApp)
}