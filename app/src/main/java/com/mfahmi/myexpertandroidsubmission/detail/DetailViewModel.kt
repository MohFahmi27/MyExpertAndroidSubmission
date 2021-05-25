package com.mfahmi.myexpertandroidsubmission.detail

import androidx.lifecycle.ViewModel
import com.mfahmi.core.domain.model.Movie
import com.mfahmi.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setBookmarkMovie(movie: Movie, newState: Boolean) =
        movieUseCase.setBookmarkMovieFromRepo(movie, newState)
}