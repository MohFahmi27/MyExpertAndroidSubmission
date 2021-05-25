package com.mfahmi.myexpertandroidsubmission.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mfahmi.core.domain.usecase.MovieUseCase

class MainViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movies = movieUseCase.getAllMovieFromRepo().asLiveData()
}