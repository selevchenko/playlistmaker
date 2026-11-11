package com.example.playlist_maker_android_levchenkosergey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController

/**
 * Единственная Activity приложения.
 *
 * Навигация между экранами выполняется не через Intent и разные Activity,
 * а внутри Compose — через [PlaylistHost] и NavController.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}
