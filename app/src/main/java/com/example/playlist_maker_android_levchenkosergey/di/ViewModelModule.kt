package com.example.playlist_maker_android_levchenkosergey.di

import com.example.playlist_maker_android_levchenkosergey.ui.view_model.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * DI-модуль ViewModel.
 */
val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }
}
