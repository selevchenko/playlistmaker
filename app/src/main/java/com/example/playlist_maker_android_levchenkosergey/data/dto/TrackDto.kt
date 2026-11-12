package com.example.playlist_maker_android_levchenkosergey.data.dto

/**
 * DTO трека — модель, в которой данные приходят из источника (сети).
 *
 * DTO не выходит за пределы слоя Data: перед передачей в Domain он
 * преобразуется в доменную модель `Track`.
 */
data class TrackDto(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long
)
