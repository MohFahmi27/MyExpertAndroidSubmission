package com.mfahmi.core.domain.repository

import com.mfahmi.core.data.Resource
import com.mfahmi.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface MovieRepositoryInterface {

    fun getAllMoviesData(): Flow<Resource<List<Movie>>>

    fun getBookmarkedMoviesData(): Flow<List<Movie>>

    fun setBookmarkMovieFromDB(movie: Movie, state: Boolean)

}