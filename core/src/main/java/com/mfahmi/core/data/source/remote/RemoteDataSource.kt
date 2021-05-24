package com.mfahmi.core.data.source.remote

import com.mfahmi.core.data.source.local.entity.MovieEntity
import com.mfahmi.core.data.source.remote.network.ApiResponse
import com.mfahmi.core.data.source.remote.network.ApiResponse.*
import com.mfahmi.core.data.source.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMoviesFromAPI(): Flow<ApiResponse<List<MovieEntity>>> =
        flow {
            try {
                val response = apiService.getLatestMovies().results
                if (response.isNotEmpty()) emit(Success(response)) else emit(Empty)
            } catch (e: Exception) {
                emit(Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

}