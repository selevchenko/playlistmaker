package com.example.playlist_maker_android_levchenkosergey

/**
 * Список экранов приложения.
 *
 * Один элемент enum — один маршрут (route) в графе навигации.
 * Маршрут используется в [androidx.navigation.NavHost] и в методах перехода
 * внутри [PlaylistHost], чтобы не писать строки руками в разных местах.
 */
enum class Screen(val route: String) {
    Main("main"),
    Search("search"),
    Settings("settings"),
    Favorites("favorites"),
    Playlists("playlists")
}
