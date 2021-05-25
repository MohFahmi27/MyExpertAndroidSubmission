package com.mfahmi.core.data.source.remote

import com.mfahmi.core.data.source.remote.network.ApiResponse
import com.mfahmi.core.data.source.remote.network.ApiResponse.*
import com.mfahmi.core.data.source.remote.network.ApiService
import com.mfahmi.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMoviesFromAPI(): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getLatestMovies()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) emit(Success(response.results)) else emit(Empty)
            } catch (e: Exception) {
                emit(Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

}