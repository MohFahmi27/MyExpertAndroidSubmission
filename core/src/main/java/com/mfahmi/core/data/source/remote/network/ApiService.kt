package com.mfahmi.core.data.source.remote.network

import com.mfahmi.core.data.source.remote.network.ApiCredentials.API_KEY
import com.mfahmi.core.data.source.remote.response.ResponseItemsMovie
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/latest")
    fun getLatestMovies(@Query("api_key") apiKey: String = API_KEY): ResponseItemsMovie
}