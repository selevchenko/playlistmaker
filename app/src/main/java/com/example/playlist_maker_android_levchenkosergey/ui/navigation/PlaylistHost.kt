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
 * Единственная сущность, отвечающая за навигацию по приложению.
 *
 * Внутри собирается [NavHost] со всеми экранами, а переходы выполняются
 * методами, которые используют [NavController] и [Screen].
 *
 * @param navController контроллер навигации (наследник NavController), создаётся в Activity.
 * @param gson сериализатор для передачи выбранного трека на экран деталей.
 * @param searchViewModel ViewModel экрана поиска, созданная Koin в MainActivity.
 */
@Composable
fun PlaylistHost(
    navController: NavHostController,
    gson: Gson,
    searchViewModel: SearchViewModel
) {

    /** Переход на экран [screen] по его маршруту. */
    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    /** Возврат на предыдущий экран. */
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
                    // Gson превращает Track в JSON, Uri.encode делает строку безопасной
                    // для маршрута (названия треков могут содержать /, ?, & и пробелы).
                    val trackJson = Uri.encode(gson.toJson(track))
                    navController.navigate("${Screen.Details.route}/$trackJson")
                },
                onBackClick = { navigateBack() }
            )
        }

        // Временный маршрут деталей трека: полноценный экран появится позже.
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

        // Экраны «Избранное» и «Плейлисты» появятся в следующих спринтах,
        // пока для них оставлены заглушки, чтобы навигация не вела в пустоту.
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
