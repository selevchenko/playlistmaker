package com.example.playlist_maker_android_levchenkosergey.domain.api

import com.example.playlist_maker_android_levchenkosergey.domain.model.Track

/**
 * Репозиторий поиска треков.
 */
interface TracksRepository {

    /** Ищет треки по названию или имени исполнителя. */
    suspend fun searchTracksContains(expression: String): List<Track>
}
