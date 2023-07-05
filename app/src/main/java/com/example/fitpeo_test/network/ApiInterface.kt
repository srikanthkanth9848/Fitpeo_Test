package com.example.fitpeo_test.network

import com.example.fitpeo_test.ResponseDataItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/photos")
    suspend fun dataResponse() : Response<List<ResponseDataItem>>
}