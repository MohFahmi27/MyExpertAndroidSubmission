package com.mfahmi.core.data.source.local

import com.mfahmi.core.data.source.local.entity.MovieEntity
import com.mfahmi.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getMoviesDataFromDB(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getBookmarkedMoviesFromDB(): Flow<List<MovieEntity>> = movieDao.getBookmarkedMovie()

    suspend fun insertMoviesToDB(moviesEntities: List<MovieEntity>) =
        movieDao.insertMovies(moviesEntities)

    fun updateMovieFromDB(movieEntity: MovieEntity, status: Boolean) {
        movieEntity.isBookmark = status
        movieDao.updateBookmarkMovie(movieEntity)
    }

}