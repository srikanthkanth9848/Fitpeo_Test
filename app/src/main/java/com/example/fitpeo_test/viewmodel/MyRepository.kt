package com.example.fitpeo_test.viewmodel

import com.example.fitpeo_test.model.ResponseDataItem
import com.example.fitpeo_test.network.ApiInterface
import com.example.fitpeo_test.network.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class MyRepository(private val apiService: ApiInterface) {
    suspend fun getComment(): Flow<CommentApiState<Response<List<ResponseDataItem>>>> {
        val flows = flow {

            // get the comment Data from the api
            val comment = apiService.dataResponse()

            // Emit this data wrapped in
            // the helper class [CommentApiState]
            emit(CommentApiState.success(comment))
        }.flowOn(Dispatchers.IO)
        return flows
    }
}