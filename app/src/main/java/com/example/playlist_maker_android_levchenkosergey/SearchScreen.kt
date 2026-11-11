package com.example.playlist_maker_android_levchenkosergey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistBlue
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistHint
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistSearchField
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistTextPrimary
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistWhite

/**
 * Экран поиска.
 *
 * Свёрстан по макету: шапка с заголовком и поисковая строка.
 * Полноценная логика поиска появится в следующих спринтах — сейчас
 * обрабатывается только ввод текста и его сброс по иконке «х».
 */
@Composable
fun SearchScreen(onBackClick: () -> Unit) {
    // rememberSaveable — чтобы запрос переживал пересоздание Activity (поворот экрана)
    var query by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaylistWhite)
    ) {
        ScreenHeader(
            title = stringResource(R.string.menu_search),
            onBackClick = onBackClick
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(PlaylistSearchField)
                .padding(start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка «лупа». Нажатие пока не обрабатывается — логики поиска ещё нет.
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = PlaylistHint,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_hint),
                        fontSize = 16.sp,
                        color = PlaylistHint
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = { newValue -> query = newValue },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = PlaylistTextPrimary
                    ),
                    cursorBrush = SolidColor(PlaylistBlue),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Иконка очистки показывается только когда в поле есть текст
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { query = "" },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_query),
                        tint = PlaylistHint,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
