package com.example.playlist_maker_android_levchenkosergey.di

import com.example.playlist_maker_android_levchenkosergey.ui.view_model.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * DI-модуль ViewModel.
 *
 * Вынесен из [com.example.playlist_maker_android_levchenkosergey.MyApplication]
 * отдельным файлом: когда экранов и ViewModel станет больше, `onCreate()`
 * не превратится в свалку из регистраций.
 *
 * `viewModel { SearchViewModel(get()) }` означает: Koin сам достанет из графа
 * репозиторий и передаст его в конструктор.
 */
val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }
}
