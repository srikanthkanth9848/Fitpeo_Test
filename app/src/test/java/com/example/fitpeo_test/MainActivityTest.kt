package com.example.fitpeo_test

import com.example.fitpeo_test.network.ApiInterface
import com.example.fitpeo_test.network.LoadingState
import com.example.fitpeo_test.viewmodel.ResponseViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
@HiltAndroidTest
class MainActivityTest {
    @Inject
    lateinit var apiInterface: ApiInterface

    private lateinit var viewModel: ResponseViewModel
    private lateinit var fakeWeatherUseCase: ResponseDataItem
    private lateinit var loadingstate:LoadingState

    @Before
    fun setup(){
        loadingstate = LoadingState.LOADED
    }


}