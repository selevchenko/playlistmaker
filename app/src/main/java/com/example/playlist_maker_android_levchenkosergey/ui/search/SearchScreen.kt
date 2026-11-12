package com.example.playlist_maker_android_levchenkosergey.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_levchenkosergey.R
import com.example.playlist_maker_android_levchenkosergey.domain.model.Track
import com.example.playlist_maker_android_levchenkosergey.ui.components.ScreenHeader
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistBlue
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistChevron
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistHint
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistSearchField
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistTextPrimary
import com.example.playlist_maker_android_levchenkosergey.ui.theme.PlaylistWhite
import com.example.playlist_maker_android_levchenkosergey.ui.view_model.SearchState
import com.example.playlist_maker_android_levchenkosergey.ui.view_model.SearchViewModel

/**
 * Экран поиска треков.
 *
 * Экран — это View из MVVM: он ничего не знает про репозиторий и сеть.
 * Он подписывается на [SearchViewModel.searchScreenState] и перерисовывается,
 * когда состояние меняется (Initial → Searching → Success/Fail).
 *
 * @param searchViewModel ViewModel экрана; создаётся Koin и передаётся из [PlaylistHost].
 * @param onTrackClick колбэк перехода на экран деталей выбранного трека.
 * @param onBackClick возврат на главный экран.
 */
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onTrackClick: (Track) -> Unit,
    onBackClick: () -> Unit
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()

    // rememberSaveable — чтобы запрос переживал пересоздание Activity (поворот экрана).
    var query by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    // ViewModel живёт в Activity и переживает выход с экрана. Если пользователь
    // вернулся на поиск с пустым полем, старые результаты показывать не нужно.
    // При возврате с экрана деталей запрос восстанавливается из rememberSaveable,
    // поэтому результаты остаются на месте.
    LaunchedEffect(Unit) {
        if (query.isEmpty()) {
            searchViewModel.clearSearch()
        }
    }

    // Поиск запускается и по иконке лупы, и по кнопке «Поиск» на клавиатуре.
    val runSearch = {
        if (query.isNotEmpty()) {
            searchViewModel.searchTracks(query)
            focusManager.clearFocus()
        }
    }

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
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon),
                tint = PlaylistHint,
                modifier = Modifier
                    .size(18.dp)
                    .clickable(onClick = runSearch)
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
                    onValueChange = { newValue ->
                        query = newValue
                        // Если строку стёрли целиком, старые результаты больше не актуальны.
                        if (newValue.isEmpty()) {
                            searchViewModel.clearSearch()
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = PlaylistTextPrimary
                    ),
                    cursorBrush = SolidColor(PlaylistBlue),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { runSearch() }),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Иконка очистки показывается только когда в поле есть текст.
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = {
                        query = ""
                        searchViewModel.clearSearch()
                    },
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

        SearchResults(
            screenState = screenState,
            onTrackClick = onTrackClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

/**
 * Отображает результаты поиска в зависимости от состояния экрана.
 */
@Composable
private fun SearchResults(
    screenState: SearchState,
    onTrackClick: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    when (screenState) {
        is SearchState.Initial -> {
            // Пользователь ещё ничего не искал — экран остаётся пустым.
        }

        is SearchState.Searching -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PlaylistBlue)
            }
        }

        is SearchState.Success -> {
            val tracks = screenState.foundList
            if (tracks.isEmpty()) {
                InformerState(
                    titleText = stringResource(R.string.no_tracks_found),
                    modifier = modifier
                )
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items = tracks, key = { track -> track.id }) { track ->
                        TrackListItem(
                            track = track,
                            onClick = { onTrackClick(track) }
                        )
                    }
                }
            }
        }

        is SearchState.Fail -> {
            InformerState(
                titleText = screenState.error,
                subtitleText = stringResource(R.string.check_connection),
                modifier = modifier
            )
        }
    }
}

/**
 * Информационное сообщение на весь экран: «ничего не найдено» или ошибка.
 */
@Composable
private fun InformerState(
    titleText: String,
    subtitleText: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleText,
            fontSize = 18.sp,
            color = PlaylistTextPrimary,
            textAlign = TextAlign.Center
        )

        subtitleText?.let { subtitle ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = PlaylistHint,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Один трек в списке результатов: иконка-заглушка, название, исполнитель,
 * длительность и стрелка перехода к деталям.
 */
@Composable
private fun TrackListItem(
    track: Track,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(PlaylistSearchField),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = PlaylistHint,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.trackName,
                fontSize = 16.sp,
                color = PlaylistTextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = track.artistName,
                    fontSize = 11.sp,
                    color = PlaylistHint,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Text(
                    text = "•",
                    fontSize = 11.sp,
                    color = PlaylistHint,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = track.trackTime,
                    fontSize = 11.sp,
                    color = PlaylistHint,
                    maxLines = 1
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = stringResource(R.string.open_track),
            tint = PlaylistChevron,
            modifier = Modifier.size(24.dp)
        )
    }
}
