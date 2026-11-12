package com.example.playlist_maker_android_levchenkosergey.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlist_maker_android_levchenkosergey.R
import com.example.playlist_maker_android_levchenkosergey.domain.model.Track
import com.example.playlist_maker_android_levchenkosergey.ui.components.StubScreen
import com.example.playlist_maker_android_levchenkosergey.ui.main.MainScreen
import com.example.playlist_maker_android_levchenkosergey.ui.search.DetailsScreen
import com.example.playlist_maker_android_levchenkosergey.ui.search.SearchScreen
import com.example.playlist_maker_android_levchenkosergey.ui.setting.SettingsScreen
import com.example.playlist_maker_android_levchenkosergey.ui.view_model.SearchViewModel
import com.google.gson.Gson

/**
 * Навигация приложения.
 */
@Composable
fun PlaylistHost(
    navController: NavHostController,
    gson: Gson,
    searchViewModel: SearchViewModel
) {

    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                onSearchClick = { navigateTo(Screen.Search) },
                onPlaylistsClick = { navigateTo(Screen.Playlists) },
                onFavoritesClick = { navigateTo(Screen.Favorites) },
                onSettingsClick = { navigateTo(Screen.Settings) }
            )
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                searchViewModel = searchViewModel,
                onTrackClick = { track ->
                    // Спецсимволы в названии трека не должны ломать маршрут.
                    val trackJson = Uri.encode(gson.toJson(track))
                    navController.navigate("${Screen.Details.route}/$trackJson")
                },
                onBackClick = { navigateBack() }
            )
        }

        composable(route = "${Screen.Details.route}/{trackJson}") { backStackEntry ->
            val trackJson = backStackEntry.arguments?.getString("trackJson")
            if (trackJson != null) {
                val track = gson.fromJson(trackJson, Track::class.java)
                DetailsScreen(
                    track = track,
                    onBackClick = { navigateBack() }
                )
            }
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(onBackClick = { navigateBack() })
        }

        composable(route = Screen.Favorites.route) {
            StubScreen(
                titleText = R.string.menu_favorites,
                onBackClick = { navigateBack() }
            )
        }

        composable(route = Screen.Playlists.route) {
            StubScreen(
                titleText = R.string.menu_playlists,
                onBackClick = { navigateBack() }
            )
        }
    }
}
