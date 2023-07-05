package com.example.fitpeo_test

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("/photos")
    suspend fun dataResponse() : Response<List<ResponseDataItem>>

//    companion object {
//        var retrofitService: Retrofit? = null
//        fun getInstance() : Retrofit {
//            if (retrofitService == null) {
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://howtodoandroid.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                retrofitService = retrofit.create(Retrofit::class.java)
//            }
//            return retrofitService!!
//        }
//
//    }
}