package com.example.fitpeo_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitpeo_test.viewmodel.ResponseViewModel

class MyViewModelFactory constructor(private val repository: ApiInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ResponseViewModel::class.java)) {
            ResponseViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}