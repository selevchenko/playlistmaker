package com.example.playlist_maker_android_levchenkosergey.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_levchenkosergey.R
import com.example.playlist_maker_android_levchenkosergey.domain.model.Track
import com.example.playlist_maker_android_levchenkosergey.ui.components.ScreenHeader
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistHint
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistTextPrimary
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistWhite

/**
 * Временный экран деталей трека.
 *
 * В этом спринте мы только настраиваем связь «список результатов → экран деталей».
 * Финальная вёрстка и полноценная логика появятся в следующих спринтах,
 * поэтому сейчас экран показывает базовую информацию о треке.
 */
@Composable
fun DetailsScreen(
    track: Track,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaylistWhite)
    ) {
        ScreenHeader(
            title = stringResource(R.string.track_details_title),
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = track.trackName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = PlaylistTextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = track.artistName,
                fontSize = 16.sp,
                color = PlaylistTextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = track.trackTime,
                fontSize = 14.sp,
                color = PlaylistHint
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.details_stub),
                fontSize = 14.sp,
                color = PlaylistHint
            )
        }
    }
}
