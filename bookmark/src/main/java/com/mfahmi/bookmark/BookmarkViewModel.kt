package com.mfahmi.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mfahmi.core.domain.usecase.MovieUseCase

class BookmarkViewModel(movieUseCase: MovieUseCase): ViewModel() {
    val bookmarkMovies = movieUseCase.getBookmarkedMovieFromRepo().asLiveData()
}