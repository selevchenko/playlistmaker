package com.example.playlist_maker_android_levchenkosergey.data.di

import com.example.playlist_maker_android_levchenkosergey.data.NetworkClient
import com.example.playlist_maker_android_levchenkosergey.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_levchenkosergey.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android_levchenkosergey.domain.api.TracksRepository
import org.koin.dsl.module

/**
 * DI-модуль слоя Data.
 *
 * Koin создаёт сетевой клиент и репозиторий сам: репозиторий получает клиента
 * через `get()`, а не создаёт его внутри себя. Благодаря этому Data-слой
 * не зависит от конкретной реализации сети.
 */
val repositoryModule = module {
    factory<NetworkClient> {
        RetrofitNetworkClient()
    }
    factory<TracksRepository> {
        TracksRepositoryImpl(get())
    }
}
