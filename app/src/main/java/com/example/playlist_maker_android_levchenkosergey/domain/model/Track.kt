package com.example.playlist_maker_android_levchenkosergey.domain.model

/**
 * Доменная модель трека — то, с чем работают ViewModel и UI.
 *
 * В отличие от [com.example.playlist_maker_android_levchenkosergey.data.dto.TrackDto],
 * здесь длительность уже приведена к строке `mm:ss`, а лишних полей из ответа
 * сервера нет: слой Domain ничего не знает про формат данных сети.
 */
data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String
)
