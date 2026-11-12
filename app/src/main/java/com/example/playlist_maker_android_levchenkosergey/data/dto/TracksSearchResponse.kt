package com.example.playlist_maker_android_levchenkosergey.data.dto

/**
 * Ответ сервера на поисковый запрос: список найденных треков в виде DTO.
 */
class TracksSearchResponse(val results: List<TrackDto>) : BaseResponse()
