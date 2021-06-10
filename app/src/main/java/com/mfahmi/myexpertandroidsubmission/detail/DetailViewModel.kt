package com.mfahmi.myexpertandroidsubmission.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfahmi.core.domain.model.Movie
import com.mfahmi.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setBookmarkMovie(movie: Movie, newState: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.setBookmarkMovieFromRepo(movie, newState)
        }
}