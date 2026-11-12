package com.example.playlist_maker_android_levchenkosergey.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_levchenkosergey.R
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistBlue
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistChevron
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistDivider
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistTextPrimary
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistWhite

/**
 * Главный экран приложения: синяя шапка и список разделов.
 *
 * Экран ничего не знает про навигацию — он только сообщает наружу,
 * какой раздел выбрал пользователь.
 */
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
                .background(PlaylistBlue)
        ) {
            Text(
                text = stringResource(R.string.app_title),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 40.dp)
            )
        }

        // Белая карточка с закруглёнными верхними углами
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(PlaylistWhite)
        ) {
            MenuItem(
                icon = Icons.Default.Search,
                title = stringResource(R.string.menu_search),
                onClick = onSearchClick
            )
            HorizontalDivider(color = PlaylistDivider, thickness = 1.dp)

            MenuItem(
                icon = Icons.AutoMirrored.Filled.QueueMusic,
                title = stringResource(R.string.menu_playlists),
                onClick = onPlaylistsClick
            )
            HorizontalDivider(color = PlaylistDivider, thickness = 1.dp)

            MenuItem(
                icon = Icons.Default.FavoriteBorder,
                title = stringResource(R.string.menu_favorites),
                onClick = onFavoritesClick
            )
            HorizontalDivider(color = PlaylistDivider, thickness = 1.dp)

            MenuItem(
                icon = Icons.Default.Settings,
                title = stringResource(R.string.menu_settings),
                onClick = onSettingsClick
            )
        }
    }
}

/**
 * Строка меню главного экрана: иконка, название и стрелка вправо.
 */
@Composable
private fun MenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
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
            tint = PlaylistTextPrimary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = PlaylistTextPrimary,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = PlaylistChevron,
            modifier = Modifier.size(24.dp)
        )
    }
}
