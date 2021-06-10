package com.mfahmi.core.domain.usecase

import com.mfahmi.core.domain.model.Movie
import com.mfahmi.core.domain.repository.MovieRepositoryInterface
import com.mfahmi.core.utils.Resource
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: MovieRepositoryInterface) : MovieUseCase {
    override fun getAllMovieFromRepo(): Flow<Resource<List<Movie>>> =
        movieRepository.getAllMoviesData()

    override fun getBookmarkedMovieFromRepo(): Flow<List<Movie>> =
        movieRepository.getBookmarkedMoviesData()

    override suspend fun setBookmarkMovieFromRepo(movie: Movie, state: Boolean) =
        movieRepository.setBookmarkMovieFromDB(movie, state)

}