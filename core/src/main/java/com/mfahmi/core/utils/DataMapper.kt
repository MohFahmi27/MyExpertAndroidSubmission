package com.mfahmi.core.utils

import com.mfahmi.core.data.source.local.entity.MovieEntity
import com.mfahmi.core.data.source.remote.response.MovieResponse
import com.mfahmi.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(movies: List<MovieResponse>): List<MovieEntity> {
        val moviesList = ArrayList<MovieEntity>()
        movies.map {
            val myMovies = MovieEntity(
                movieId = it.movieId,
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                originalLanguage = it.originalLanguage,
                isBookmark = false
            )
            moviesList.add(myMovies)
        }
        return moviesList
    }

    fun mapEntitiesToDomain(movies: List<MovieEntity>): List<Movie> {
        return movies.map {
            Movie(
                it.movieId,
                it.overview,
                it.title,
                it.posterPath,
                it.releaseDate,
                it.voteAverage,
                it.voteCount,
                it.originalLanguage,
                it.isBookmark
            )
        }
    }

    fun mapDomainToEntity(movie: Movie) = MovieEntity(
        movie.movieId,
        movie.overview,
        movie.title,
        movie.posterPath,
        movie.releaseDate,
        movie.voteAverage,
        movie.voteCount,
        movie.originalLanguage,
        movie.isBookmark
    )
}