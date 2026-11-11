package com.example.playlist_maker_android_levchenkosergey

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip

// Импорты для Activity
import com.example.playlist_maker_android_levchenkosergey.SearchActivity
import com.example.playlist_maker_android_levchenkosergey.PlaylistsActivity
import com.example.playlist_maker_android_levchenkosergey.FavoritesActivity
import com.example.playlist_maker_android_levchenkosergey.SettingsActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(
                    onSearchClick = { startActivity(Intent(this, SearchActivity::class.java)) },
                    onPlaylistsClick = { startActivity(Intent(this, PlaylistsActivity::class.java)) },
                    onFavoritesClick = { startActivity(Intent(this, FavoritesActivity::class.java)) },
                    onSettingsClick = { startActivity(Intent(this, SettingsActivity::class.java)) }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onPlaylistsClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Синий заголовок
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF3B82F6))
        ) {
            Text(
                text = "Playlist maker",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 40.dp)
            )
        }

        // Белая карточка с закругленными верхними углами
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
        ) {
            MenuItem(
                icon = Icons.Default.Search,
                title = "Поиск",
                onClick = onSearchClick
            )
            HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            MenuItem(
                icon = Icons.Default.QueueMusic,
                title = "Плейлисты",
                onClick = onPlaylistsClick
            )
            HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            MenuItem(
                icon = Icons.Default.FavoriteBorder,
                title = "Избранное",
                onClick = onFavoritesClick
            )
            HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            MenuItem(
                icon = Icons.Default.Settings,
                title = "Настройки",
                onClick = onSettingsClick,
                showDivider = false
            )
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF212121),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF212121),
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFFBDBDBD),
            modifier = Modifier.size(24.dp)
        )
    }
}