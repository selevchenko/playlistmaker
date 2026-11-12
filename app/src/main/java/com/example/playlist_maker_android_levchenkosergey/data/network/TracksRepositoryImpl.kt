package com.example.playlist_maker_android_levchenkosergey.data.network

import com.example.playlist_maker_android_levchenkosergey.data.NetworkClient
import com.example.playlist_maker_android_levchenkosergey.data.dto.TrackDto
import com.example.playlist_maker_android_levchenkosergey.domain.api.TracksRepository
import com.example.playlist_maker_android_levchenkosergey.domain.model.Track
import kotlinx.coroutines.delay

/**
 * Реализация репозитория поиска треков.
 *
 * Пока данные учебные: вместо запроса в сеть класс фильтрует заранее заданный
 * список [tracksDto]. Задержка [SEARCH_DELAY_MILLIS] имитирует долгий сетевой
 * ответ, чтобы на экране было видно состояние загрузки.
 *
 * @param networkClient сетевой клиент. Сейчас не используется для фильтрации:
 * он понадобится, когда вместо локального списка появится реальный запрос.
 * Оставлен в конструкторе, чтобы DI-граф и сигнатура репозитория не менялись
 * при переходе на сеть.
 */
class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    private val tracksDto = listOf(
        TrackDto(1, "Yesterday (Remastered 2009)", "The Beatles", 125000L),
        TrackDto(2, "Here Comes The Sun (Remastered 2009)", "The Beatles", 187000L),
        TrackDto(3, "No Reply", "The Beatles", 152000L),
        TrackDto(4, "Let It Be", "The Beatles", 243000L),
        TrackDto(5, "Girl", "The Beatles", 138000L),
        TrackDto(6, "Michelle", "The Beatles", 167000L),
        TrackDto(7, "Eleanor Rigby", "The Beatles", 198000L),
        TrackDto(8, "Come Together", "The Beatles", 259000L)
    )

    override suspend fun searchTracksContains(expression: String): List<Track> {
        // Имитация обращения по сети: 3 секунды задержки.
        delay(SEARCH_DELAY_MILLIS)

        return tracksDto
            .filter { trackDto ->
                trackDto.trackName.contains(expression, ignoreCase = true) ||
                    trackDto.artistName.contains(expression, ignoreCase = true)
            }
            .map { trackDto -> trackDto.toDomain() }
    }

    private fun TrackDto.toDomain(): Track {
        val totalSeconds = trackTimeMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return Track(
            id = id,
            trackName = trackName,
            artistName = artistName,
            trackTime = "%02d:%02d".format(minutes, seconds)
        )
    }

    private companion object {
        const val SEARCH_DELAY_MILLIS = 3000L
    }
}
