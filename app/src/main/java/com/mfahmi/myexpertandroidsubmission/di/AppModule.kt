package com.mfahmi.myexpertandroidsubmission.di

import com.mfahmi.core.domain.usecase.MovieInteractor
import com.mfahmi.core.domain.usecase.MovieUseCase
import com.mfahmi.myexpertandroidsubmission.detail.DetailViewModel
import com.mfahmi.myexpertandroidsubmission.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}