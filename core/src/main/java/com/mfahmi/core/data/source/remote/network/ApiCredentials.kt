package com.mfahmi.core.data.source.remote.network

import com.mfahmi.core.BuildConfig

object ApiCredentials {
    val API_KEY by lazy { BuildConfig.API_KEY_TMDB }
    val BASE_URL by lazy { "https://api.themoviedb.org/3/" }
}