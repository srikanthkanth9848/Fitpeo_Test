package com.sgemin.daggertwoex.di.module

import com.example.fitpeo_test.adapter.RecyclerViewAdapter
import com.example.fitpeo_test.view.MainActivity
import com.sgemin.daggertwoex.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by srikanth.saini on 02/07/2023
 */
@Module()
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @ActivityScope
    fun getStarWarsPeopleLIst(clickListener: RecyclerViewAdapter.ClickListener): RecyclerViewAdapter {
        return RecyclerViewAdapter(clickListener)
    }

    @Provides
    @ActivityScope
    fun getClickListener(mainActivity: MainActivity): RecyclerViewAdapter.ClickListener {
        return mainActivity
    }
}