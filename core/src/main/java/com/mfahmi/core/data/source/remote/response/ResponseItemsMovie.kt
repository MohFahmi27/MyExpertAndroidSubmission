package com.mfahmi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.mfahmi.core.data.source.local.entity.MovieEntity

data class ResponseItemsMovie(
    @SerializedName("results")
    val results: List<MovieEntity>
)
