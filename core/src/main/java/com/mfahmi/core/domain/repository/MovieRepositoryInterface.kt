package com.mfahmi.core.domain.repository

import com.mfahmi.core.domain.model.Movie
import com.mfahmi.core.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MovieRepositoryInterface {

    fun getAllMoviesData(): Flow<Resource<List<Movie>>>

    fun getBookmarkedMoviesData(): Flow<List<Movie>>

    suspend fun setBookmarkMovieFromDB(movie: Movie, state: Boolean)

}