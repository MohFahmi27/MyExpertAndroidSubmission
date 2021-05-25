package com.mfahmi.bookmark.di

import com.mfahmi.bookmark.BookmarkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    viewModel { BookmarkViewModel(get()) }
}