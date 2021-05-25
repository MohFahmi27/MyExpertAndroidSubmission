package com.mfahmi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseItemsMovie(
    @SerializedName("results")
    val results: List<MovieResponse>
)
