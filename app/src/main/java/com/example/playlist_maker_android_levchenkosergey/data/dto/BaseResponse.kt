package com.example.playlist_maker_android_levchenkosergey.data.dto

/**
 * Общий родитель ответов сервера.
 *
 * Благодаря ему репозиторий может единообразно проверить результат запроса
 * по [resultCode], не зная заранее, какой конкретно ответ пришёл.
 */
open class BaseResponse {
    var resultCode = 0
}
