package com.example.playlist_maker_android_levchenkosergey.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_levchenkosergey.ui.navigation.PlaylistHost
import com.example.playlist_maker_android_levchenkosergey.ui.view_model.SearchViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Единственная Activity приложения.
 */
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PlaylistHost(
                    navController = rememberNavController(),
                    gson = remember { Gson() },
                    searchViewModel = searchViewModel
                )
            }
        }
    }
}
