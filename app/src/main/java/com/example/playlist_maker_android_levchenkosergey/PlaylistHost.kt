package com.example.playlist_maker_android_levchenkosergey

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Единственная сущность, отвечающая за навигацию по приложению.
 *
 * Внутри собирается [NavHost] со всеми экранами, а переходы выполняются
 * методами, которые используют [NavController] и [Screen].
 *
 * @param navController контроллер навигации (наследник NavController), создаётся в Activity.
 */
@Composable
fun PlaylistHost(navController: NavHostController) {

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
            SearchScreen(onBackClick = { navigateBack() })
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(onBackClick = { navigateBack() })
        }

        // Экраны «Избранное» и «Плейлисты» появятся в следующих спринтах,
        // пока для них оставлены заглушки, чтобы навигация не вела в пустоту.
        composable(route = Screen.Favorites.route) {
            StubScreen(titleText = R.string.menu_favorites, onBackClick = { navigateBack() })
        }

        composable(route = Screen.Playlists.route) {
            StubScreen(titleText = R.string.menu_playlists, onBackClick = { navigateBack() })
        }
    }
}
