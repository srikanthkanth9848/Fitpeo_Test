package com.example.fitpeo_test.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitpeo_test.network.ApiInterface
import com.example.fitpeo_test.network.LoadingState
import com.example.fitpeo_test.ResponseDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// @HiltViewModel will make models to be
// created using Hilt's model factory
// @Inject annotation used to inject all
// dependencies to view model class
@HiltViewModel
class ResponseViewModel @Inject constructor(var apiInterface: ApiInterface) : ViewModel() {
    private val loadingState = MutableLiveData<LoadingState>()
    val loadingData: LiveData<LoadingState> get() = loadingState

    private val responseData = MutableLiveData<List<ResponseDataItem>>()
    val getData: LiveData<List<ResponseDataItem>> get() = responseData

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val responseDataa = apiInterface.dataResponse()
                Log.e("myResp", "dataResp " + responseDataa)
                if (responseDataa.isSuccessful!!) {
                    responseData.postValue(responseDataa.body())
                    loadingState.postValue(LoadingState.LOADED)
                } else {
                    loadingState.postValue(LoadingState.error(responseDataa.message()))
                }
            } catch (e: Exception) {
                loadingState.postValue(LoadingState.error(e.message))
            }
        }
    }
}