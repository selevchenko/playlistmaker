package com.example.playlist_maker_android_levchenkosergey

import android.app.Application
import com.example.playlist_maker_android_levchenkosergey.data.di.repositoryModule
import com.example.playlist_maker_android_levchenkosergey.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application приложения.
 *
 * Koin запускается до создания Activity, поэтому к моменту, когда MainActivity
 * запрашивает SearchViewModel, граф зависимостей уже собран.
 *
 * Ручная сборка (`Creator`/фабрики в MyApplication) больше не нужна: этим
 * занимается DI-фреймворк.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(repositoryModule, viewModelModule)
        }
    }
}
