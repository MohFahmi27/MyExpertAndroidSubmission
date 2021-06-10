package com.mfahmi.core.domain.usecase

import com.mfahmi.core.domain.model.Movie
import com.mfahmi.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovieFromRepo(): Flow<Resource<List<Movie>>>
    fun getBookmarkedMovieFromRepo(): Flow<List<Movie>>
    suspend fun setBookmarkMovieFromRepo(movie: Movie, state: Boolean)
}