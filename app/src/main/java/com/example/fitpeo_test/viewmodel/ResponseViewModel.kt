package com.example.fitpeo_test.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitpeo_test.network.ApiInterface
import com.example.fitpeo_test.network.LoadingState
import com.example.fitpeo_test.model.ResponseDataItem
import com.example.fitpeo_test.ResponseDataItem
import com.example.fitpeo_test.view.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

// @HiltViewModel will make models to be
// created using Hilt's model factory
// @Inject annotation used to inject all
// dependencies to view model class
@HiltViewModel
class ResponseViewModel @Inject constructor(var apiInterface: ApiInterface) :
    ViewModel() {
    private val loadingState = MutableLiveData<LoadingState>()
    val loadingData: LiveData<LoadingState> get() = loadingState

    private lateinit var context: Context

    private val responseData = MutableLiveData<List<ResponseDataItem>>()
    val getData: LiveData<List<ResponseDataItem>> get() = responseData
    private val resposity: MyRepository = MyRepository(apiInterface)

    val commentState = MutableStateFlow(
        CommentApiState(
            Status.LOADING,
            listOf(ResponseDataItem()),
            ""
        )
    )

    init {
        //fetchData()
        flowData()
    }

    private fun flowData() {
        //since network calls takes time,set the
        //initial value to loading state...
        commentState.value = CommentApiState.loading()
        viewModelScope.launch {
            resposity.getComment().catch {
                commentState.value = CommentApiState.error(
                    it.message.toString()
                )
            }.collect {
//                var responseDataItem = ResponseDataItem()
                val myList: List<ResponseDataItem>? = it.data?.body()
                for (i in myList!!.indices) {
                    //responseDataItem = myList[i]
                    commentState.value = CommentApiState.success(it.data.body())
                }
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadingState.postValue(LoadingState.LOADING)
                val responseList = apiInterface.dataResponse()
                Log.e("responseViewModel", "fetchData $responseList")
                if (responseList.isSuccessful) {
                    responseData.postValue(responseList.body())
                val responseDataa = apiInterface.dataResponse()
                Log.e("myResp", "dataResp $responseDataa")
                if (responseDataa.isSuccessful) {
                    responseData.postValue(responseDataa.body())
                    loadingState.postValue(LoadingState.LOADED)
                } else {
                    loadingState.postValue(LoadingState.error(responseList.message()))
                }
            } catch (e: Exception) {
                loadingState.postValue(LoadingState.error(e.message))
                Log.e("responseViewModel", "No address associated with hostname")
            }
        }
    }
}